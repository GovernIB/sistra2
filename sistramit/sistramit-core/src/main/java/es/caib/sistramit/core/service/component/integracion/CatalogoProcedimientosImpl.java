package es.caib.sistramit.core.service.component.integracion;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.CatalogoPluginException;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ICatalogoProcedimientosPlugin;
import es.caib.sistramit.core.api.exception.CatalogoProcedimientosException;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
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

	@Override
	public DefinicionTramiteCP obtenerDefinicionTramite(final String idEntidad, final String idTramiteCP,
			final boolean servicio, final String idioma) {
		DefinicionTramiteCP definicionTramite = null;

		// Obtenemos definicion a traves del plugin
		final ICatalogoProcedimientosPlugin plgCP = getPlugin(idEntidad);
		try {
			definicionTramite = plgCP.obtenerDefinicionTramite(idTramiteCP, servicio, idioma);
		} catch (final CatalogoPluginException e) {
			log.error("Error obteniendo la info del tramite", e);
			throw new CatalogoProcedimientosException("Error obteniendo la definición de tramites", e);
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

	/**
	 * Obtiene plugin
	 *
	 * @param idEntidad
	 *            idEntidad
	 *
	 * @return plugin
	 */
	private ICatalogoProcedimientosPlugin getPlugin(final String idEntidad) {
		return (ICatalogoProcedimientosPlugin) configuracionComponent
				.obtenerPluginEntidad(TypePluginEntidad.CATALOGO_PROCEDIMIENTOS, idEntidad);
	}

}
