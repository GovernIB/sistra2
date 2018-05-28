package es.caib.sistramit.core.service.component.system;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.exception.ServiceException;
import es.caib.sistramit.core.service.model.system.EventoAplicacion;

@Component("auditoriaComponent")
@Scope(value = "prototype")
public class AuditoriaComponentImpl implements AuditoriaComponent {

	@Override
	public void auditarExcepcionNegocio(final String idSesionTramitacion, final ServiceException excepcion) {
		// TODO PENDIENTE

	}

	@Override
	public void auditarEventoAplicacion(final EventoAplicacion evento) {
		// TODO PENDIENTE

	}

	@Override
	public void auditarErrorFront(final String pIdSesionTramitacion, final ErrorFrontException pFrontException) {
		// TODO PENDIENTE
	}

}
