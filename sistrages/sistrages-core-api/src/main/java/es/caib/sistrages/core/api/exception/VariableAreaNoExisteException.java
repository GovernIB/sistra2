package es.caib.sistrages.core.api.exception;

/**
 * Excepción producida en la parte de REST.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class VariableAreaNoExisteException extends RuntimeException {

	public VariableAreaNoExisteException() {
		super();
	}

	public VariableAreaNoExisteException(final String message) {
		super(message);
	}

	public VariableAreaNoExisteException(final String message, final Throwable arg0) {
		super(message, arg0);
	}

}
