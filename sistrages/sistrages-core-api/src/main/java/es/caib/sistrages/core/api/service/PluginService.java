package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypePlugin;

/**
 * Plugin Service.
 */
public interface PluginService {

	/**
	 * Obtiene Plugin.
	 *
	 * @param id
	 *            identificador
	 * @return Plugin
	 */
	Plugin getPlugin(Long id);

	/**
	 * Añade Plugin.
	 *
	 * @param plugin
	 *            Plugin
	 * @param idEntidad
	 *            idEntidad
	 */
	void addPlugin(Plugin plugin, Long idEntidad);

	/**
	 * Elimina Plugin.
	 *
	 * @param id
	 *            identificador
	 * @return indica si ha borrado o no (por dependencias)
	 */
	boolean removePlugin(Long id);

	/**
	 * Actualiza plugin.
	 *
	 * @param plugin
	 *            plugin
	 */
	void updatePlugin(Plugin plugin);

	/**
	 * Lista los plugin.
	 *
	 * @param ambito
	 *            ambito
	 * @param idEntidad
	 *            idEntidad
	 * @param filtro
	 *            filtro
	 * @return la lista de plugin
	 */
	List<Plugin> listPlugin(TypeAmbito ambito, Long idEntidad, String filtro);

	/**
	 * Lista los plugin.
	 *
	 * @param ambito
	 *            ambito
	 * @param idEntidad
	 *            idEntidad
	 * @param tipo
	 *            tipo
	 * @return la lista de plugin
	 */
	List<Plugin> listPlugin(TypeAmbito ambito, Long idEntidad, TypePlugin tipo);

}
