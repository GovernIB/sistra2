package es.caib.sistramit.core.service.component.integracion;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ArchivoCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.CatalogoPluginException;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionProcedimientoCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteTelematico;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ICatalogoProcedimientosPlugin;
import es.caib.sistramit.core.api.exception.CatalogoProcedimientosException;
import es.caib.sistramit.core.api.model.comun.Constantes;
import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.literales.Literales;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Implementación acceso catálogo procedimientos.
 *
 * @author Indra
 *
 */
@Component("catalogoProcedimientosComponent")
public final class CatalogoProcedimientosImpl implements CatalogoProcedimientosComponent {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Nombre de la cache. **/
	private static final String CACHE_NAME = "cacheCP";

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Literales negocio. */
	@Autowired
	private Literales literales;

	@Override
	public DefinicionTramiteCP obtenerDefinicionTramite(final String idEntidad, final String idTramiteCP,
			final boolean servicio, final String idioma) {
		DefinicionTramiteCP definicionTramite = null;

		// Definición trámite simulado
		if (Constantes.TRAMITE_CATALOGO_SIMULADO_ID.equals(idTramiteCP)) {
			definicionTramite = obtenerDefinicionTramiteSimulado(idioma);
		} else {
			// Definición trámite a través de plugin CP
			// - Generamos key para cache
			final String cacheKey = getCacheKey(idEntidad, idTramiteCP, servicio, idioma);
			// - Verifica si esta en cache
			definicionTramite = getFromCache(cacheKey);
			// - Obtener definicion tramite de catalogo servicios
			if (definicionTramite == null) {
				// Obtenemos definicion a traves del plugin
				final ICatalogoProcedimientosPlugin plgCP = getPlugin(idEntidad);
				try {
					definicionTramite = plgCP.obtenerDefinicionTramite(idTramiteCP, servicio, idioma);
				} catch (final CatalogoPluginException e) {
					log.error("Error obtenint la info del tràmit", e);
					throw new CatalogoProcedimientosException("Error obtenint la definició de tramits", e);
				}
			}
			// - Guarda en cache
			saveToCache(cacheKey, definicionTramite);
		}

		// Verificamos datos definicion
		if (definicionTramite == null) {
			throw new CatalogoProcedimientosException(
					"Error obtenint la definició de tramits: torna nul la definició");
		}
		if (definicionTramite.getProcedimiento() == null) {
			throw new CatalogoProcedimientosException(
					"Error obtenint la definició de tràmits: no existeix procediment associat");
		}
		if (StringUtils.isBlank(definicionTramite.getProcedimiento().getIdProcedimientoSIA())) {
			throw new CatalogoProcedimientosException(
					"Error obtenint la definició de tràmits: no existeix codi SIA associat al procediment");
		}
		if (StringUtils.isBlank(definicionTramite.getProcedimiento().getOrganoResponsableDir3())) {
			throw new CatalogoProcedimientosException(
					"Error obtenint la definició de tràmits: no existeix codi associat DIR3 associat al procediment");
		}
		if (StringUtils.isBlank(definicionTramite.getOrganoDestinoDir3())) {
			throw new CatalogoProcedimientosException(
					"Error obtenint la definició de tràmits: no existeix codi DIR3 destinatari tràmit");
		}

		return definicionTramite;
	}

	@Override
	public ArchivoCP descargarArchivo(final String idEntidad, final String referenciaArchivo) {
		final ICatalogoProcedimientosPlugin plgCP = getPlugin(idEntidad);
		try {
			final ArchivoCP archivo = plgCP.descargarArchivo(referenciaArchivo);
			return archivo;
		} catch (final CatalogoPluginException e) {
			log.error("Error obtenint la info del tràmit", e);
			throw new CatalogoProcedimientosException("Error obtenint la definició de tràmits", e);
		}
	}

	@Override
	public void evictCatalogoProcedimientosEntidad(final String idEntidad) {
		final Cache cache = getCache();
		final List<String> keys = cache.getKeys();
		for (final Iterator<String> it = keys.iterator(); it.hasNext();) {
			final String key = it.next();
			if (key.startsWith(idEntidad + "#"))
				cache.remove(key);
		}
	}

