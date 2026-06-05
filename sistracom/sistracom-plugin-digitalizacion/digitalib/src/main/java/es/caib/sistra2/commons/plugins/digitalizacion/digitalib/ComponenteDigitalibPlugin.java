package es.caib.sistra2.commons.plugins.digitalizacion.digitalib;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

import es.caib.sistra2.commons.plugins.digitalizacion.digitalib.modelo.*;
import es.caib.sistra2.commons.utils.JSONUtil;
import es.caib.sistra2.commons.utils.JSONUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistra2.commons.plugins.digitalizacion.api.*;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Plugin componente digitalib.
 *
 * @author Indra
 *
 */
public class ComponenteDigitalibPlugin extends AbstractPluginProperties implements IDigitalizacionPlugin {

	/** Prefix. */
	public static final String IMPLEMENTATION_BASE_PROPERTY = "digitalib.";

	/** Logger. */
	private final Logger log = LoggerFactory.getLogger(ComponenteDigitalibPlugin.class);

	/** Constants. */
	public static final MassiveScanWebSimpleConstants CONSTANTS = new MassiveScanWebSimpleConstants();

	public ComponenteDigitalibPlugin() {
	}

	/**
	 * Constructor.
	 *
	 * @param prefijoPropiedades
	 *                               prefijo props
	 * @param properties
	 *                               propiedades
	 */
	public ComponenteDigitalibPlugin(final String prefijoPropiedades, final Properties properties) {
		super(prefijoPropiedades, properties);
	}



