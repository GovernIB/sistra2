package es.caib.sistramit.rest.test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import es.caib.sistramit.rest.api.interna.RInvalidacion;
import es.caib.sistramit.rest.api.util.JsonException;
import es.caib.sistramit.rest.api.util.JsonUtil;

/**
 * Test acceso rest interna.
 *
 * @author Indra
 *
 */
public class TestRestInternaClient {
    private final static String urlBase = "http://localhost:8080/sistramit/api/rest/interna";

    public static void main(String[] args) throws JsonException {

        testInvalidacion();

    }

    private static void testInvalidacion() throws JsonException {

        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors()
                .add(new BasicAuthorizationInterceptor("api-stt", "1234"));

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final RInvalidacion inv = new RInvalidacion();
        inv.setTipo("C");
        final String invJSON = JsonUtil.toJson(inv);

        System.out.println(invJSON);

        final MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("invalidacion", invJSON);

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
                map, headers);

        final ResponseEntity<Boolean> response = restTemplate.postForEntity(
                urlBase + "/invalidacion", request, Boolean.class);

        System.out.println("Invalidacion realizada");

    }

}
