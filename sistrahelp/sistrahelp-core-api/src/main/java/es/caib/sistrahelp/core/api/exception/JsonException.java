package es.caib.sistrahelp.core.api.exception;

/**
 *
 * Excepcion convirtiendo en JSON.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class JsonException extends ServiceRollbackException {

	/**
	 * Constructor ErrorNoControladoException.
	 *
	 * @param cause
	 *            Causa
	 */
	public JsonException(final Throwable cause) {
		super("Error clonando objeto: " + cause.getMessage(), cause);
	}

	/**
	 * Constructor.
	 *
	 * @param message
	 *            mensaje
	 */
	public JsonException(final String message) {
		super(message);
	}

}
