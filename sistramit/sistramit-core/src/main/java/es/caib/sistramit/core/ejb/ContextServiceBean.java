package es.caib.sistramit.core.ejb;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import es.caib.sistramit.core.api.service.ContextService;


@Stateless
public class ContextServiceBean implements ContextService {

	/** Inyeccion contexto EJB */
	@Resource
	private SessionContext ctx;
	
	@PermitAll
	public String getUsername() {
		return ctx.getCallerPrincipal().getName();
	}
	
	@PermitAll
	public boolean hashRole(String role) {
		return ctx.isCallerInRole(role);
	}

}
