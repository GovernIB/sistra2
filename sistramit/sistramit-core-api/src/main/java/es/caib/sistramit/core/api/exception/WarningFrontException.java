package es.caib.sistramit.core.api.exception;

/**
 *
 * Excepcion de bajo nivel generada en el front.
 *
 */
@SuppressWarnings("serial")
public final class WarningFrontException extends ErrorFrontException {

	/**
	 * Genera excepción FrontWarning estableciendo un mensaje .
	 *
	 * @param message
	 *            Mensaje
	 */
	public WarningFrontException(final String message) {
		super(message);
	}

	/**
	 * Constructor FrontWarning.
	 *
	 * @param message
	 *            Mensaje
	 * @param excep
	 *            Excepcion origen
	 */
	public WarningFrontException(final String message, final Exception excep) {
		super(message, excep);
	}

}
