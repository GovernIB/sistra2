package es.caib.sistramit.core.ejb;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.model.comun.ResultadoProcesoProgramado;
import es.caib.sistramit.core.api.service.PurgaService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class PurgaServiceBean implements PurgaService {

	@Autowired
	private PurgaService purgaService;

	@Override
	public ResultadoProcesoProgramado purgar() {
		return purgaService.purgar();
	}

}
