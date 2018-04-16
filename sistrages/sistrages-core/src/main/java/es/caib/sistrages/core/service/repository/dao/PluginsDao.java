package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

/**
 * PluginsDao.
 */
public interface PluginsDao {

	/**
	 * Obtiene plugin.
	 *
	 * @param id
	 *            el identificador de configuracion global
	 * @return el valor de configuracion global
	 */
	Plugin getById(Long id);

	/**
	 * Lista plugins.
	 *
	 * @param ambito
	 *            ambito
	 * @param idEntidad
	 *            idEntidad
	 * @return Lista plugins
	 */
	List<Plugin> getAll(TypeAmbito ambito, Long idEntidad);

	/**
	 * Lista plugins.
	 *
	 * @param ambito
	 *            ambito
	 * @param idEntidad
	 *            idEntidad
	 * @param filtro
	 *            el filtro de busqueda
	 * @return la lista de valores de configuracion global
	 */
	List<Plugin> getAllByFiltro(TypeAmbito ambito, Long idEntidad, String filtro);

	/**
	 * Añade plugin.
	 *
	 * @param plugin
	 *            valor plugin
	 * @param idEntidad
	 *            id Entidad (requerido para ambito de entidad)
	 */
	void add(Plugin plugin, Long idEntidad);

	/**
	 * Actualiza plugin.
	 *
	 * @param plugin
	 *            plugin
	 */
	void update(Plugin plugin);

	/**
	 * Elimina Plugin.
	 *
	 * @param id
	 *            el identificador de Plugin
	 */
	void remove(Long id);

	/**
	 * Elimina Plugins de una entidad.
	 *
	 * @param idEntidad
	 *            el identificador de identidad
	 */
	void removeByEntidad(Long idEntidad);

}
