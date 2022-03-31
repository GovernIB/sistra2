package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Error conversion JSON.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ErrorJsonException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor DatabaseException.
	 *
	 * @param cause Causa
	 */
	public ErrorJsonException(final Exception cause) {
		super("Error conversió JSON: " + cause.getMessage(), cause);
	}

	/**
	 * Constructor DatabaseException.
	 *
	 * @param cause Causa
	 */
	public ErrorJsonException(final String cause) {
		super("Error conversió JSON: " + cause);
	}

}
