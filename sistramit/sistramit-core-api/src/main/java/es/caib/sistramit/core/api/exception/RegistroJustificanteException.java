package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada al obtener el justificante de registro.
 *
 */
@SuppressWarnings("serial")
public final class RegistroJustificanteException extends ServiceRollbackException {

	/*
	 * RegistroJustificanteException ERROR.
	 */
	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.ERROR;
	}

	/**
	 * Constructor RegistroJustificanteException.
	 *
	 * @param message
	 *            Mensaje
	 * @param cause
	 *            Causa
	 */
	public RegistroJustificanteException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor RegistroJustificanteException.
	 *
	 * @param message
	 *            Mensaje
	 */
	public RegistroJustificanteException(final String message) {
		super(message);
	}

}
