package es.caib.sistrahelp.core.ejb;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrahelp.core.api.model.Sesion;
import es.caib.sistrahelp.core.api.service.SystemService;

/**
 * Servicios de sistema.
 *
 * @author Indra
 *
 */
@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SystemServiceBean implements SystemService {

	/** System service. */
	@Autowired
	private SystemService systemService;

	@Override
	@PermitAll
	public Sesion getSesion(final String pUserName) {
		return systemService.getSesion(pUserName);
	}

	@Override
	@PermitAll
	public void updateSesionPropiedades(String pUserName, String pPropiedades) {
		systemService.updateSesionPropiedades(pUserName, pPropiedades);
	}

}
