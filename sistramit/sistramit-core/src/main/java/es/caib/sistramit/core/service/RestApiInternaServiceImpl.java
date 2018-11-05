package es.caib.sistramit.core.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.model.system.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.FiltrosAuditoriaTramitacion;
import es.caib.sistramit.core.api.service.RestApiInternaService;
import es.caib.sistramit.core.interceptor.NegocioInterceptor;
import es.caib.sistramit.core.service.component.system.AuditoriaComponent;

@Service
@Transactional
public class RestApiInternaServiceImpl implements RestApiInternaService {

	/** Componente auditoria. */
	@Autowired
	private AuditoriaComponent auditoriaComponent;

	/** Log. */
	private static Logger log = LoggerFactory.getLogger(RestApiInternaServiceImpl.class);

	@Override
	@NegocioInterceptor
	public List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(
			final FiltrosAuditoriaTramitacion pFiltros) {
		return auditoriaComponent.recuperarLogSesionTramitacionArea(pFiltros);
	}

}
