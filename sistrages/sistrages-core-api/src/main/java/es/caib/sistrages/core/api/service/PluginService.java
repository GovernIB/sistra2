package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

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
	 *            el identificador
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
	 * Lista de Aviso Entidad.
	 *
	 * @param idEntidad
	 *            id Entidad
	 * @param ambito
	 *            ambito
	 * @param filtro
	 *            filtro busqueda
	 * @return la lista de Aviso Entidad
	 */
	List<Plugin> listPlugin(TypeAmbito ambito, Long idEntidad, String filtro);

}
