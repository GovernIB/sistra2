package es.caib.sistrahelp.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.core.interceptor.NegocioInterceptor;
import es.caib.sistrahelp.core.service.component.SistramitApiComponent;

@Service
@Transactional
public class HelpDeskServiceImpl implements HelpDeskService {

	@Autowired
	private SistramitApiComponent sistramitApiComponent;

	@Override
	@NegocioInterceptor
	public List<EventoAuditoriaTramitacion> obtenerAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		return sistramitApiComponent.obtenerAuditoriaEvento(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	public Long obtenerAuditoriaEventoCount(final FiltroAuditoriaTramitacion pFiltroBusqueda) {
		return sistramitApiComponent.obtenerAuditoriaEventoCount(pFiltroBusqueda);
	}
}
