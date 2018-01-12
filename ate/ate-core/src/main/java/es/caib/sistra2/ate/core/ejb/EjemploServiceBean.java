package es.caib.sistra2.ate.core.ejb;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistra2.ate.core.api.model.EjemploDto;
import es.caib.sistra2.ate.core.api.service.EjemploService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class EjemploServiceBean implements EjemploService {

	@Autowired
	EjemploService ejemploService;

	@Override
	@RolesAllowed("STR2_TEST")
	public EjemploDto recuperaDato() {
		return ejemploService.recuperaDato("EJB");
	}

	@Override
	@RolesAllowed("STR2_TEST")
	public EjemploDto recuperaDato(String origen) {
		return ejemploService.recuperaDato(origen);
	}

}
