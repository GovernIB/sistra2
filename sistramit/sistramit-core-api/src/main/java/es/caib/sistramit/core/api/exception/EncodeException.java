package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Error al encode/decode.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class EncodeException extends ServiceRollbackException {

	@Override
	public TypeNivelExcepcion getNivel() {
		return TypeNivelExcepcion.FATAL;
	}

	/**
	 * Constructor EncodeException.
	 *
	 * @param cause
	 *            Causa
	 */
	public EncodeException(final Exception cause) {
		super("Error conversion: " + cause.getMessage(), cause);
	}

	/**
	 * Constructor EncodeException.
	 *
	 * @param cause
	 *            Causa
	 */
	public EncodeException(final String cause) {
		super("Error conversion: " + cause);
	}

}
