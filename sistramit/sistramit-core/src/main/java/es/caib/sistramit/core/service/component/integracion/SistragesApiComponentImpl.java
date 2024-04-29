package es.caib.sistramit.core.service.component.integracion;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import es.caib.sistramit.core.api.exception.ErrorConfiguracionException;
import es.caib.sistramit.core.api.exception.ErrorNoControladoException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RDominio;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.component.system.ConfiguracionComponent;
import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;

import javax.net.ssl.SSLContext;

/**
 * Implementación acceso SISTRAGES.
 *
 * @author Indra
 *
 */
@Component("sistragesApiComponent")
public final class SistragesApiComponentImpl implements SistragesApiComponent {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Configuracion. */
	@Autowired
	private ConfiguracionComponent configuracionComponent;

	@Override
	public RConfiguracionGlobal obtenerConfiguracionGlobal() {
		final RestTemplate restTemplate = getRestTemplate(true);
		return restTemplate.getForObject(getUrl() + "/configuracionGlobal", RConfiguracionGlobal.class);
	}

	@Override
	public RConfiguracionEntidad obtenerConfiguracionEntidad(final String idEntidad) {
		final RestTemplate restTemplate = getRestTemplate(false);
		return restTemplate.getForObject(getUrl() + "/entidad/" + idEntidad, RConfiguracionEntidad.class);
	}

	@Override
	public RVersionTramite recuperarDefinicionTramite(final String idTramite, final int version, final String idioma) {
		final RestTemplate restTemplate = getRestTemplate(false);
		return restTemplate.getForObject(getUrl() + "/tramite/" + idTramite + "/" + version + "/" + idioma,
				RVersionTramite.class);
	}

	@Override
	public RDominio recuperarDefinicionDominio(final String idDominio) {
		final RestTemplate restTemplate = getRestTemplate(false);
		return restTemplate.getForObject(getUrl() + "/dominio/" + idDominio, RDominio.class);
	}

	@Override
	public RAvisosEntidad obtenerAvisosEntidad(final String idEntidad) {
		final RestTemplate restTemplate = getRestTemplate(false);
		return restTemplate.getForObject(getUrl() + "/entidad/" + idEntidad + "/avisos", RAvisosEntidad.class);
	}

	@Override
	public RValoresDominio resuelveDominioFuenteDatos(final RDominio dominio,
			final ParametrosDominio parametrosDominio) {

		// Convertimos lista parametros
		final List<RValorParametro> parametros = new ArrayList<>();
		if (parametrosDominio != null && parametrosDominio.getParametros() != null
				&& !parametrosDominio.getParametros().isEmpty()) {
			for (final es.caib.sistramit.core.service.model.integracion.ParametroDominio parametroDominio : parametrosDominio
					.getParametros()) {
				final RValorParametro rValorParametro = new RValorParametro();
				rValorParametro.setCodigo(parametroDominio.getCodigo());
				rValorParametro.setValor(parametroDominio.getValor());
				parametros.add(rValorParametro);
			}
		}
		final RListaParametros listaParametros = new RListaParametros();
		listaParametros.setParametros(parametros);

		// Realizamos llamada
		final RestTemplate restTemplate = new RestTemplate();
		final String url = getUrl();
		final String usuario = getUser();
		final String pass = getPassword();
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(usuario, pass));
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final HttpEntity<RListaParametros> request = new HttpEntity<>(listaParametros, headers);
		final ResponseEntity<RValoresDominio> responseRest = restTemplate.postForEntity(
				url + "/dominioFuenteDatos/" + dominio.getIdentificador(), request, RValoresDominio.class);
		return responseRest.getBody();
	}

	@Override
	public RValoresDominio resuelveDominioListaFija(final RDominio dominio) {
		final RestTemplate restTemplate = getRestTemplate(false);
		return restTemplate.getForObject(getUrl() + "/dominioListaFija/" + dominio.getIdentificador(),
				RValoresDominio.class);
	}

	/**** Private functions. **/
	/**
	 * Obtiene el password.
	 *
	 * @return
	 */
	private String getPassword() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_PWD);
	}

	/**
	 * Obtiene el usuario
	 *
	 * @return
	 */
	private String getUser() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_USR);
	}

	/**
	 * Obtiene la url.
	 *
	 * @return
	 */
	private String getUrl() {
		return configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_URL);
	}

	/**
	 * Obtiene timeout (millis).
	 *
	 * @return timeout (millis)
	 */
	private int getTimeout() {
		String timeoutStr = configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_TIMEOUT);
		if (StringUtils.isBlank(timeoutStr)) {
			timeoutStr = "60";
		}
		return Integer.parseInt(timeoutStr) * 1000;
	}

	/**
	 * Obtiene si esta habilitado timeout.
	 *
	 * @return true si esta habilitado timeout.
	 */
	private boolean isTimeoutEnabled() {
		String timeout = configuracionComponent.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAGES_TIMEOUT_HABILITAR);
		return (BooleanUtils.toBoolean(timeout));
	}

	/**
	 * Obtiene rest template aplicando timeout.
	 * @return rest template.
	 */
	private RestTemplate getRestTemplate(boolean timeoutFijo) {
		RestTemplate restTemplate = null;
		if (!timeoutFijo && isTimeoutEnabled()) {
			// Acceso con timeout especifico
			HttpComponentsClientHttpRequestFactory factory = null;
			try {
				// Deshabilitamos verificacion SSL
				TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
				SSLContext sslContext = org.apache.http.conn.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
				SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
				CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
				factory = new HttpComponentsClientHttpRequestFactory();
				factory.setHttpClient(httpClient);
			} catch (Exception e) {
				throw new ErrorNoControladoException(e);
			}
			int timeinMillis = 60000; // 60 secs
			if (!timeoutFijo) {
				timeinMillis = getTimeout();
			}
			factory.setReadTimeout(timeinMillis);
			factory.setConnectTimeout(timeinMillis);
			restTemplate = new RestTemplate(factory);
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		} else {
			// Acceso sin timeout especifico
			restTemplate = new RestTemplate();
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(getUser(), getPassword()));
		}
		return restTemplate;
	}

}
