package es.caib.sistrages.core.api.exception;

/**
 *
 * Error al encode/decode.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class EncodeException extends ServiceRollbackException {

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
