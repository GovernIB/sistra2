package es.caib.sistra2.commons.plugins.registro.envio;

import java.util.Properties;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import es.caib.sistra2.commons.plugins.registro.api.AsientoRegistral;
import es.caib.sistra2.commons.plugins.registro.api.DestinoEnvio;
import es.caib.sistra2.commons.plugins.registro.api.EnvioRemotoPluginException;
import es.caib.sistra2.commons.plugins.registro.api.IEnvioRemotoPlugin;
import es.caib.sistra2.commons.plugins.registro.api.ResultadoRegistro;
import es.caib.sistra2.commons.plugins.registro.api.VerificacionRegistro;

/**
 * Implementacion REST del plugin de envio remoto.
 *
 * @author Indra
 *
 */
public class EnvioRemotoPlugin extends AbstractPluginProperties implements IEnvioRemotoPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "envio.";
	public static final String URL_INICIAR_SESION = "/iniciarSesion";
	public static final String URL_REALIZAR_ENVIO = "/realizarEnvio";
	public static final String URL_VERIFICAR_ENVIO = "/verificarEnvio";

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public EnvioRemotoPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public String iniciarSesionEnvio(final DestinoEnvio destinoEnvio) throws EnvioRemotoPluginException {
		final RestTemplate restTemplate = new RestTemplate(
				getClientHttpRequestFactory((int) (destinoEnvio.getTimeoutSecs() * 1000L)));
		final String user = destinoEnvio.getUsuario();
		final String pass = destinoEnvio.getPassword();
		final String url = destinoEnvio.getUrl();

		/*Creamos objeto inicio sesion que se pasa al servicio*/
		RInicioSesion inicioSesion = new RInicioSesion();
		inicioSesion.setIdEntidad(destinoEnvio.getIdEntidad());
		inicioSesion.setIdEnvioRemoto(destinoEnvio.getIdEnvioRemoto());

		if (user != null && pass != null) {
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(user, pass));
		}
		final HttpHeaders headers = new HttpHeaders();

		// headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		final HttpEntity<RInicioSesion> request = new HttpEntity<>(inicioSesion, headers);
		ResponseEntity<String> response = null;

		try {
			response = restTemplate.postForEntity(url + URL_INICIAR_SESION, request, String.class);
		} catch (final Exception e) {
			throw new EnvioRemotoPluginException("Error realizando conexión con " + url + ": " + e.getMessage(), e);
		}

		if (response == null) {
			throw new EnvioRemotoPluginException("Respuesta vacía.");
		}

		return response.getBody();

	}

	@Override
	public ResultadoRegistro realizarEnvio(final DestinoEnvio destinoEnvio, final String idSesionEnvio,
			final AsientoRegistral asientoRegistral) throws EnvioRemotoPluginException {
		final RestTemplate restTemplate = new RestTemplate(
				getClientHttpRequestFactory((int) (destinoEnvio.getTimeoutSecs() * 1000L)));
		final String user = destinoEnvio.getUsuario();
		final String pass = destinoEnvio.getPassword();
		final String url = destinoEnvio.getUrl();

		RAsientoRegistral ras = new RAsientoRegistral();
		ras.setDatosAsunto(asientoRegistral.getDatosAsunto());
		ras.setDatosDestino(asientoRegistral.getDatosDestino());
		ras.setDatosOrigen(asientoRegistral.getDatosOrigen());
		ras.setDocumentosRegistro(asientoRegistral.getDocumentosRegistro());
		ras.setInteresados(asientoRegistral.getInteresados());

		if (user != null && pass != null) {
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(user, pass));
		}

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final REnvioRemoto envio = new REnvioRemoto();
		envio.setIdEnvio(idSesionEnvio);
		envio.setAsiento(ras);

		final HttpEntity<REnvioRemoto> request = new HttpEntity<>(envio, headers);

		ResponseEntity<RResultadoRegistro> response = null;

		try {
			response = restTemplate.postForEntity(url + URL_REALIZAR_ENVIO, request, RResultadoRegistro.class);
		} catch (final Exception e) {
			throw new EnvioRemotoPluginException("Error realizando conexión con " + url + ": " + e.getMessage(), e);
		}

		if (response == null) {
			throw new EnvioRemotoPluginException("Respuesta vacía.");
		}

		return Utilidades.getResultadoRegistro(response.getBody());
	}

	@Override
	public VerificacionRegistro verificarEnvio(final DestinoEnvio destinoEnvio, final String idSesionEnvio)
			throws EnvioRemotoPluginException {
		final RestTemplate restTemplate = new RestTemplate(
				getClientHttpRequestFactory((int) (destinoEnvio.getTimeoutSecs() * 1000L)));
		final String user = destinoEnvio.getUsuario();
		final String pass = destinoEnvio.getPassword();
		final String url = destinoEnvio.getUrl();
		if (user != null && pass != null) {
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(user, pass));
		}
		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		final HttpEntity<String> request = new HttpEntity<>(idSesionEnvio, headers);

		ResponseEntity<RVerificacionRegistro> response = null;

		try {
			response = restTemplate.postForEntity(url + URL_VERIFICAR_ENVIO, request, RVerificacionRegistro.class);
		} catch (final Exception e) {
			throw new EnvioRemotoPluginException("Error realizando conexión con " + url + ": " + e.getMessage(), e);
		}

		if (response == null) {
			throw new EnvioRemotoPluginException("Respuesta vacía.");
		}

		return Utilidades.getValidacion(response.getBody());
	}

	private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory(final int timeout) {
		final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		// Connect timeout
		clientHttpRequestFactory.setConnectTimeout(timeout);
		// Read timeout
		clientHttpRequestFactory.setReadTimeout(timeout);
		return clientHttpRequestFactory;
	}

}