	/**
	 * Obtiene definicion tramite de forma simulada.
	 *
	 * @param idioma
	 *                   idioma
	 * @return definicion tramite de forma simulada.
	 */
	private DefinicionTramiteCP obtenerDefinicionTramiteSimulado(final String idioma) {

		final TypeEntorno entorno = TypeEntorno
				.fromString(configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.ENTORNO));
		if (entorno != TypeEntorno.DESARROLLO) {
			throw new CatalogoProcedimientosException(
					"Simulació de catàleg procediments només disponible per entorn desenvolupament");
		}

		final DefinicionTramiteTelematico tt = new DefinicionTramiteTelematico();
		tt.setTramiteIdentificador(Constantes.TRAMITE_CATALOGO_SIMULADO_ID);
		tt.setTramiteVersion(1);

		final DefinicionProcedimientoCP pr = new DefinicionProcedimientoCP();
		pr.setDescripcion(literales.getLiteral(Literales.FLUJO, "tramiteSimuladoCP.descripcion", idioma));
		pr.setIdentificador(Constantes.TRAMITE_CATALOGO_SIMULADO_ID);
		pr.setIdProcedimientoSIA("SIA-SIMULADO");
		pr.setOrganoResponsableDir3("DIR3-SIMULADO");

		final DefinicionTramiteCP dt = new DefinicionTramiteCP();
		dt.setIdentificador(Constantes.TRAMITE_CATALOGO_SIMULADO_ID);
		dt.setDescripcion(literales.getLiteral(Literales.FLUJO, "tramiteSimuladoCP.descripcion", idioma));
		dt.setOrganoDestinoDir3("DIR3-SIMULADO");
		dt.setVigente(true);
		dt.setTelematico(true);
		dt.setProcedimiento(pr);
		dt.setTramiteTelematico(tt);

		return dt;
	}

	/**
	 * Obtiene plugin
	 *
	 * @param idEntidad
	 *                      idEntidad
	 *
	 * @return plugin
	 */
	private ICatalogoProcedimientosPlugin getPlugin(final String idEntidad) {
		return (ICatalogoProcedimientosPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.CATALOGO_PROCEDIMIENTOS, idEntidad);
	}

	/**
	 * Recupera de caché.
	 *
	 * @param key
	 *                key
	 * @return DefinicionTramiteCP
	 */
	private DefinicionTramiteCP getFromCache(final Serializable key) {
		DefinicionTramiteCP res = null;
		final Cache cache = getCache();
		final Element element = cache.get(key);
		if (element != null && !cache.isExpired(element)) {
			res = (DefinicionTramiteCP) element.getObjectValue();
		}
		return res;
	}

	/**
	 * Salva la cache.
	 *
	 * @param key
	 * @param value
	 * @throws CacheException
	 */
	protected void saveToCache(final Serializable key, final DefinicionTramiteCP value) {
		final Cache cache = getCache();
		cache.put(new Element(key, value));
	}

	/**
	 * Obtiene caché.
	 *
	 * @return caché
	 */
	private Cache getCache() {
		final CacheManager cacheManager = CacheManager.getInstance();
		Cache cache = null;
		if (cacheManager.cacheExists(CACHE_NAME)) {
			cache = cacheManager.getCache(CACHE_NAME);
		} else {
			throw new CacheException("Caché de domini no està definida: " + CACHE_NAME);
		}
		return cache;
	}

	/**
	 * Obtiene cache key.
	 *
	 * @param idEntidad
	 *                        idEntidad
	 * @param idTramiteCP
	 *                        idTramiteCP
	 * @param servicio
	 *                        servicio
	 * @param idioma
	 *                        idioma
	 * @return cache key
	 */
	private String getCacheKey(final String idEntidad, final String idTramiteCP, final boolean servicio,
			final String idioma) {
		return idEntidad + "#" + idTramiteCP + "#" + servicio + "#" + idioma;
	}

}
