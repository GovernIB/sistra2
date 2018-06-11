package es.caib.sistramit.core.service.component.system;

import java.util.Date;
import java.util.List;

import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.exception.ServiceException;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;

/**
 * Componente para generar auditoria.
 *
 * @author Indra
 *
 */
public interface AuditoriaComponent {

    /**
     * Audita excepción de la capa de servicios.
     *
     * @param excepcion
     *            Excepción
     * @param idSesionTramitacion
     *            idSesionTramitacion
     */
    void auditarExcepcionNegocio(String idSesionTramitacion,
            ServiceException excepcion);

    /**
     * Audita evento propio de la aplicación.
     *
     * @param evento
     *            Evento
     * @param idSesionTramitacion
     *            idSesionTramitacion
     *
     */
    void auditarEventoAplicacion(String idSesionTramitacion,
            EventoAuditoria evento);

    /**
     * Permite auditar en el log un error generado en el front.
     *
     * @param idSesionTramitacion
     *            idSesionTramitacion
     * @param pFrontException
     *            Excepcion
     */
    void auditarErrorFront(String idSesionTramitacion,
            ErrorFrontException pFrontException);

    /**
     * Permite recuperar la lista de eventos internos parametrizada por fecha e
     * identificador de sesión.
     *
     * @param fechaDesde
     *            Fecha inicio (opcional)
     * @param fechaHasta
     *            Fecha fin (opcional)
     * @param idSesionTramitacion
     *            Id sesion tramitacion
     * @param ordenAsc
     *            Indica si orden ascendente de fecha evento (true) o
     *            descendente (false).
     *
     * @return Lista de eventos asociados a la sesión.
     */
    List<EventoAuditoria> recuperarLogSesionTramitacion(
            String idSesionTramitacion, Date fechaDesde, Date fechaHasta,
            boolean ordenAsc);

}