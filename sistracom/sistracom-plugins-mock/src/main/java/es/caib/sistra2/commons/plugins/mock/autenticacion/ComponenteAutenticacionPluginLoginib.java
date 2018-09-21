package es.caib.sistra2.commons.plugins.mock.autenticacion;

import java.util.List;
import java.util.Properties;

import org.fundaciobit.plugins.utils.AbstractPluginProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import es.caib.loginib.rest.api.v1.RDatosAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.DatosUsuario;
import es.caib.sistra2.commons.plugins.autenticacion.IComponenteAutenticacionPlugin;
import es.caib.sistra2.commons.plugins.autenticacion.TipoAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.TipoMetodoAutenticacion;

/**
 * Plugin componente autenticación con loginib.
 *
 * @author Indra
 *
 */
public class ComponenteAutenticacionPluginLoginib extends AbstractPluginProperties
		implements IComponenteAutenticacionPlugin {

	public ComponenteAutenticacionPluginLoginib() {
		prefijo = null;
		properties = null;
		url = "http://localhost:8080/loginib/api/rest/v1";
	}

	private final String prefijo;
	private final Properties properties;
	private String url;

	public ComponenteAutenticacionPluginLoginib(final String prefijoPropiedades, final Properties properties) {
		this.prefijo = prefijoPropiedades;
		this.properties = properties;
		this.url = "http://localhost:8080/loginib/api/rest/v1";
		if (this.properties != null) {
			if (this.properties.get("plugins.login.url") != null) {
				url = this.properties.get("plugins.login.url").toString();
			}
		}

	}

	@Override
	public String iniciarSesionAutenticacion(final String codigoEntidad, final String idioma,
			final List<TipoAutenticacion> metodos, final String qaa, final String callback) {

		// Configuracion global
		final StringBuilder parametros = new StringBuilder();
		String metodo = "";
		if (metodos.contains(TipoAutenticacion.ANONIMO)) {
			metodo += "ANONIMO;";
		}
		if (metodos.contains(TipoAutenticacion.AUTENTICADO)) {
			metodo += "CLAVE_CERTIFICADO;CLAVE_PIN;CLAVE_PERMANENTE";
		}
		if (metodo.endsWith(";")) {
			metodo = metodo.substring(0, metodo.length() - 1);
		}

		parametros.append("{  \"entidad\": \"" + codigoEntidad + "\"," + "  \"forzarAutenticacion\": false,"
				+ "  \"idioma\": \"" + idioma + "\"," + "  \"metodosAutenticacion\": \"" + metodo + "\","
				+ "  \"qaa\": " + qaa + "," + "  \"urlCallback\": \"" + callback + "\"" + "}");

		final RestTemplate restTemplate = new RestTemplate();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Obtener tramite.
		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("parametros", parametros.toString());
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		final ResponseEntity<String> responseTramite = restTemplate.postForEntity(this.url + "/login", request,
				String.class);
		return responseTramite.getBody();
	}

	@Override
	public DatosUsuario validarTicketAutenticacion(final String pTicket) {

		final RestTemplate restTemplate = new RestTemplate();

		// Configuracion global
		final RDatosAutenticacion datosUtenticacion = restTemplate.getForObject(this.url + "/ticket/" + pTicket,
				RDatosAutenticacion.class);

		final DatosUsuario datos = new DatosUsuario();
		datos.setMetodoAutenticacion(TipoMetodoAutenticacion.fromString(datosUtenticacion.getMetodoAutenticacion()));
		datos.setNif(datosUtenticacion.getNif());
		datos.setNombre(datosUtenticacion.getNombre());
		datos.setApellido1(datosUtenticacion.getApellido1());
		datos.setApellido2(datosUtenticacion.getApellido2());
		datos.setMetodoAutenticacion(TipoMetodoAutenticacion.fromString(datosUtenticacion.getMetodoAutenticacion()));
		if ("ANONIMO".equals(datosUtenticacion.getMetodoAutenticacion())) {
			datos.setAutenticacion(TipoAutenticacion.ANONIMO);
		} else {
			datos.setAutenticacion(TipoAutenticacion.AUTENTICADO);
		}
		return datos;
	}

	@Override
	public String iniciarSesionLogout(final String codigoEntidad, final String pIdioma, final String pCallback) {
		final RestTemplate restTemplate = new RestTemplate();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Configuracion global
		final StringBuilder parametros = new StringBuilder();

		parametros.append("{  \"entidad\": \"" + codigoEntidad + "\", \"idioma\": \"" + pIdioma
				+ "\", \"urlCallback\": \"" + pCallback + "\"" + "}");

		// Obtener tramite.
		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("parametros", parametros.toString());
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		final ResponseEntity<String> responseTramite = restTemplate.postForEntity(this.url + "/logout", request,
				String.class);
		return responseTramite.getBody();
	}

}