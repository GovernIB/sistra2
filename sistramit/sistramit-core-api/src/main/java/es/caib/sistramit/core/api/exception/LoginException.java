package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada al autenticar usuario.
 *
 */
@SuppressWarnings("serial")
public final class LoginException extends ServiceRollbackException {

	/*
	 * LoginException FATAL.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor LoginException.
	 *
	 * @param message
	 *            Mensaje
	 * @param cause
	 *            Causa
	 */
	public LoginException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor LoginException.
	 *
	 * @param message
	 *            Mensaje
	 */
	public LoginException(final String message) {
		super(message);
	}

}
