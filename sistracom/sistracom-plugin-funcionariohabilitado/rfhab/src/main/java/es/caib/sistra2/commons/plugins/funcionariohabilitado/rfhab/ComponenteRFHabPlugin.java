package es.caib.sistra2.commons.plugins.funcionariohabilitado.rfhab;

import java.util.Date;
import java.util.Properties;

import es.caib.sistra2.commons.plugins.funcionariohabilitado.api.FuncionarioHabilitadoPluginException;
import es.caib.sistra2.commons.plugins.funcionariohabilitado.api.IFuncionarioHabilitadoPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Plugin componente RFHAB.
 *
 * @author Indra
 *
 */
public class ComponenteRFHabPlugin extends AbstractPluginProperties implements IFuncionarioHabilitadoPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "rfhab.";

	/** Logger. */
	private final Logger log = LoggerFactory.getLogger(ComponenteRFHabPlugin.class);

	public ComponenteRFHabPlugin() {
	}

	/**
	 * Constructor.
	 *
	 * @param prefijoPropiedades
	 *                               prefijo props
	 * @param properties
	 *                               propiedades
	 */
	public ComponenteRFHabPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}


	/**
	 * Obtiene propiedad (obligatoria).
	 *
	 * @param propiedad
	 *                      propiedad
	 * @return valor
	 * @throws FuncionarioHabilitadoPluginException
	 */
	private String getPropiedad(final String propiedad) throws FuncionarioHabilitadoPluginException {
		final String res = getProperty(FUNCIONARIOHABILITADO_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
		if (res == null) {
			throw new FuncionarioHabilitadoPluginException("No se ha especificado parametro " + propiedad + " en propiedades");
		}
		return res;
	}

	/**
	 * Obtiene propiedad (opcional)
	 .
	 *
	 * @param propiedad
	 *                      propiedad
	 * @return valor
	 */
	private String getPropiedadOpcional(final String propiedad) {
		final String res = getProperty(FUNCIONARIOHABILITADO_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
		return res;
	}


	@Override
	public void avisarFinalizacionTramite(String nifFH, String idActuacionFH, String numeroRegistro, Date fechaRegistro, String idioma, boolean debug) throws FuncionarioHabilitadoPluginException {

		String url = getPropiedad("url");
		String usuario = getPropiedad("username");
		String password = getPropiedad("password");

		// 'https://governdigital.fundaciobit.org/rfhabapi/interna/secure/activitat/registre?language=ca&funcionari=15647325N&tipus=2&data=2025-08-31T06%3A15%3A00%2B00%3A00&registre=23&idactuaciotramitfh=e0f7d461-350a-449f-984e-2403e70d8031' \
		url += "?language=" + idioma +
				"&funcionari=" + nifFH +
				"&tipus=2" +
				"&data=" + fechaRegistro.toInstant() +
				"&registre=" + numeroRegistro +
				"&idactuaciotramitfh=" + idActuacionFH;

		if (debug) {
			log.debug("RFHAB --- Invocando a " + url);
		}

		final RestTemplate restTemplate = new RestTemplate();
		HttpStatus status = null;
		String body = null;
		// Autenticación
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(usuario, password));
		// Headers
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// 📨 Entidad HTTP (cuerpo + headers)
		String requestBody = "";
		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
		// 🚀 Enviar POST y obtener respuesta como String
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
			status = response.getStatusCode();
			body = response.getBody();
		} catch (HttpClientErrorException e) {
			status = e.getStatusCode();
			body = e.getResponseBodyAsString();
		}

		if (debug) {
			log.debug("RFHAB --- Status: " + status);
			log.debug("RFHAB --- Body: " + body);
		}

		if (status != HttpStatus.OK) {
			throw new FuncionarioHabilitadoPluginException("Status: " + status + " - Body: " + body);
		}

	}
}
