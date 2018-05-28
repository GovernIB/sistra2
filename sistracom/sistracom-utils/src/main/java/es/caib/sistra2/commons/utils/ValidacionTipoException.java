package es.caib.sistra2.commons.utils;

/**
 * Excepcion validaciones tipo.
 */
@SuppressWarnings("serial")
public final class ValidacionTipoException extends Exception {

	/**
	 * Constructor ValidacionTipoException.
	 *
	 * @param message
	 *            message
	 * @param cause
	 *            cause
	 */
	public ValidacionTipoException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor ValidacionException.
	 *
	 * @param message
	 *            message
	 */
	public ValidacionTipoException(final String message) {
		super(message);
	}
}
