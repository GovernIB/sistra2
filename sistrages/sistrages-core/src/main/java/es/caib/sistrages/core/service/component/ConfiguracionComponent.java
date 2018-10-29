package es.caib.sistrages.core.service.component;

import org.fundaciobit.pluginsib.core.IPlugin;

import es.caib.sistrages.core.api.model.types.TypePlugin;

/**
 * Componente para acceder a configuracion.
 *
 * @author Indra
 *
 */
public interface ConfiguracionComponent {

	/**
	 * Obtiene tipo plugin global.
	 *
	 * @param tipoPlugin
	 *            tipo plugin
	 * @return Plugin
	 */
	IPlugin obtenerPluginGlobal(TypePlugin tipoPlugin);

	/**
	 * Obtiene tipo plugin entidad.
	 *
	 * @param tipoPlugin
	 *            tipo plugin
	 * @return Plugin
	 */
	IPlugin obtenerPluginEntidad(TypePlugin tipoPlugin, Long idEntidad);

}