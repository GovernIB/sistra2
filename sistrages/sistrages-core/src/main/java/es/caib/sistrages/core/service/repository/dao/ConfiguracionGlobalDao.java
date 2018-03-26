package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;

/**
 * La interface ConfiguracionGlobalDao.
 */
public interface ConfiguracionGlobalDao {

	/**
	 * Añade la configuracion global.
	 *
	 * @param configuracionGlobal
	 *            el valor de configuracion global
	 */
	void add(ConfiguracionGlobal configuracionGlobal);

	/**
	 * Obtiene el valor de configuracion global.
	 *
	 * @param id
	 *            el identificador de configuracion global
	 * @return el valor de configuracion global
	 */
	ConfiguracionGlobal getById(Long id);

	/**
	 * Obtiene el valor de configuracion global.
	 *
	 * @param propiedad
	 *            la propiedad de configuracion global
	 * @return el valor de configuracion global
	 */
	ConfiguracionGlobal getByPropiedad(String propiedad);

	/**
	 * Lista de valores de configuracion global.
	 *
	 * @return la lista de valores de configuracion global
	 */
	List<ConfiguracionGlobal> getAll();

	/**
	 * Lista de valores de configuracion global.
	 *
	 * @param filtro
	 *            el filtro de busqueda
	 * @return la lista de valores de configuracion global
	 */
	List<ConfiguracionGlobal> getAllByFiltro(String filtro);

	/**
	 * Elimina la configuracion global.
	 *
	 * @param id
	 *            el identificador de configuracion global
	 */
	void remove(Long id);

	/**
	 * Actualiza la configuracion global.
	 *
	 * @param configuracionGlobal
	 *            el valor de configuracion global
	 */
	void update(ConfiguracionGlobal configuracionGlobal);

}
