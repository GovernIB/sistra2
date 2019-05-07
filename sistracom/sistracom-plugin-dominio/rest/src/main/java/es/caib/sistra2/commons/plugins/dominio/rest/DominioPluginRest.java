package es.caib.sistra2.commons.plugins.dominio.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import es.caib.sistra2.commons.plugins.dominio.api.DominioPluginException;
import es.caib.sistra2.commons.plugins.dominio.api.IDominioPlugin;
import es.caib.sistra2.commons.plugins.dominio.api.ParametroDominio;
import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistra2.commons.plugins.dominio.rest.api.v1.RFiltroDominio;
import es.caib.sistra2.commons.plugins.dominio.rest.api.v1.RParametroDominio;
import es.caib.sistra2.commons.plugins.dominio.rest.api.v1.RValoresDominio;
import es.caib.sistra2.commons.plugins.dominio.rest.cxf.SistraFacade;
import es.caib.sistra2.commons.plugins.dominio.rest.cxf.SistraFacadeException_Exception;
import es.caib.sistra2.commons.plugins.dominio.rest.cxf.SistraFacadeService;
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
	private static final String BASE_PROPERTY_USER = ".USER";
	private static final String BASE_PROPERTY_PASS = ".PASS";

	/** Constructor. **/
	public DominioPluginRest(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}

	/**
	 * Invoca dominio remoto.
	 *
	 * @param idDominio
	 *            id dominio
	 * @param url
	 *            url
	 * @param parametros
	 *            parametros
	 * @return valores dominio
	 * @throws DominioPluginException
	 */
	@Override
	public ValoresDominio invocarDominio(final String idDominio, final String url,
			final List<ParametroDominio> parametros) throws DominioPluginException {
		ValoresDominio retorno;
		if (url.endsWith("wsdl")) {
			retorno = invocarDominioWSDL(idDominio, url, parametros);
		} else {
			retorno = invocarDominioREST(idDominio, url, parametros);
		}
		return retorno;
	}

	/**
	 * Invoca un dominio wsdl (principalmente de sistra1)
	 *
	 * @param idDominio
	 * @param url
	 * @param parametros
	 * @return
	 * @throws DominioPluginException
	 */
	private ValoresDominio invocarDominioWSDL(final String idDominio, final String url,
			final List<ParametroDominio> parametros) throws DominioPluginException {
		ValoresDominio valoresDominio = null;
		URL wsdlURL;
		/***
		 * En caso de error: PKIX path building faild SunCertPathBuilderException... Hay
		 * que importar, bajarse el certificado del https y ejecutar:
		 *
		 * La ruta del keytool y el cacert variará según servidor (cambiar el $JAVA_HOME
		 * por la ruta correcta) :
		 *
		 * $JAVA_HOME\jre\bin>keytool -import -trustcacerts -keystore
		 * $JAVA_HOME\jre\lib\security\cacerts -storepass changeit -alias provescaib
		 * -file provescaibes.crt -noprompt
		 *
		 */

		try {
			wsdlURL = new URL(url);
		} catch (final MalformedURLException e) {
			throw new DominioPluginException("URL mal formada", e.getCause());
		}

		final SistraFacadeService ss = new SistraFacadeService(wsdlURL, SERVICE_NAME);
		final SistraFacade port = ss.getSistraFacade();

		final String user = getPropiedad(idDominio, BASE_PROPERTY_USER);
		final String pass = getPropiedad(idDominio, BASE_PROPERTY_PASS);

		try {
			configurarService((BindingProvider) port, getEndpoint(url), user, pass, false);
		} catch (final Exception e1) {
			throw new DominioPluginException("Mal configuracion del servicio", e1.getCause());

		}

		final es.caib.sistra2.commons.plugins.dominio.rest.cxf.ParametrosDominio parametrosWSDL = new es.caib.sistra2.commons.plugins.dominio.rest.cxf.ParametrosDominio();
		if (parametros != null && !parametros.isEmpty()) {
			for (final ParametroDominio parametro : parametros) {
				if (parametro.getValor() != null && !parametro.getValor().isEmpty()) {
					parametrosWSDL.getParametro().add(parametro.getValor());
				}
			}
		}

		try {
			final es.caib.sistra2.commons.plugins.dominio.rest.cxf.ValoresDominio valoresDominioSistra1 = port
					.obtenerDominio(idDominio, parametrosWSDL);
			valoresDominio = Utilidades.getValoresDominioSistra1(valoresDominioSistra1);

		} catch (final SistraFacadeException_Exception e) {
			throw new DominioPluginException("Error conectándose a la url", e.getCause());
		}

		return valoresDominio;
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

	/**
	 * Configura service.
	 *
	 * @param bp
	 *            Binding Provider
	 * @param endpoint
	 *            Endpoint ws
	 * @param user
	 *            usuario
	 * @param pass
	 *            password
	 * @throws Exception
	 */
	private void configurarService(final BindingProvider bp, final String endpoint, final String user,
			final String pass, final boolean logCalls) throws Exception {
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
		WsClientUtil.configurePort(bp, endpoint, user, pass, "BASIC", logCalls);
	}

	/**
	 * Invoca un dominio de tipo REST (principalmente de tipo sistra2)
	 *
	 * @param idDominio
	 * @param url
	 * @param parametros
	 * @return
	 * @throws DominioPluginException
	 */
	private ValoresDominio invocarDominioREST(final String idDominio, final String url,
			final List<ParametroDominio> parametros) throws DominioPluginException {
		final RestTemplate restTemplate = new RestTemplate();

		final String user = getPropiedad(idDominio, BASE_PROPERTY_USER);
		final String pass = getPropiedad(idDominio, BASE_PROPERTY_PASS);

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

		final ResponseEntity<RValoresDominio> response = restTemplate.postForEntity(url, request,
				RValoresDominio.class);

		if (response == null) {
			throw new DominioPluginException("Respuesta vacía.");
		}

		return Utilidades.getValoresDominio(response.getBody());

	}

	/**
	 * Obtiene propiedad.
	 *
	 * @param propiedad
	 *            propiedad
	 * @return valor
	 * @throws AutenticacionPluginException
	 */
	private String getPropiedad(final String idDominio, final String propiedad) throws DominioPluginException {
		String valor = null;
		final String login = getProperty(DOMINIO_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + "DOMINIO." + idDominio + ".LOGIN");
		if (login != null) {
			valor = getProperty(DOMINIO_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + "LOGIN." + login + propiedad);
		}
		return valor;
	}
}
