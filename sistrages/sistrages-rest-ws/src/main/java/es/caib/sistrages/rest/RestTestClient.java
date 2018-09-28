package es.caib.sistrages.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistrages.rest.api.util.JsonException;
import es.caib.sistrages.rest.api.util.XTestJson;

public class RestTestClient {

    private final static String urlBase = "http://localhost:8080/sistrages/api/rest/interna";

    public static void main(final String[] args) throws JsonException {

        test();

    }

    public static void test() {

        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors()
                .add(new BasicAuthorizationInterceptor("api-stg", "1234"));

        // Configuracion global

        final RConfiguracionGlobal c = restTemplate.getForObject(
                urlBase + "/configuracionGlobal", RConfiguracionGlobal.class);
        System.out.println("ConfiguracionGlobal: props "
                + c.getPropiedades().getParametros().size());

        // Definicion entidad
        final RConfiguracionEntidad e = restTemplate.getForObject(
                urlBase + "/entidad/A1234567", RConfiguracionEntidad.class);
        System.out.println("Entidad: " + e.getIdentificador());

        // Avisos entidad
        final RAvisosEntidad avisos = restTemplate.getForObject(
                urlBase + "/entidad/E1/avisos", RAvisosEntidad.class);
        System.out.println("Avisos Entidad: " + avisos.getAvisos().size());

        // Definicion version
        final RVersionTramite vt = restTemplate.getForObject(
                urlBase + "/tramite/ID1/1/es", RVersionTramite.class);
        System.out.println("Version tramite: " + vt.getIdentificador());

        // Fuente datos
        testFD();

    }

    private static void testFD() {

        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors()
                .add(new BasicAuthorizationInterceptor("api-stg", "1234"));

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final RListaParametros lp = XTestJson.crearListaParametros();

        final HttpEntity<RListaParametros> request = new HttpEntity<>(lp,
                headers);

        final ResponseEntity<RValoresDominio> response = restTemplate
                .postForEntity(urlBase + "/dominioFuenteDatos/D1", request,
                        RValoresDominio.class);

        final RValoresDominio vd = response.getBody();

        System.out.println("Valores dominio: filas " + vd.getNumeroFilas());

    }

}
