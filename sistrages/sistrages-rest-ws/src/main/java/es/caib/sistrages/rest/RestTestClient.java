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

import es.caib.sistrages.rest.api.AvisosEntidad;
import es.caib.sistrages.rest.api.ConfiguracionGlobal;
import es.caib.sistrages.rest.api.ConfiguracionEntidad;
import es.caib.sistrages.rest.api.ListaParametros;
import es.caib.sistrages.rest.api.ValoresDominio;
import es.caib.sistrages.rest.api.VersionTramite;
import es.caib.sistrages.rest.api.util.JsonException;
import es.caib.sistrages.rest.api.util.JsonUtil;
import es.caib.sistrages.rest.api.util.XTestJson;

public class RestTestClient {

    private final static String urlBase = "http://localhost:8080/sistrages/api/rest/asistente";

    public static void main(String[] args) throws JsonException {

        // test1();

        test2();

    }

    public static void test1() {
        final RestTemplate restTemplate = new RestTemplate();

        final String plainCreds = "api-stg:1234";
        final byte[] plainCredsBytes = plainCreds.getBytes();
        final byte[] base64CredsBytes = Base64.getEncoder()
                .encode(plainCredsBytes);
        final String base64Creds = new String(base64CredsBytes);

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        final HttpEntity<String> request = new HttpEntity<String>(headers);
        final ResponseEntity<VersionTramite> response = restTemplate.exchange(
                urlBase + "/dominioFuenteDatos/D1", HttpMethod.POST, request,
                VersionTramite.class);

        final VersionTramite vt = response.getBody();
        System.out.println(vt.getIdentificador());
    }

    public static void test2() throws JsonException {
        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors()
                .add(new BasicAuthorizationInterceptor("api-stg", "1234"));

        // Configuracion global
        final ConfiguracionGlobal c = restTemplate.getForObject(
                urlBase + "/configuracionGlobal", ConfiguracionGlobal.class);
        System.out.println("ConfiguracionGlobal: props "
                + c.getPropiedades().getParametros().size());

        // Definicion entidad
        final ConfiguracionEntidad e = restTemplate.getForObject(urlBase + "/entidad/E1",
                ConfiguracionEntidad.class);
        System.out.println("Entidad: " + e.getIdentificador());

        // Avisos entidad
        final AvisosEntidad avisos = restTemplate.getForObject(
                urlBase + "/entidad/E1/avisos", AvisosEntidad.class);
        System.out.println("Avisos Entidad: " + avisos.getAvisos().size());

        // Definicion version
        final VersionTramite vt = restTemplate.getForObject(
                urlBase + "/tramite/ID1/1/es", VersionTramite.class);
        System.out.println("Version tramite: " + vt.getIdentificador());

        // Fuente datos
        testFD();

    }

    private static void testFD() throws JsonException {

        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors()
                .add(new BasicAuthorizationInterceptor("api-stg", "1234"));

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final ListaParametros lp = XTestJson.crearListaParametros();
        final String parametrosJSON = JsonUtil.toJson(lp);

        final MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("parametros", parametrosJSON);

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
                map, headers);

        final ResponseEntity<ValoresDominio> response = restTemplate
                .postForEntity(urlBase + "/dominioFuenteDatos/D1", request,
                        ValoresDominio.class);

        final ValoresDominio vd = response.getBody();

        System.out.println("Valores dominio: filas " + vd.getNumeroFilas());

    }

}
