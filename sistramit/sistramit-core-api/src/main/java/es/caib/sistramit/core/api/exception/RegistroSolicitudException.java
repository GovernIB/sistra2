package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada al registrar una solicitud.
 *
 */
@SuppressWarnings("serial")
public final class RegistroSolicitudException extends ServiceRollbackException {

	/*
	 * RegistroSolicitudException ERROR.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.ERROR;
	}

	/**
	 * Constructor RegistroNoPermitidoException.
	 *
	 * @param message
	 *            Mensaje
	 * @param cause
	 *            Causa
	 */
	public RegistroSolicitudException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor RegistroNoPermitidoException.
	 *
	 * @param message
	 *            Mensaje
	 */
	public RegistroSolicitudException(final String message) {
		super(message);
	}

}
