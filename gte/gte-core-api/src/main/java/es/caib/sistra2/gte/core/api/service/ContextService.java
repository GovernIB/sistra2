package es.caib.sistra2.gte.core.api.service;

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
	
}
