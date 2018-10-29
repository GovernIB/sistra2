package es.caib.sistramit.core.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.service.RestApiInternaService;
import es.caib.sistramit.rest.api.interna.RFiltrosAuditoria;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class RestApiInternaServiceBean implements RestApiInternaService {

	@Autowired
	private RestApiInternaService restApiInternaService;

	@Override
	public List<EventoAuditoria> recuperarLogSesionTramitacionArea(final RFiltrosAuditoria pFiltros) {
		return restApiInternaService.recuperarLogSesionTramitacionArea(pFiltros);
	}

}
