package es.caib.sistra2.gte.core.ejb;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import es.caib.sistra2.gte.core.api.service.ContextService;


/**
 * EJB que permite a los SpringBeans acceder al contexto para verificar usuario.
 * 
 * @author Indra
 *
 */
@Stateless
public class ContextServiceBean implements ContextService {

	/** Inyeccion contexto EJB */
	@Resource
	private SessionContext ctx;
	
	@Override
	@PermitAll
	public String getUsername() {
		return ctx.getCallerPrincipal().getName();
	}
	
	@Override
	@PermitAll
	public boolean hashRole(String role) {
		return ctx.isCallerInRole(role);
	}

}
