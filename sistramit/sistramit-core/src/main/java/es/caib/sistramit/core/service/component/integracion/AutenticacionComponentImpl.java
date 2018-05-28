package es.caib.sistramit.core.service.component.integracion;

import org.springframework.stereotype.Component;

import es.caib.sistramit.core.service.model.integracion.DatosAutenticacionUsuario;

/**
 * Implementación acceso componente autenticación.
 *
 * @author Indra
 *
 */
@Component("autenticacionComponent")
public final class AutenticacionComponentImpl implements AutenticacionComponent {

	// TODO PENDIENTE IMPLEMENTAR

	@Override
	public String iniciarSesionAutenticacion(final String codigoEntidad, final String idioma, final String metodos,
			final String qaa, final String callback, final boolean pDebugEnabled) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DatosAutenticacionUsuario validarTicketAutenticacion(final String pTicket, final boolean pDebug) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String iniciarSesionLogout(final String codigoEntidad, final String pIdioma, final String pCallback,
			final boolean pDebugEnabled) {
		// TODO Auto-generated method stub
		return null;
	}

}
