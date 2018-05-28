package es.caib.sistramit.core.service.component.flujo;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Componente que permite generar una sesion.
 *
 * @author Indra
 *
 */
@Component("sesionTramitacion")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public final class SesionTramitacionImpl implements SesionTramitacion {

	@Override
	public String generarSesionTramitacion() {
		// TODO PENDIENTE
		return null;
	}

}
