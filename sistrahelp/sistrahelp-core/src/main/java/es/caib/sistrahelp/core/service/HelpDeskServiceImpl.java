package es.caib.sistrahelp.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaPago;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.model.FiltroPerdidaClave;
import es.caib.sistrahelp.core.api.model.FiltroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.PagoAuditoria;
import es.caib.sistrahelp.core.api.model.PersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.ResultadoAuditoriaDetallePago;
import es.caib.sistrahelp.core.api.model.ResultadoAuditoriaPago;
import es.caib.sistrahelp.core.api.model.ResultadoAuditoriaPersistencia;
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
		FiltroAuditoriaTramitacion filtroAuditoriaTramitacion = null;

		filtroAuditoriaTramitacion = new FiltroAuditoriaTramitacion(pFiltroBusqueda);
		filtroAuditoriaTramitacion.setSoloContar(false);

		final ResultadoEventoAuditoria resultadoEventoAuditoria = sistramitApiComponent
				.obtenerAuditoriaEvento(filtroAuditoriaTramitacion, pFiltroPaginacion);
		if (resultadoEventoAuditoria != null) {
			resultado = resultadoEventoAuditoria.getListaEventos();
		}

		return resultado;
	}

	@Override
	@NegocioInterceptor
	public Long countAuditoriaEvento(final FiltroAuditoriaTramitacion pFiltroBusqueda) {
		Long resultado = null;
		FiltroAuditoriaTramitacion filtroAuditoriaTramitacion = null;

		filtroAuditoriaTramitacion = new FiltroAuditoriaTramitacion(pFiltroBusqueda);
		filtroAuditoriaTramitacion.setSoloContar(true);

		final ResultadoEventoAuditoria resultadoEventoAuditoria = sistramitApiComponent
				.obtenerAuditoriaEvento(filtroAuditoriaTramitacion, null);
		if (resultadoEventoAuditoria != null) {
			resultado = resultadoEventoAuditoria.getNumElementos();
		}

		return resultado;
	}

	@Override
	@NegocioInterceptor
	public ResultadoPerdidaClave obtenerClaveTramitacion(final FiltroPerdidaClave pFiltroBusqueda) {
		return sistramitApiComponent.obtenerClaveTramitacion(pFiltroBusqueda);
	}

	@Override
	@NegocioInterceptor
	public List<PagoAuditoria> obtenerAuditoriaPago(final FiltroAuditoriaPago pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		List<PagoAuditoria> resultado = null;
		FiltroAuditoriaPago filtroAuditoriaPago = null;

		filtroAuditoriaPago = new FiltroAuditoriaPago(pFiltroBusqueda);
		filtroAuditoriaPago.setSoloContar(false);

		final ResultadoAuditoriaPago resultadoAuditoriaPago = sistramitApiComponent
				.obtenerAuditoriaPago(filtroAuditoriaPago, pFiltroPaginacion);

		if (resultadoAuditoriaPago != null) {
			resultado = resultadoAuditoriaPago.getListaPagos();
		}

		return resultado;

	}

	@Override
	@NegocioInterceptor
	public Long countAuditoriaPago(final FiltroAuditoriaPago pFiltroBusqueda) {
		Long resultado = null;
		FiltroAuditoriaPago filtroAuditoriaPago = null;

		filtroAuditoriaPago = new FiltroAuditoriaPago(pFiltroBusqueda);
		filtroAuditoriaPago.setSoloContar(true);

		final ResultadoAuditoriaPago resultadoAuditoriaPago = sistramitApiComponent
				.obtenerAuditoriaPago(filtroAuditoriaPago, null);

		if (resultadoAuditoriaPago != null) {
			resultado = resultadoAuditoriaPago.getNumElementos();
		}

		return resultado;
	}

	@Override
	@NegocioInterceptor
	public ResultadoAuditoriaDetallePago obtenerAuditoriaDetallePago(final Long pIdPago) {
		return sistramitApiComponent.obtenerAuditoriaDetallePago(pIdPago);
	}

	@Override
	public Long contarPersistenciaArea(final FiltroPersistenciaAuditoria pFiltroBusqueda) {
		Long resultado = null;
		FiltroPersistenciaAuditoria filtroPersistencia = null;

		filtroPersistencia = new FiltroPersistenciaAuditoria(pFiltroBusqueda);
		filtroPersistencia.setSoloContar(true);

		final ResultadoAuditoriaPersistencia resultadoPersistencia = sistramitApiComponent
				.obtenerAuditoriaPersistencia(filtroPersistencia, null);

		if (resultadoPersistencia != null) {
			resultado = resultadoPersistencia.getNumElementos();
		}

		return resultado;
	}

	@Override
	public List<PersistenciaAuditoria> recuperarPersistenciaArea(final FiltroPersistenciaAuditoria pFiltroBusqueda,
			final FiltroPaginacion pFiltroPaginacion) {
		List<PersistenciaAuditoria> resultado = null;
		FiltroPersistenciaAuditoria filtroPersistencia = null;

		filtroPersistencia = new FiltroPersistenciaAuditoria(pFiltroBusqueda);
		filtroPersistencia.setSoloContar(false);

		final ResultadoAuditoriaPersistencia resultadoPersistencia = sistramitApiComponent
				.obtenerAuditoriaPersistencia(filtroPersistencia, pFiltroPaginacion);

		if (resultadoPersistencia != null) {
			resultado = resultadoPersistencia.getListaPersistencia();
		}

		return resultado;
	}
}