	/**
	 * Obtiene propiedad (obligatoria).
	 *
	 * @param propiedad
	 *                      propiedad
	 * @return valor
	 * @throws DigitalizacionPluginException
	 */
	private String getPropiedad(final String propiedad) throws DigitalizacionPluginException {
		final String res = getProperty(DIGITALIZACION_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
		if (res == null) {
			throw new DigitalizacionPluginException("No se ha especificado parametro " + propiedad + " en propiedades");
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
		final String res = getProperty(DIGITALIZACION_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
		return res;
	}

	@Override
	public String generarSesionDigitalizacion(InfoSesionDigitalizacion infoSesionDigitalizacion) throws DigitalizacionPluginException {

		// Generamos request
		final String profileCode = getPropiedad("profileCode");
		final int view = isIframe() ? CONSTANTS.getMassiveScanWebSimpleGetTransactionIdRequestVIEWIFRAME() : CONSTANTS.getMassiveScanWebSimpleGetTransactionIdRequestVIEWFULLSCREEN();

		// Parametros de firma
		MassiveScanWebSimpleSignatureParameters signatureParameters = new MassiveScanWebSimpleSignatureParameters();
		signatureParameters.setFunctionaryFullName(infoSesionDigitalizacion.getFuncionarioHabilitado().getNombreApellidos());
		signatureParameters.setFunctionaryAdministrationID(infoSesionDigitalizacion.getFuncionarioHabilitado().getNif());
		signatureParameters.setFunctionayDIR3Unit(infoSesionDigitalizacion.getCodigoDIR3());

		// Parametros de archivo
		MassiveScanWebSimpleArxiuRequiredParameters arxiuRequiredParameters = new MassiveScanWebSimpleArxiuRequiredParameters();
		arxiuRequiredParameters.setDocumentOrigen(CONSTANTS.getMassiveScanWebSimpleArxiuRequiredParametersDOCUMENTORIGENCIUTADA());
		arxiuRequiredParameters.setDocumentElaborationState(CONSTANTS.getMassiveScanWebSimpleArxiuRequiredParametersDOCUMENTELABORATIONSTATECOPIADP());
		arxiuRequiredParameters.setCitizenFullName(infoSesionDigitalizacion.getCiudadano().getNombreApellidos());
		arxiuRequiredParameters.setCitizenAdministrationID(infoSesionDigitalizacion.getCiudadano().getNif());


		// Request
		MassiveScanWebSimpleGetTransactionIdRequest transacctionIdRequest = new MassiveScanWebSimpleGetTransactionIdRequest();
		transacctionIdRequest.setTransactionName(cleanFileName(infoSesionDigitalizacion.getNombreDocumento()));
		transacctionIdRequest.setScanWebProfile(profileCode);
		transacctionIdRequest.setView(view);
		transacctionIdRequest.setLanguageUI(infoSesionDigitalizacion.getIdioma());
		transacctionIdRequest.setFuncionariUsername(infoSesionDigitalizacion.getFuncionarioHabilitado().getUserName());
		transacctionIdRequest.setSignatureParameters(signatureParameters);
		transacctionIdRequest.setArxiuRequiredParameters(arxiuRequiredParameters);

		// Obtenemos transactionID
		try {
			String transactionID =  invokeApiScanWebSimple("getTransactionID", transacctionIdRequest, String.class);
			return transactionID;
		} catch (Exception e) {
			throw new DigitalizacionPluginException("Error al obtener el transactionID", e);
		}
	}

	@Override
	public String iniciarSesionDigitalizacion(String idSesionDigitalizacion, String urlCallBack) throws DigitalizacionPluginException {
		try {
			// Iniciamos transacción
			MassiveScanWebSimpleStartTransactionRequest startTransactionInfo = new MassiveScanWebSimpleStartTransactionRequest();
			startTransactionInfo.setTransactionID(idSesionDigitalizacion);
			startTransactionInfo.setReturnUrl(urlCallBack);
			// String redirectUrl = api.startTransaction(startTransactionInfo);
			String redirectUrl = invokeApiScanWebSimple("startTransaction", startTransactionInfo, String.class);
			return redirectUrl;
		} catch (Exception e) {
			throw new DigitalizacionPluginException("Error al obtener el transactionID", e);
		}
	}

	@Override
	public ResultadoDigitalizacion obtenerResultadoDigitalizacion(String idSesionDigitalizacion) throws DigitalizacionPluginException {

		ResultadoDigitalizacion res = new ResultadoDigitalizacion();


		try {
			// Recuperamos resultado (solo fichero escaneado y firmado)
			// MassiveScanWebSimpleSubTransactionsOfTransaction subs = api.getSubTransactionsOfTransaction(idSesionDigitalizacion);
			MassiveScanWebSimpleSubTransactionsOfTransaction subs = invokeApiScanWebSimple("getSubTransactionsOfTransaction", idSesionDigitalizacion, MassiveScanWebSimpleSubTransactionsOfTransaction.class);

			if (subs == null || subs.getSubtransacions() == null || subs.getSubtransacions().size() != 1) {
				res.setMensajeError("S'ha rebut un nombre de substransactions diferent a 1 : " + (subs == null ? "null" : subs.getSubtransacions().size()));
			} else {
				MassiveScanWebSimpleSubtransactionResultRequest resultRequest;
				resultRequest = new MassiveScanWebSimpleSubtransactionResultRequest();
				resultRequest.setSubtransactionID(subs.getSubtransacions().get(0));
				resultRequest.setReturnScannedFile(false);
				resultRequest.setReturnSignedFile(true);
				// MassiveScanWebSimpleSubtransactionResult result = api.getSubTransactionResult(resultRequest);
				MassiveScanWebSimpleSubtransactionResult result = invokeApiScanWebSimple("getSubTransactionResult", resultRequest, MassiveScanWebSimpleSubtransactionResult.class);
				MassiveScanWebSimpleStatus transactionStatus = result.getStatus();
				int status = transactionStatus.getStatus();
				if (status == CONSTANTS.getMassiveScanWebSimpleStatusSTATUSFINALOK()) {
					MassiveScanWebSimpleFile signedFile = result.getSignedFile();
					if (signedFile == null) {
						res.setMensajeError("No s'ha rebut cap fitxer escanejat.");
					} else {
						res.setDigitalizado(true);
						res.setNombreFichero(signedFile.getNom());
						res.setDocumento(Base64.getDecoder().decode(signedFile.getData()));
					}
				} else {
					res.setMensajeError("S'ha rebut un estat inconsistent del procés: " + status);
					res.setTrazaError("- ERROR_MESSAGE: " + transactionStatus.getErrorMessage() + "\n" +
									  "- STACK_TRACE: " + transactionStatus.getErrorStackTrace());
				}
			}
			return res;
		} catch (Exception e) {
			throw new DigitalizacionPluginException("Error al obtener el resultado de la digitalización: " + e.getMessage(), e);
		} finally {
				try {
					// api.closeTransaction(idSesionDigitalizacion);
					invokeApiScanWebSimple("closeTransaction", idSesionDigitalizacion, Void.class);
				} catch (Throwable th) {
					log.warn("Error al cerrar la transacción: " + th.getMessage());
				}
		}
	}

	@Override
	public boolean isIframe() throws DigitalizacionPluginException {
		return new Boolean(getPropiedad("iframe"));
	}


	/**
	 * Realiza llamada api.
	 * @return api scan web simple
	 */
	private <T> T invokeApiScanWebSimple(String method, Object requestObject, Class<T> responseType) throws DigitalizacionPluginException {

		String endpoint = getPropiedad("endpoint");
		final String user = getPropiedad("username");
		final String pwd = getPropiedad("password");

		if (!endpoint.endsWith("/")) {
			endpoint += "/";
		}

		String url = endpoint + "secure/apimassivescanwebsimple/v1/" + method;


		final RestTemplate restTemplate = createRestTemplate(user, pwd);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));

		String jsonRequest = null;
        try {
            jsonRequest = JSONUtil.toJSON(requestObject);
        } catch (JSONUtilException e) {
            throw new DigitalizacionPluginException("Error al serializar el objeto a JSON", e);
        }
        HttpEntity<String> request = new HttpEntity<String>(jsonRequest, headers);

		ResponseEntity <T> response = restTemplate.postForEntity(url, request, responseType);

		return response.getBody();
	}


	/**
	 * Limpieza nombre ficheros a petición de digitalib.
	 * @param badFileName Nombre fichero original
	 * @return Nombre fichero limpio
	 */
	private String cleanFileName(String badFileName) {
		StringBuilder cleanName = new StringBuilder();
		for (char c : badFileName.toCharArray()) {
			if (c == ' ' || c == '.' || Character.isJavaIdentifierPart(c))
				cleanName.append(c);
			else
				cleanName.append('_');
		}
		return cleanName.toString();
	}

	private RestTemplate createRestTemplate(String usuario, String password) {

		boolean debug = "true".equalsIgnoreCase(this.getPropiedadOpcional("debug"));

		RestTemplate restTemplate;
		if (debug) {
			restTemplate = new RestTemplate(
					new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory())
			);
			restTemplate.getInterceptors().add(loggingInterceptor());
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(usuario, password));
		} else {
			restTemplate = new RestTemplate();
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(usuario, password));
		}
		return restTemplate;
	}

	// ---- Logging interceptor
	@Bean
	public ClientHttpRequestInterceptor loggingInterceptor() {
		return (request, body, execution) -> {

			log.info("URI: {}", request.getURI());
			log.info("Method: {}", request.getMethod());
			log.info("Headers: {}", request.getHeaders());
			log.info("Request body: {}", new String(body, StandardCharsets.UTF_8));

			ClientHttpResponse response = execution.execute(request, body);

			byte[] responseBytes = StreamUtils.copyToByteArray(response.getBody());
			String responseBody = new String(responseBytes, StandardCharsets.UTF_8);

			log.info("Response status: {}", response.getStatusCode());
			log.info("Response headers: {}", response.getHeaders());
			log.info("Response body: {}", responseBody);

			return response;
		};
	}

}
