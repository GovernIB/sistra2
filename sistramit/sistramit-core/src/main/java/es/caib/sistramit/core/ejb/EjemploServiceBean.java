package es.caib.sistramit.core.ejb;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.model.EjemploDto;
import es.caib.sistramit.core.api.service.EjemploService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class EjemploServiceBean implements EjemploService {

	@Autowired
	EjemploService ejemploService;

	@Override
	public EjemploDto recuperaDato() {
		return ejemploService.recuperaDato();
	}

}
