package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Rol;

/**
 * La interface RolDao.
 */
public interface RolDao {

	/**
	 * Obtiene el rol.
	 *
	 * @param id
	 *            el identificador
	 * @return el rol
	 */
	Rol getById(Long id);

	/**
	 * Añade el rol.
	 *
	 * @param rol
	 *            el rol
	 */
	void add(Rol rol);

	/**
	 * Elimina rol.
	 *
	 * @param id
	 *            el identificador
	 */
	void remove(Long id);

	/**
	 * Elimina roles area.
	 *
	 * @param idArea
	 *            el identificador area
	 */
	void removeByArea(final Long idArea);

	/**
	 * Actualiza rol.
	 *
	 * @param rol
	 *            el rol
	 */
	void update(Rol rol);

	/**
	 * Obtiene la lista de roles.
	 *
	 * @param idEntidad
	 *            Id entidad
	 * @return lista de roles
	 */
	List<Rol> getAll(Long idEntidad);

	/**
	 * Obtiene la lista de roles.
	 *
	 * @param idEntidad
	 *            Id entidad
	 * @param filtro
	 *            filtro
	 * @return la lista de roles
	 */
	List<Rol> getAllByFiltro(Long idEntidad, String filtro);

	/**
	 * Obtiene lista permisos por area.
	 *
	 * @param idArea
	 *            Id Area
	 * @return lista permisos
	 */
	List<Rol> getAllByArea(Long idArea);

	/**
	 * Obtiene lista de roles que tienen permiso de helpdesk.
	 *
	 * @return la lista de roles
	 */
	List<Rol> getAllByHelpDesk();

}
