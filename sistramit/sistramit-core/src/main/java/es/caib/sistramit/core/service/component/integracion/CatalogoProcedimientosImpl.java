package es.caib.sistramit.core.service.component.integracion;

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

		// Obtener definicion tramite de catalogo servicios
		if (Constantes.TRAMITE_CATALOGO_SIMULADO_ID.equals(idTramiteCP)) {
			// Obtenemos definicion tramite simulado
			definicionTramite = obtenerDefinicionTramiteSimulado(idioma);
		} else {
			// Obtenemos definicion a traves del plugin
			final ICatalogoProcedimientosPlugin plgCP = getPlugin(idEntidad);
			try {
				definicionTramite = plgCP.obtenerDefinicionTramite(idTramiteCP, servicio, idioma);
			} catch (final CatalogoPluginException e) {
				log.error("Error obteniendo la info del tramite", e);
				throw new CatalogoProcedimientosException("Error obteniendo la definición de tramites", e);
			}
		}

		// Verificamos datos definicion
		if (definicionTramite == null) {
			throw new CatalogoProcedimientosException(
					"Error obteniendo la definición de tramites: devuelve nulo la definición");
		}
		if (definicionTramite.getProcedimiento() == null) {
			throw new CatalogoProcedimientosException(
					"Error obteniendo la definición de tramites: no existe procedimiento asociado");
		}
		if (StringUtils.isBlank(definicionTramite.getProcedimiento().getIdProcedimientoSIA())) {
			throw new CatalogoProcedimientosException(
					"Error obteniendo la definición de tramites: no existe código SIA asociado al procedimiento");
		}
		if (StringUtils.isBlank(definicionTramite.getProcedimiento().getOrganoResponsableDir3())) {
			throw new CatalogoProcedimientosException(
					"Error obteniendo la definición de tramites: no existe código DIR3 asociado al procedimiento");
		}
		if (StringUtils.isBlank(definicionTramite.getOrganoDestinoDir3())) {
			throw new CatalogoProcedimientosException(
					"Error obteniendo la definición de tramites: no existe código DIR3 destinatario trámite");
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
			log.error("Error obteniendo la info del tramite", e);
			throw new CatalogoProcedimientosException("Error obteniendo la definición de tramites", e);
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
					"Simulación de catálogo procedimientos sólo disponible para entorno desarrollo");
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

}
