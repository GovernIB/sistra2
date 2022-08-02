package es.caib.sistramit.core.service.component.integracion;

import java.io.IOException;

import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

// TODO ERROR HANDLER SI SE QUISIERA INTERPRETAR ERROR LLAMADA API SI
// EN LA PARTE SERVIDOR SE CAPTURA ERROR Y SE DEVUELVE ERROR COMO JSON
// EN LA PARTE CLIENTE HABRIA QUE AÑADIR EL HANDLER A LAS LLAMADAS: restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(final ClientHttpResponse httpResponse) throws IOException {

		return (httpResponse.getStatusCode().series() == Series.CLIENT_ERROR
				|| httpResponse.getStatusCode().series() == Series.SERVER_ERROR);
	}

	@Override
	public void handleError(final ClientHttpResponse httpResponse) throws IOException {

		// SE PODRIA CAPTURAR INFO JSON ERROR Y GENERAR EXC NEGOCIO
		// (LA CLASE QUE MODELIDA JSON ERROR DEBERIA ESTAR EN EL MODEL API)

		throw new RuntimeException("Client error xxxx");

		/*
		 * if (httpResponse.getStatusCode().series() == Series.SERVER_ERROR) {
		 *
		 * // handle SERVER_ERROR
		 *
		 *
		 * } else if (httpResponse.getStatusCode().series() == Series.CLIENT_ERROR) {
		 *
		 * // handle CLIENT_ERROR if (httpResponse.getStatusCode() ==
		 * HttpStatus.NOT_FOUND) { throw new NotFoundException(); }
		 *
		 * }
		 */

	}
}