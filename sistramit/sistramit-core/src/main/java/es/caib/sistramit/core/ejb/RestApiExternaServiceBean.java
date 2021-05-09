package es.caib.sistramit.core.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import es.caib.sistramit.core.api.model.system.rest.externo.Evento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroEvento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramiteFinalizado;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramitePersistencia;
import es.caib.sistramit.core.api.model.system.rest.externo.InfoTicketAcceso;
import es.caib.sistramit.core.api.model.system.rest.externo.TramiteFinalizado;
import es.caib.sistramit.core.api.model.system.rest.externo.TramitePersistencia;
import es.caib.sistramit.core.api.service.RestApiExternaService;

@Stateless
@Interceptors(SpringBeanAutowiringInterceptor.class)
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
