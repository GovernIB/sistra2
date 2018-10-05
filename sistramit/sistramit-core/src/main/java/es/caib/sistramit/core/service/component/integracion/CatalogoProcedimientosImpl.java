package es.caib.sistramit.core.service.component.integracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.DefinicionTramiteCP;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.ICatalogoProcedimientosPlugin;
import es.caib.sistra2.commons.plugins.catalogoprocedimientos.api.RolsacPluginException;
import es.caib.sistramit.core.api.exception.CatalogoProcedimientosException;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;

/**
 * Implementación acceso SISTRAGES.
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
			final String idioma) {
		final ICatalogoProcedimientosPlugin plgCP = getPlugin(idEntidad);
		try {
			return plgCP.obtenerDefinicionTramite(idTramiteCP, idioma);
		} catch (final RolsacPluginException e) {
			log.error("Error obteniendo la info del tramite", e);
			throw new CatalogoProcedimientosException("Error obteniendo la definición de tramites", e);
		}
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
