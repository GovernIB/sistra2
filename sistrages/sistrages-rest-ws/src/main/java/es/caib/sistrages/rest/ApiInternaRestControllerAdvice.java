package es.caib.sistrages.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;

import es.caib.sistrages.rest.exception.ExceptionJsonResponse;
import es.caib.sistrages.rest.exception.NoExisteException;

/**
 * Control errores rest.
 *
 * @author Indra
 *
 */
// TODO SI SE QUISIERA CONTROLAR ERRORES GENERADOS POR API Y GENERAR JSON CON
// INFOR ERROR ( ExceptionJsonResponse DEBERIA ESTAR EN MODEL API)
// @RestControllerAdvice
public class ApiInternaRestControllerAdvice {

	@ExceptionHandler({ NoExisteException.class })
	public ResponseEntity<ExceptionJsonResponse> handleNotFound(final Exception ce) {
		return new ResponseEntity<>(new ExceptionJsonResponse("No existeix element"), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionJsonResponse> handleException(final Exception e) {
		return new ResponseEntity<>(new ExceptionJsonResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
