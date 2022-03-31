package es.caib.sistrages.rest.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Elemento no existente")
public class NoExisteException   extends RuntimeException {

	/** Serial Version UID  */
	private static final long serialVersionUID = 1L;

	public NoExisteException() {
		super();
	}

	public NoExisteException(String message) {
		super(message);
	}

	public NoExisteException(String message, Throwable cause) {
		super(message, cause);
	}
}
