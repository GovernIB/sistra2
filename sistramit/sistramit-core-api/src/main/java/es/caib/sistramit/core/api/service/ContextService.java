package es.caib.sistramit.core.api.service;

/**
 * EJB que permite a los SpringBeans acceder al contexto para verificar usuario.
 * 
 * @author Indra
 *
 */
public interface ContextService {

	public String getUsername();
	
	public boolean hashRole(String role);
	
}
