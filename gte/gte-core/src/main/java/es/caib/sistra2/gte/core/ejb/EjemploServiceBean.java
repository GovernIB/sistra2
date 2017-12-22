package es.caib.sistra2.gte.core.ejb;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistra2.gte.core.api.model.EjemploDto;
import es.caib.sistra2.gte.core.api.service.EjemploService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class EjemploServiceBean implements EjemploService {

	@Autowired
	EjemploService ejemploService;

	@Override
	public EjemploDto recuperaDato() {
		return ejemploService.recuperaDato("EJB");
	}

	@Override
	public EjemploDto recuperaDato(String origen) {
		return ejemploService.recuperaDato(origen);
	}

}
