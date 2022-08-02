package es.caib.sistrages.rest.exception;

public class ExceptionJsonResponse {

	/** Mensaje error. */
	private String errorMessage;

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public ExceptionJsonResponse(final String message) {
		super();
		this.errorMessage = message;
	}

	/** Constructor. */
	public ExceptionJsonResponse() {
		super();
	}

	/**
	 * Método de acceso a message.
	 * 
	 * @return message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Método para establecer message.
	 * 
	 * @param message
	 *                    message a establecer
	 */
	public void setErrorMessage(final String message) {
		this.errorMessage = message;
	}

}
