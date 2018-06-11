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
import es.caib.sistramit.core.api.model.system.types.TypeEvento;
import es.caib.sistramit.core.service.repository.dao.AuditoriaDao;

@Component("auditoriaComponent")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AuditoriaComponentImpl implements AuditoriaComponent {

    @Autowired
    private AuditoriaDao auditoriaDao;

    @Override
    public void auditarExcepcionNegocio(String idSesionTramitacion,
            ServiceException excepcion) {
        final EventoAuditoria evento = new EventoAuditoria();
        evento.setTipoEvento(TypeEvento.ERROR);
        evento.setDescripcion(excepcion.getMessage());
        evento.setCodigoError(excepcion.getClass().getSimpleName());
        evento.setTrazaError(
                ExceptionUtils.getStackTrace((Throwable) excepcion));
        evento.setPropiedadesEvento(excepcion.getDetallesExcepcion());
        auditoriaDao.add(evento, idSesionTramitacion);
    }

    @Override
    public void auditarEventoAplicacion(String idSesionTramitacion,
            EventoAuditoria evento) {
        auditoriaDao.add(evento, idSesionTramitacion);
    }

    @Override
    public void auditarErrorFront(String idSesionTramitacion,
            ErrorFrontException pFrontException) {
        final EventoAuditoria evento = new EventoAuditoria();
        evento.setTipoEvento(TypeEvento.ERROR);
        evento.setDescripcion(pFrontException.getMessage());
        evento.setCodigoError(pFrontException.getClass().getSimpleName());
        evento.setTrazaError(ExceptionUtils.getStackTrace(pFrontException));
        auditoriaDao.add(evento, idSesionTramitacion);
    }

    @Override
    public List<EventoAuditoria> recuperarLogSesionTramitacion(
            String idSesionTramitacion, Date fechaDesde, Date fechaHasta,
            boolean ordenAsc) {
        return auditoriaDao.retrieve(idSesionTramitacion, fechaDesde,
                fechaHasta, ordenAsc);
    }

}
