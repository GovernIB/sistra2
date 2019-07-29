package es.caib.sistrahelp.core.api.service;

import java.util.List;

import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.types.TypeRoleAcceso;

/**
 * Servicio para verificar accesos de seguridad.
 *
 * @author Indra
 *
 */
public interface SecurityService {

	/**
	 * Obtiene usuario autenticado.
	 *
	 * @return usuario autenticado.
	 */
	public String getUsername();

	/**
	 * Obtiene la lista de roles.
	 *
	 * @return lista de roles
	 */
	public List<TypeRoleAcceso> getRoles();

	/**
	 * Obtener areas que se tiene permiso.
	 *
	 * @param Rol
	 * @return lista de areas
	 */
	public List<Area> obtenerAreas(TypeRoleAcceso rol);

}
