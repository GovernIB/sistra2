package es.caib.sistramit.core.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import es.caib.sistramit.core.api.model.system.rest.externo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.service.RestApiExternaService;

@Stateless
@Interceptors({
		SpringBeanAutowiringInterceptor.class,
		EJBExceptionInterceptor.class
})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class RestApiExternaServiceBean implements RestApiExternaService {

	@Autowired
	private RestApiExternaService restApiExternaService;

	@Override
	public List<TramitePersistencia> recuperarTramites(final FiltroTramitePersistencia pFiltro) {
		return restApiExternaService.recuperarTramites(pFiltro);
	}

	@Override
	public List<Evento> recuperarEventos(final FiltroEvento pFiltro) {
		return restApiExternaService.recuperarEventos(pFiltro);
	}

	@Override
	public String obtenerTicketAcceso(final InfoTicketAcceso pInfoTicketAcceso) {
		return restApiExternaService.obtenerTicketAcceso(pInfoTicketAcceso);
	}

	@Override
	public List<TramiteFinalizado> recuperarTramitesFinalizados(final FiltroTramiteFinalizado filtroBusqueda) {
		return restApiExternaService.recuperarTramitesFinalizados(filtroBusqueda);
	}

}
