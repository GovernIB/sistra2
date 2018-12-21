package es.caib.sistramit.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.model.system.rest.externo.Evento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroEvento;
import es.caib.sistramit.core.api.model.system.rest.externo.FiltroTramitePersistencia;
import es.caib.sistramit.core.api.model.system.rest.externo.InfoTicketAcceso;
import es.caib.sistramit.core.api.model.system.rest.externo.TramitePersistencia;
import es.caib.sistramit.core.api.service.RestApiExternaService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.system.RestApiExternaComponent;

@Service
@Transactional
public class RestApiExternaServiceImpl implements RestApiExternaService {

	@Autowired
	private RestApiExternaComponent restApiExternaComponent;

	/** Log. */
	private static Logger log = LoggerFactory.getLogger(RestApiExternaServiceImpl.class);

	@Override
	@NegocioInterceptor
	public List<TramitePersistencia> recuperarTramites(final FiltroTramitePersistencia pFiltro) {
		return restApiExternaComponent.recuperarTramites(pFiltro);
	}

	@Override
	@NegocioInterceptor
	public List<Evento> recuperarEventos(final FiltroEvento pFiltro) {
		return restApiExternaComponent.recuperarEventos(pFiltro);
	}

	@Override
	@NegocioInterceptor
	public String obtenerTicketAcceso(final InfoTicketAcceso pInfoTicketAcceso) {
		return restApiExternaComponent.obtenerTicketAcceso(pInfoTicketAcceso);
	}

}
