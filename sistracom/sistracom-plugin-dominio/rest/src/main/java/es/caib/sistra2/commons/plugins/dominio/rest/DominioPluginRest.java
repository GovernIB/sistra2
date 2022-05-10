package es.caib.sistra2.commons.plugins.dominio.rest;

import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.lang3.StringUtils;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import es.caib.sistra2.commons.plugins.dominio.api.DominioPluginException;
import es.caib.sistra2.commons.plugins.dominio.api.IDominioPlugin;
import es.caib.sistra2.commons.plugins.dominio.api.ParametroDominio;
import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistra2.commons.plugins.dominio.rest.cxf.SistraFacade;
import es.caib.sistra2.commons.ws.utils.WsClientUtil;

/**
 * Interface email plugin.
 *
 * @author Indra
 *
 */
public class DominioPluginRest extends AbstractPluginProperties implements IDominioPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "ws.";

	/** Propiedades. **/
	private static final QName SERVICE_NAME = new QName("urn:es:caib:sistra:ws:v2:services", "SistraFacadeService");
	private static final QName PORT_NAME = new QName("urn:es:caib:sistra:ws:v2:services", "SistraFacade");

	/** Constructor. **/
	public DominioPluginRest(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	@Override
	public ValoresDominio invocarDominio(final String idCompuestoDominio, final String url,
			final List<ParametroDominio> parametros, final String user, final String pass, final Long timeout)
			throws DominioPluginException {

		// Del identificador compuesto, solo nos quedamos con el idDominio
		final String idDominio = idCompuestoDominio.substring(idCompuestoDominio.lastIndexOf(".") + 1);

		// Recuperamos dominio según sea rest o soap (S1)
		ValoresDominio retorno;
		if (url.indexOf("[SOAP]") != -1 || url.endsWith("wsdl")) {
			final String urlWs = getSistra1WsUrl(url);
			final String soapAction = getSistra1WsSoapAction(url);
			final boolean logCallsSistra1 = "true".equals(this.getProperty("logCallsSoap"));
			retorno = invocarDominioWSDL(idDominio, urlWs, parametros, user, pass, soapAction, timeout,
					logCallsSistra1);
		} else {
			retorno = invocarDominioREST(idDominio, url, parametros, user, pass, timeout);
		}
		return retorno;
	}

	private String getSistra1WsSoapAction(final String url) {
		String res = null;
		final int pos = url.indexOf("[SOAP-ACTION=");
		if (pos != -1) {
			res = url.substring(pos + "[SOAP-ACTION=".length(), url.indexOf("]", pos));
		}
		return StringUtils.trim(res);
	}

	private String getSistra1WsUrl(final String url) {
		// Elimina tags especiales configuracion S1
		String res = StringUtils.replaceAll(url, "\\[SOAP\\]", "");
		res = StringUtils.replaceAll(res, "\\[SOAP-ACTION=.*\\]", "");
		return StringUtils.trim(res);
	}

	/**
	 * Invoca un dominio wsdl (principalmente de sistra1)
	 *
	 * @param idDominio
	 * @param url
	 * @param parametros
	 * @param timeout
	 * @param logCalls
	 * @return
	 * @throws DominioPluginException
	 */
	private ValoresDominio invocarDominioWSDL(final String idDominio, final String url,
			final List<ParametroDominio> parametros, final String user, final String pass, final String soapAction,
			final Long timeout, final boolean logCalls) throws DominioPluginException {

		try {

			// final SistraFacadeService ss = new SistraFacadeService(wsdlURL,
			// SERVICE_NAME);
			// final SistraFacade port = ss.getSistraFacade();

			final Service service = javax.xml.ws.Service.create(SERVICE_NAME);
			service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, url);
			final SistraFacade port = service.getPort(PORT_NAME, SistraFacade.class);

			final BindingProvider bp = (BindingProvider) port;

			final String endpoint = getEndpoint(url);
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
			WsClientUtil.configurePort(bp, endpoint, user, pass, "BASIC", soapAction, timeout * 1000L, logCalls);

			final es.caib.sistra2.commons.plugins.dominio.rest.cxf.ParametrosDominio parametrosWSDL = new es.caib.sistra2.commons.plugins.dominio.rest.cxf.ParametrosDominio();
			if (parametros != null && !parametros.isEmpty()) {
				for (final ParametroDominio parametro : parametros) {
					if (parametro.getValor() != null && !parametro.getValor().isEmpty()) {
						parametrosWSDL.getParametro().add(parametro.getValor());
					}
				}
			}

			final es.caib.sistra2.commons.plugins.dominio.rest.cxf.ValoresDominio valoresDominioSistra1 = port
					.obtenerDominio(idDominio, parametrosWSDL);
			final ValoresDominio valoresDominio = Utilidades.getValoresDominioSistra1(valoresDominioSistra1);

			return valoresDominio;

		} catch (final Exception e) {
			throw new DominioPluginException("Error conectándose a la url: " + e.getMessage(), e);
		}

	}

	/**
	 * Extrae la url
	 *
	 * @param url
	 * @return
	 */
	private String getEndpoint(final String url) {
		String endpoint;
		if (url.endsWith("?wsdl")) {
			endpoint = url.substring(0, url.indexOf("?wsdl"));
		} else {
			endpoint = url;
		}
		return endpoint;
	}

	private ValoresDominio invocarDominioREST(final String idDominio, final String url,
			final List<ParametroDominio> parametros, final String user, final String pass, final Long timeout)
			throws DominioPluginException {

		// Para habilitar logs de rest hay que habilitar a nivel de apache client
		// logging.level.org.apache.http=DEBUG
		// logging.level.httpclient.wire=DEBUG

		final RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory((int) (timeout * 1000L)));

		if (user != null && pass != null) {
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(user, pass));
		}

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		// Obtener valores dominio.
		final RFiltroDominio filtro = new RFiltroDominio();
		filtro.setIdDominio(idDominio);

		if (parametros != null && !parametros.isEmpty()) {
			for (final ParametroDominio parametro : parametros) {
				if (parametro.getValor() != null && !parametro.getValor().isEmpty()) {
					filtro.addParam(new RParametroDominio(parametro.getCodigo(), parametro.getValor()));
				}
			}
		}
		final HttpEntity<RFiltroDominio> request = new HttpEntity<>(filtro, headers);

		ResponseEntity<RValoresDominio> response = null;

		try {
			response = restTemplate.postForEntity(url, request, RValoresDominio.class);
		} catch (final Exception e) {
			throw new DominioPluginException("Error realizando conexión con " + url + ": " + e.getMessage(), e);
		}

		if (response == null) {
			throw new DominioPluginException("Respuesta vacía.");
		}

		return Utilidades.getValoresDominio(response.getBody());

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
