package es.caib.sistramit.core.service.component.integracion;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistramit.core.api.exception.DominioErrorException;
import es.caib.sistramit.core.api.exception.DominioNoExisteException;
import es.caib.sistramit.core.api.exception.DominioSinDatosException;
import es.caib.sistramit.core.api.exception.TipoNoControladoException;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;
import es.caib.sistramit.core.service.model.integracion.ValoresDominio;
import es.caib.sistramit.core.service.model.integracion.types.TypeCache;
import es.caib.sistramit.core.service.util.UtilsSTG;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Implementación dominios component.
 *
 * @author Indra
 *
 */
@Component("dominiosComponent")
public final class DominiosComponentImpl implements DominiosComponent {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Nombre de la cache. **/
	private static final String CACHE_NAME = "cacheDominiosDatos";

	/** Nombre de la cache implicita. **/
	private static final String CACHE_IMPLICITA_NAME = "cacheImplicitaDominiosDatos";

	/** Separator. **/
	private static final String SEPARATOR = "#@@#";

	/** TIEMPO EN CACHE PARA DOMINIOS */
	public static final int TIEMPO_EN_CACHE = 60 * 60 * 24;

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	/** Configuracion. */
	@Autowired
	private DominiosResolucionComponent dominioResolucionComponent;

	/**
	 * Se encarga de resolver el dominio
	 *
	 * @param idDominio
	 * @param parametrosDominio
	 * @param defTramite
	 * @return
	 */
	@Override
	public ValoresDominio recuperarDominio(final String idDominio, final ParametrosDominio parametrosDominio,
			final DefinicionTramiteSTG defTramite) {

		// Obtenemos informacion dominio
		if (UtilsSTG.isDebugEnabled(defTramite)) {
			if (parametrosDominio != null && parametrosDominio.getParametros().size() > 0) {
				log.debug(
						"Accediendo a dominio " + idDominio + " con parametros [ " + parametrosDominio.print() + " ]");
			} else {
				log.debug("Accediendo a dominio " + idDominio + " sin parametros");
			}
		}

		final boolean existe = UtilsSTG.existeDominioDefinicion(idDominio, defTramite);
		if (!existe) {
			throw new DominioNoExisteException("No existeix domini " + idDominio + " en la definició del tràmit");
		}
		final RDominio dominio = configuracionComponent.recuperarDefinicionDominio(idDominio);

		// Generamos key para cache
		final String cacheKey = generateCacheKey(idDominio, parametrosDominio);

		// Verificamos cache a usar
		final TypeCache tipoCache = TypeCache.fromString(dominio.getTipoCache());
		if (tipoCache == null) {
			throw new TipoNoControladoException("Tipus cache no controlat " + dominio.getTipoCache());
		}

		// Intentamos obtener de cache
		ValoresDominio valoresDominio = getFromCache(cacheKey, tipoCache);

		// Accedemos a dominio si no se encuentra en caché
		if (valoresDominio == null) {
			switch (dominio.getTipo()) {
			case RDominio.TIPO_LISTA_LISTA:
				valoresDominio = resuelveDominioLF(dominio);
				break;
			case RDominio.TIPO_CONSULTA_REMOTA:
				valoresDominio = resuelveDominioWS(dominio, parametrosDominio);
				break;
			case RDominio.TIPO_CONSULTA_BD:
				valoresDominio = resuelveDominioSQL(dominio, parametrosDominio);
				break;
			case RDominio.TIPO_FUENTE_DATOS:
				valoresDominio = resuelveDominioFD(dominio, parametrosDominio);
				break;
			default:
				throw new TipoNoControladoException("Tipus de domini no suportat: " + dominio.getTipo());
			}

			// Controlamos que el dominio devuelve datos nulos
			if (valoresDominio == null) {
				throw new DominioSinDatosException("Domini " + dominio.getIdentificador() + " retorna dades nules");
			} else if (valoresDominio.isError()) {
				// Controlamos que el dominio devuelve código retorno opcional
				throw new DominioErrorException("Domini " + dominio.getIdentificador() + " no es pot recuperar: ["
						+ valoresDominio.getCodigoError() + "] - " + valoresDominio.getDescripcionError());
			}

			// Guardamos en cache
			this.saveToCache(cacheKey, valoresDominio, tipoCache);

		}

		return valoresDominio;

	}

