package es.caib.sistramit.core.service.component.system;

import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.exception.ServiceException;
import es.caib.sistramit.core.service.model.system.EventoAplicacion;

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
	 *            Id sesion tramitacion asociada
	 */
	void auditarExcepcionNegocio(String idSesionTramitacion, ServiceException excepcion);

	/**
	 * Audita evento propio de la aplicación.
	 *
	 * @param evento
	 *            Evento
	 *
	 */
	void auditarEventoAplicacion(EventoAplicacion evento);

	/**
	 * Permite auditar en el log un error generado en el front.
	 *
	 * @param pIdSesionTramitacion
	 *            Id sesion tramitacion
	 * @param pFrontException
	 *            Excepcion
	 */
	void auditarErrorFront(String pIdSesionTramitacion, ErrorFrontException pFrontException);

}