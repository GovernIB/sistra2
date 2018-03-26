package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;

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
	 * Verifica si es usuario Superadministrador.
	 *
	 * @return boolean.
	 */
	public boolean isSuperAdministrador();

	/**
	 * Verifica si es administrador de la entidad.
	 *
	 * @param codigoEntidad
	 *            código entidad
	 * @return boolean
	 */
	public boolean isAdministradorEntidad(long codigoEntidad);

	/**
	 * Verifica si es desarrollador de la entidad (tiene algún role asocidado a la
	 * entidad).
	 *
	 * @param codigoEntidad
	 *            código entidad
	 * @return boolean
	 */
	public boolean isDesarrolladorEntidad(long codigoEntidad);

	/**
	 * Obtiene permisos para el desarrollador en el area.
	 *
	 * @param codigoArea
	 *            código área
	 * @return boolean
	 */
	public List<TypeRolePermisos> getPermisosDesarrolladorEntidad(long codigoArea);

	/**
	 * En caso de ser un administrador de entidad recupera las entidades asociadas.
	 *
	 * @return lista entidades
	 */
	public List<Entidad> getEntidadesAdministrador();

	/**
	 * En caso de ser un administrador de entidad recupera las entidades asociadas.
	 *
	 * @return lista entidades
	 */
	public List<Entidad> getEntidadesDesarrollador();

}
