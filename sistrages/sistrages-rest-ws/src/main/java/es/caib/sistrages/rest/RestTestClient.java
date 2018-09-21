package es.caib.sistrages.rest;

import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistrages.rest.api.util.JsonException;
import es.caib.sistrages.rest.api.util.JsonUtil;
import es.caib.sistrages.rest.api.util.XTestJson;

public class RestTestClient {

	private final static String urlBase = "http://localhost:8080/sistrages/api/rest/interna";

	public static void main(final String[] args) throws JsonException {

		// test1();

		test2();

	}

	public static void test1() {
		final RestTemplate restTemplate = new RestTemplate();

		final String plainCreds = "api-stg:1234";
		final byte[] plainCredsBytes = plainCreds.getBytes();
		final byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
		final String base64Creds = new String(base64CredsBytes);

		final HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);

		final HttpEntity<String> request = new HttpEntity<>(headers);
		final ResponseEntity<RVersionTramite> response = restTemplate.exchange(urlBase + "/dominioFuenteDatos/D1",
				HttpMethod.POST, request, RVersionTramite.class);

		final RVersionTramite vt = response.getBody();
		System.out.println(vt.getIdentificador());
	}

	public static void test2() throws JsonException {
		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("api-stg", "1234"));

		// Configuracion global
		final RConfiguracionGlobal c = restTemplate.getForObject(urlBase + "/configuracionGlobal",
				RConfiguracionGlobal.class);
		System.out.println("ConfiguracionGlobal: props " + c.getPropiedades().getParametros().size());

		// Definicion entidad
		final RConfiguracionEntidad e = restTemplate.getForObject(urlBase + "/entidad/E1", RConfiguracionEntidad.class);
		System.out.println("Entidad: " + e.getIdentificador());

		// Avisos entidad
		final RAvisosEntidad avisos = restTemplate.getForObject(urlBase + "/entidad/E1/avisos", RAvisosEntidad.class);
		System.out.println("Avisos Entidad: " + avisos.getAvisos().size());

		// Definicion version
		final RVersionTramite vt = restTemplate.getForObject(urlBase + "/tramite/ID1/1/es", RVersionTramite.class);
		System.out.println("Version tramite: " + vt.getIdentificador());

		// Fuente datos
		testFD();

	}

	private static void testFD() throws JsonException {

		final RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("api-stg", "1234"));

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final RListaParametros lp = XTestJson.crearListaParametros();
		final String parametrosJSON = JsonUtil.toJson(lp);

		final MultiValueMap<String, String> map = new LinkedMultiValueMap();
		map.add("parametros", parametrosJSON);

		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
				headers);

		final ResponseEntity<RValoresDominio> response = restTemplate.postForEntity(urlBase + "/dominioFuenteDatos/D1",
				request, RValoresDominio.class);

		final RValoresDominio vd = response.getBody();

		System.out.println("Valores dominio: filas " + vd.getNumeroFilas());

	}

}
