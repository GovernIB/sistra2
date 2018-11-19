package es.caib.sistrahelp.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.model.FiltroPerdidaClave;
import es.caib.sistrahelp.core.api.model.ResultadoEventoAuditoria;
import es.caib.sistrahelp.core.api.model.ResultadoPerdidaClave;
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
		List<EventoAuditoriaTramitacion> resultado = null;

		if (pFiltroBusqueda != null) {
			pFiltroBusqueda.setSoloContar(false);
		}

		final ResultadoEventoAuditoria resultadoEventoAuditoria = sistramitApiComponent
				.obtenerAuditoriaEvento(pFiltroBusqueda, pFiltroPaginacion);
		if (resultadoEventoAuditoria != null) {
			resultado = resultadoEventoAuditoria.getListaEventos();
		}

		return resultado;
	}

	@Override
	@NegocioInterceptor
	public Long countAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda) {
		Long resultado = null;

		if (pFiltroBusqueda != null) {
			pFiltroBusqueda.setSoloContar(true);
		}
		final ResultadoEventoAuditoria resultadoEventoAuditoria = sistramitApiComponent
				.obtenerAuditoriaEvento(pFiltroBusqueda, null);
		if (resultadoEventoAuditoria != null) {
			resultado = resultadoEventoAuditoria.getNumElementos();
		}

		return resultado;
	}

	@Override
	@NegocioInterceptor
	public ResultadoPerdidaClave obtenerAuditoriaTramite(final FiltroPerdidaClave pFiltroBusqueda) {
		return sistramitApiComponent.obtenerAuditoriaTramite(pFiltroBusqueda);
	}
}
