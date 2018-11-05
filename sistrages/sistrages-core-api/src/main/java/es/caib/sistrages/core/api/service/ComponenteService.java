package es.caib.sistrages.core.api.service;

import org.fundaciobit.pluginsib.core.IPlugin;

import es.caib.sistrages.core.api.model.types.TypePlugin;

/**
 * Componente Service.
 */
public interface ComponenteService {

	/**
	 * Obtiene un plugin global.
	 * 
	 * @param tipoPlugin
	 * @return
	 */
	public IPlugin obtenerPluginGlobal(final TypePlugin tipoPlugin);

	/**
	 * Obtiene un plugin de tipo entidad.
	 * 
	 * @param tipoPlugin
	 * @param idEntidad
	 * @return
	 */
	public IPlugin obtenerPluginEntidad(final TypePlugin tipoPlugin, final Long idEntidad);
}