	/**
	 * Genera key para caché en base a id dominio y parámetros.
	 *
	 * @param idDominio
	 *                              id dominio
	 * @param parametrosDominio
	 *                              parametros
	 * @return key cache
	 */
	private String generateCacheKey(final String idDominio, final ParametrosDominio parametrosDominio) {
		String cacheKey;
		String hashCode = "";
		if (parametrosDominio != null && parametrosDominio.getParametros() != null
				&& !parametrosDominio.getParametros().isEmpty()) {
			hashCode = parametrosDominio.hashCode() + "";
		}
		cacheKey = idDominio + SEPARATOR + hashCode;
		return cacheKey;
	}

	/**
	 * Resuelve dominio para Dominios de Fuente de Datos.
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @param url
	 * @return
	 */
	private ValoresDominio resuelveDominioFD(final RDominio dominio, final ParametrosDominio listaParametros) {
		return dominioResolucionComponent.resuelveDominioFD(dominio, listaParametros);
	}

	/**
	 * Resuelve dominio para Dominios de Lista Fija
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @param url
	 * @return
	 */
	private ValoresDominio resuelveDominioLF(final RDominio dominio) {
		return dominioResolucionComponent.resuelveDominioLF(dominio);
	}

	/**
	 * Resuelve dominio para Dominios de SQL.
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @param url
	 * @return
	 */
	private ValoresDominio resuelveDominioSQL(final RDominio dominio, final ParametrosDominio parametrosDominio) {
		return dominioResolucionComponent.resuelveDominioSQL(dominio, parametrosDominio);
	}

	/**
	 * Resuelve dominio para Dominio de Web Service.
	 *
	 * @param dominio
	 * @param parametrosDominio
	 * @return
	 */
	private ValoresDominio resuelveDominioWS(final RDominio dominio, final ParametrosDominio parametrosDominio) {
		return dominioResolucionComponent.resuelveDominioWS(dominio, parametrosDominio);
	}

	/**
	 * Obtiene el valor de la cache.
	 *
	 * @param key
	 * @return
	 * @throws CacheException
	 */
	protected ValoresDominio getFromCache(final Serializable key, final TypeCache tipoCache) {
		ValoresDominio res = null;
		if (tipoCache != TypeCache.CACHE_NO) {
			final Cache cache = getCache(tipoCache);
			final Element element = cache.get(key);
			if (element != null && !cache.isExpired(element)) {
				res = (ValoresDominio) element.getObjectValue();
				res.setTipoCache(tipoCache);
			}
		}
		return res;
	}

	/**
	 * Obtiene la cache.
	 *
	 * @return
	 * @throws CacheException
	 */
	private static Cache getCache(final TypeCache tipoCache) {
		String cachename = null;
		switch (tipoCache) {
		case CACHE_EXPLICITA:
			cachename = CACHE_NAME;
			break;
		case CACHE_IMPLICITA:
			cachename = CACHE_IMPLICITA_NAME;
			break;
		default:
			throw new TipoNoControladoException("Tipus cache no controlat");
		}
		final CacheManager cacheManager = CacheManager.getInstance();
		Cache cache;
		if (cacheManager.cacheExists(cachename)) {
			cache = cacheManager.getCache(cachename);
		} else {
			throw new CacheException("Cache de domini no està definida: " + cachename);
		}
		return cache;
	}

	/**
	 * Salva la cache.
	 *
	 * @param key
	 * @param value
	 * @throws CacheException
	 */
	protected void saveToCache(final Serializable key, final ValoresDominio value, final TypeCache tipoCache) {
		if (tipoCache != TypeCache.CACHE_NO) {
			final Cache cache = getCache(tipoCache);
			cache.put(new Element(key, value));
		}
	}

	/**
	 * Limpiar cache.
	 */
	public static void limpiarCache() {
		final CacheManager cacheManager = CacheManager.getInstance();
		Cache cache;
		if (cacheManager.cacheExists(CACHE_NAME)) {
			cache = cacheManager.getCache(CACHE_NAME);
			final List<String> keys = cache.getKeys();
			for (final Iterator<String> it = keys.iterator(); it.hasNext();) {
				final String key = it.next();
				final int idx = key.indexOf(SEPARATOR);
				if (idx != -1)
					cache.remove(key);
			}
		}

	}

	@Override
	public void invalidarDominio(final String idDominio) {
		final CacheManager cacheManager = CacheManager.getInstance();
		Cache cache;
		if (cacheManager.cacheExists(CACHE_NAME)) {
			cache = cacheManager.getCache(CACHE_NAME);
			final List<String> keys = cache.getKeys();
			for (final String key : keys) {
				// La idea sería borrar sin importar los parametros dominio que
				// van detras del
				// separador.
				if (key.indexOf(idDominio + SEPARATOR) != -1) {
					cache.remove(key);
				}
			}
		}
	}

}
