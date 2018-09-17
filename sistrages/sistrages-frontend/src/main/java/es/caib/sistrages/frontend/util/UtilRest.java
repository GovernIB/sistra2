package es.caib.sistrages.frontend.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import es.caib.sistrages.frontend.model.ResultadoError;
import es.caib.sistramit.rest.api.interna.RInvalidacion;
import es.caib.sistramit.rest.api.util.JsonException;
import es.caib.sistramit.rest.api.util.JsonUtil;

public class UtilRest {

	/**
	 * Refrescar.
	 *
	 * @param urlBase
	 *            url base
	 * @param usuario
	 *            usuario
	 * @param pwd
	 *            pwd
	 * @param tipo
	 *            tipo
	 * @param identificador
	 *            identificador
	 * @return 1 si se ha ejecutado correctamente, 0 si ha habido algun error.
	 */
	public static ResultadoError refrescar(final String urlBase, final String usuario, final String pwd,
			final String tipo, final String identificador) {
		final RestTemplate restTemplate = new RestTemplate();
		final ResultadoError resultado = new ResultadoError(0, null);

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(usuario, pwd));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final RInvalidacion inv = new RInvalidacion();
		inv.setTipo(tipo);
		inv.setIdentificador(identificador);
		String invJSON;
		try {
			invJSON = JsonUtil.toJson(inv);
		} catch (final JsonException e) {
			resultado.setMensaje(e.getMessage());
			return resultado;
		}

		final MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("invalidacion", invJSON);

		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
				headers);

		ResponseEntity<Boolean> response = null;
		try {
			response = restTemplate.postForEntity(urlBase + "/invalidacion", request, Boolean.class);

			switch (response.getStatusCodeValue()) {
			case 200:
				resultado.setCodigo(1);
				break;
			}
		} catch (final RestClientException e) {
			resultado.setMensaje(e.getMessage());
		}

		return resultado;
	}
}
