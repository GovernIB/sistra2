package es.caib.sistrages.core.api.exception;

/**
 * Excepción producida en la parte de REST.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class RestException extends RuntimeException {

	public RestException() {
		super();
	}

	public RestException(final String message) {
		super(message);
	}

	public RestException(final String message, final Throwable arg0) {
		super(message, arg0);
	}

}
