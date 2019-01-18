package es.caib.sistramit.core.service.component.system;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.exception.ServiceException;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.EventoAuditoriaTramitacion;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroEventoAuditoria;
import es.caib.sistramit.core.api.model.system.rest.interno.FiltroPaginacion;
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.service.repository.dao.AuditoriaDao;

@Component("auditoriaComponent")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AuditoriaComponentImpl implements AuditoriaComponent {

	@Autowired
	private AuditoriaDao auditoriaDao;

	@Override
	public void auditarExcepcionNegocio(final String idSesionTramitacion, final ServiceException excepcion) {
		final EventoAuditoria evento = new EventoAuditoria();
		evento.setIdSesionTramitacion(idSesionTramitacion);
		evento.setTipoEvento(TypeEvento.ERROR);
		evento.setDescripcion(excepcion.getMessage());
		evento.setCodigoError(excepcion.getClass().getSimpleName());
		evento.setTrazaError(ExceptionUtils.getStackTrace((Throwable) excepcion));
		evento.setPropiedadesEvento(excepcion.getDetallesExcepcion());
		auditoriaDao.add(evento);
	}

	@Override
	public void auditarEventoAplicacion(final EventoAuditoria evento) {
		auditoriaDao.add(evento);
	}

	@Override
	public void auditarEventosAplicacion(List<EventoAuditoria> eventos) {
		if (eventos != null) {
			for (final EventoAuditoria evento : eventos) {
				auditoriaDao.add(evento);
			}
		}
	}

	@Override
	public void auditarErrorFront(final String idSesionTramitacion, final ErrorFrontException pFrontException) {
		final EventoAuditoria evento = new EventoAuditoria();
		evento.setIdSesionTramitacion(idSesionTramitacion);
		evento.setTipoEvento(TypeEvento.ERROR);
		evento.setDescripcion(pFrontException.getMessage());
		evento.setCodigoError(pFrontException.getClass().getSimpleName());
		evento.setTrazaError(ExceptionUtils.getStackTrace(pFrontException));
		auditoriaDao.add(evento);
	}

	@Override
	public List<EventoAuditoria> recuperarLogSesionTramitacion(final String idSesionTramitacion, final Date fechaDesde,
			final Date fechaHasta, final boolean ordenAsc) {
		return auditoriaDao.retrieve(idSesionTramitacion, fechaDesde, fechaHasta, ordenAsc);
	}

	@Override
	public List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(
			final FiltroEventoAuditoria pFiltroBusqueda) {
		return auditoriaDao.retrieveByAreas(pFiltroBusqueda);
	}

	@Override
	public List<EventoAuditoriaTramitacion> recuperarLogSesionTramitacionArea(
			final FiltroEventoAuditoria pFiltroBusqueda, final FiltroPaginacion pFiltroPaginacion) {
		return auditoriaDao.retrieveByAreas(pFiltroBusqueda, pFiltroPaginacion);
	}

	@Override
	public Long contarLogSesionTramitacionArea(final FiltroEventoAuditoria pFiltroBusqueda) {
		return auditoriaDao.countByAreas(pFiltroBusqueda);
	}

}
