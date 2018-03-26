package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;

/**
 * La interface ConfiguracionGlobalService.
 */
public interface ConfiguracionGlobalService {

	/**
	 * Obtiene el valor de configuracion global.
	 *
	 * @param idConfiguracionGlobal
	 *            el identificador de configuracion global
	 * @return el valor de configuracion global
	 */
	public ConfiguracionGlobal getConfiguracionGlobal(Long idConfiguracionGlobal);

	/**
	 * Obtiene el valor de configuracion global.
	 *
	 * @param propiedad
	 *            la propiedad de configuracion global
	 * @return el valor de configuracion global
	 */
	public ConfiguracionGlobal getConfiguracionGlobal(String propiedad);

	/**
	 * Añade la configuracion global.
	 *
	 * @param configuracionGlobal
	 *            el valor de configuracion global
	 */
	public void addConfiguracionGlobal(ConfiguracionGlobal configuracionGlobal);

	/**
	 * Elimina la configuracion global.
	 *
	 * @param idConfiguracionGlobal
	 *            el identificador de configuracion global
	 */
	public void removeConfiguracionGlobal(Long idConfiguracionGlobal);

	/**
	 * Actualiza la configuracion global.
	 *
	 * @param configuracionGlobal
	 *            el valor de configuracion global
	 */
	public void updateConfiguracionGlobal(ConfiguracionGlobal configuracionGlobal);

	/**
	 * Lista de valores de configuracion global.
	 *
	 * @param filtro
	 *            el filtro
	 * @return la lista valores de configuracion global
	 */
	public List<ConfiguracionGlobal> listConfiguracionGlobal(String filtro);

}
