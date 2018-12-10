package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada al validar si se permite realizar el registro en el paso
 * registrar.
 *
 */
@SuppressWarnings("serial")
public final class RegistroNoPermitidoException extends ServiceRollbackException {

	/*
	 * RegistroNoPermitidoException WARNING.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.WARNING;
	}

	/**
	 * Constructor RegistroNoPermitidoException.
	 *
	 * @param message
	 *            Mensaje
	 * @param cause
	 *            Causa
	 */
	public RegistroNoPermitidoException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor RegistroNoPermitidoException.
	 *
	 * @param message
	 *            Mensaje
	 */
	public RegistroNoPermitidoException(final String message) {
		super(message);
	}

}
