package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.Rol;

/**
 * RolService Service.
 */
public interface RolService {

	/**
	 * Obtiene el Rol.
	 *
	 * @param idRol
	 *            el identificador
	 * @return el Rol
	 */
	Rol getRol(Long idRol);

	/**
	 * Añade Rol.
	 *
	 * @param idRol
	 *            el Rol
	 */
	void addRol(Rol idRol);

	/**
	 * Elimina rol.
	 *
	 * @param idRol
	 *            el identificador
	 * @return true, si se realiza correctamente
	 */
	boolean removeRol(Long idRol);

	/**
	 * Actualiza Rol.
	 *
	 * @param rol
	 *            el Rol
	 */
	void updateRol(Rol rol);

	/**
	 * Lista de roles.
	 *
	 * @param idEntidad
	 *            id Entidad
	 * @param filtro
	 *            filtro busqueda
	 * @return la lista de roles
	 */
	List<Rol> listRol(Long idEntidad, String filtro);

}
