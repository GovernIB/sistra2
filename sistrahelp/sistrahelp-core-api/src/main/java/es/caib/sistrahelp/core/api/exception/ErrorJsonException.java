package es.caib.sistrahelp.core.api.exception;

/**
 *
 * Error conversion JSON.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ErrorJsonException extends ServiceRollbackException {

	/**
	 * Constructor DatabaseException.
	 *
	 * @param cause
	 *            Causa
	 */
	public ErrorJsonException(final Exception cause) {
		super("Error conversion JSON: " + cause.getMessage(), cause);
	}

	/**
	 * Constructor DatabaseException.
	 *
	 * @param cause
	 *            Causa
	 */
	public ErrorJsonException(final String cause) {
		super("Error conversion JSON: " + cause);
	}

}
