package es.caib.sistrahelp.core.ejb;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistrahelp.core.api.service.ConfiguracionService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class ConfiguracionServiceBean implements ConfiguracionService {

	@Autowired
	ConfiguracionService configuracionService;

}
