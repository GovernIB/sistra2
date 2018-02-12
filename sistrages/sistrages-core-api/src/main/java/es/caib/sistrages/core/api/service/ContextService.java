package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;

/**
 * EJB que permite a los SpringBeans acceder al contexto para verificar usuario
 *
 * @author Indra
 *
 */
public interface ContextService {

	/** Obtiene usuario autenticado.
	 *
	 * @return usuario autenticado.
	 */
	public String getUsername();

	/**
	 * Verifica si tiene el role.
	 * @param role role
	 * @return boolean si tiene el role
	 */
	public boolean hashRole(String role);

	/**
	 * Obtiene la lista de roles
	 * @return lista de roles
	 */
	public List<TypeRoleAcceso> getRoles();

}
