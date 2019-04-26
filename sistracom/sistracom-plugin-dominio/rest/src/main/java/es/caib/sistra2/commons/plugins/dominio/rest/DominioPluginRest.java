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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import es.caib.sistra2.commons.plugins.dominio.api.DominioPluginException;
import es.caib.sistra2.commons.plugins.dominio.api.IDominioPlugin;
import es.caib.sistra2.commons.plugins.dominio.api.ParametroDominio;
import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
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
	public static final String IMPLEMENTATION_BASE_PROPERTY = "rest.";

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
			retorno = invocarDominioSistra1();
		} else {
			retorno = invocarDominioSistra2();
		}
		return retorno;
	}

	private static final QName SERVICE_NAME = new QName("urn:es:caib:sistra:ws:v2:services", "SistraFacadeService");

	/**
	 * Invoca un dominio de sistra1
	 *
	 * @return
	 * @throws DominioPluginException
	 */
	private ValoresDominio invocarDominioSistra1() throws DominioPluginException {
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
			wsdlURL = new URL("https://proves.caib.es/udit/integracion/sistra/services/SistraFacade?wsdl");
		} catch (final MalformedURLException e) {
			throw new DominioPluginException("URL mal formada", e.getCause());
		}

		final SistraFacadeService ss = new SistraFacadeService(wsdlURL, SERVICE_NAME);
		final SistraFacade port = ss.getSistraFacade();

		try {
			configurarService((BindingProvider) port, "endpoint.entrada", "$sistra_udit", "sistra_udit", false);
		} catch (final Exception e1) {
			throw new DominioPluginException("Mal configuracion del servicio", e1.getCause());

		}
		// user --> $sistra_udit
		// pass --> sistra_udit
		{

			final java.lang.String _obtenerDominio_id = "CICONIF";
			final es.caib.sistra2.commons.plugins.dominio.rest.cxf.ParametrosDominio _obtenerDominio_parametros = new es.caib.sistra2.commons.plugins.dominio.rest.cxf.ParametrosDominio();
			_obtenerDominio_parametros.getParametro().add("NIF=74239824X");
			try {
				final es.caib.sistra2.commons.plugins.dominio.rest.cxf.ValoresDominio valoresDominioSistra1 = port
						.obtenerDominio(_obtenerDominio_id, _obtenerDominio_parametros);
				valoresDominio = Utilidades.getValoresDominioSistra1(valoresDominioSistra1);

			} catch (final SistraFacadeException_Exception e) {
				throw new DominioPluginException("Error conectándose a la url", e.getCause());
			}
		}

		return valoresDominio;
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
	 * Invoca un dominio de sistra2
	 *
	 * @return
	 * @throws DominioPluginException
	 */
	private ValoresDominio invocarDominioSistra2() throws DominioPluginException {
		final RestTemplate restTemplate = new RestTemplate();

		if (getProperty("usr") != null) {
			restTemplate.getInterceptors()
					.add(new BasicAuthorizationInterceptor(getPropiedad("usr"), getPropiedad("pwd")));
		}
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Obtener valores dominio.
		final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		// map.add("idioma", idioma);
		final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		final ResponseEntity<RValoresDominio> response = restTemplate
				.postForEntity("http://localhost:8090/dominios/ok2", request, RValoresDominio.class);
		restTemplate.getForEntity("http://localhost:8090/dominios/ok", String.class).getBody();

		if (response == null || response.getBody() == null) {
			throw new DominioPluginException("Esta vacío de datos.");
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
	private String getPropiedad(final String propiedad) throws DominioPluginException {
		final String res = getProperty(DOMINIO_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
		if (res == null) {
			throw new DominioPluginException("No se ha especificado parametro " + propiedad + " en propiedades");
		}
		return res;
	}
}
