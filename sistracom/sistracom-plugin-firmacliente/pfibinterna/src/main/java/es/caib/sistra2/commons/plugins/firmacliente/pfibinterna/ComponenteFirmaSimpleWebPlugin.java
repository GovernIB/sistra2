package es.caib.sistra2.commons.plugins.firmacliente.pfibinterna;

import es.caib.sistra2.commons.plugins.autenticacion.api.TipoMetodoAutenticacion;
import es.caib.sistra2.commons.plugins.autenticacion.api.TipoNivelSeguridad;
import es.caib.sistra2.commons.plugins.firmacliente.api.*;
import es.caib.sistra2.commons.plugins.firmacliente.pfibinterna.model.*;
import es.caib.sistra2.commons.utils.JSONUtil;
import org.apache.commons.codec.binary.Base64;
import org.fundaciobit.pluginsib.core.utils.AbstractPluginProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

/**
 * Plugin componente firma.
 *
 * @author Indra
 */
public class ComponenteFirmaSimpleWebPlugin extends AbstractPluginProperties implements IFirmaPlugin {

    /**
     * Prefix.
     */
    public static final String IMPLEMENTATION_BASE_PROPERTY = "pfibinterna.";

    /**
     * Log.
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor por defecto.
     */
    public ComponenteFirmaSimpleWebPlugin() {
    }

    /**
     * Constructor.
     *
     * @param prefijoPropiedades prefijo props
     * @param properties         propiedades
     */
    public ComponenteFirmaSimpleWebPlugin(final String prefijoPropiedades, final Properties properties) {
        super(prefijoPropiedades, properties);
    }

    @Override
    public String generarSesionFirma(final InfoSesionFirma infoSesionFirma) throws FirmaPluginException, FirmaPluginConnectException {

        // Obtenemos perfil firma y preparamos peticion
        PluginFirmaWebPerfilFirmaConfig perfilFirma = null;
        RCommonInfo commonInfo = null;

        try {
            // Perfil firma a usar en función de configuración.
            perfilFirma = obtenerPerfilFirma(infoSesionFirma.getMetodoAutenticacion(), infoSesionFirma.getNivelSeguridad(), infoSesionFirma.isValidarFirmante());
            // En caso de firma con certificado representación se pasa nif empresa en
            // parámetro organizationId (representante en administrationId / username)
            String administrationId = null;
            String username = null;
            String organizationId = null;
            if (infoSesionFirma.isValidarFirmante()) {
                // Si es representante, pasamos nif empresa en organizationId
                if (infoSesionFirma.getNifRepresentante() != null) {
                    organizationId = infoSesionFirma.getNif();
                    administrationId = infoSesionFirma.getNifRepresentante();
                    username = infoSesionFirma.getNombreRepresentante();
                } else {
                    // Pasamos firmante indicado
                    administrationId = infoSesionFirma.getNif();
                    username = infoSesionFirma.getNombreUsuario();
                }
            } else {
                // Si no hay que validar, vendrá el usuario autenticado
                administrationId = infoSesionFirma.getNif();
                username = infoSesionFirma.getNombreUsuario();
            }
            // Establecemos parametros peticion
            commonInfo = new RCommonInfo();
            commonInfo.setAdministrationID(administrationId);
            commonInfo.setOrganizationID(organizationId);
            commonInfo.setUsername(username);
            commonInfo.setSignerEmail(infoSesionFirma.getEmail());
            commonInfo.setLanguageUI(infoSesionFirma.getIdioma());
            commonInfo.setSignProfile(perfilFirma.getPerfil());
        } catch (final Exception e) {
            throw new FirmaPluginException("Error generando una sesion para firmar.", e);
        }

        // Realizamos peticion y control especifico para ver que tenemos conexión inicial
        ResponseEntity<String> response = null;
        try {
            // Preparamos rest template
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RCommonInfo> entity = new HttpEntity<>(commonInfo, headers);
            RestTemplate restTemplate = createRestTemplate(perfilFirma.getUsuario(), perfilFirma.getPassword());
            // Realizamos peticion para obtener transaction id
            response = restTemplate.postForEntity(
                    getPropiedad("url") + "/secure/directsignatureonweb/v1/getTransactionID",
                    entity,
                    String.class
            );
        } catch (ResourceAccessException e) {
            // Manejo de problemas de conexión
            throw new FirmaPluginConnectException("Problemas de conexión o acceso al servidor: " + e.getMessage(), e);
        } catch (HttpStatusCodeException e) {
            // Manejo de errores HTTP (4xx, 5xx)
            throw new FirmaPluginConnectException("Error HTTP al realizar la solicitud: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (RestClientException e) {
            // Manejo genérico de excepciones
            throw new FirmaPluginConnectException("Error inesperado al conectar: " + e.getMessage(), e);
        }

        // Controlamos si la respuesta es correcta
        if (!(response.getStatusCode().is2xxSuccessful() && response.getBody() != null)) {
            throw new FirmaPluginConnectException("Error en la respuesta HTTP: Código de estado " + response.getStatusCode());
        }

        // Obtenemos transaction ID de la respuesta
        String transactionID = response.getBody();

        // Retornamos id sesión firma: perfil(B64) + transactionID
        String perfilFirmaB64 = null;
        try {
            perfilFirmaB64 = JSONUtil.toJSON(perfilFirma, false);
            perfilFirmaB64 = new String(Base64.encodeBase64(perfilFirmaB64.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new FirmaPluginException("Error generando una sesion para firmar.", e);
        }
        String sesionID = perfilFirmaB64 + "." + transactionID;
        return sesionID;

    }

    @Override
    public void anyadirFicheroAFirmar(final FicheroAFirmar ficheroAFirmar) throws FirmaPluginException {
        try {

            // Perfil de firma y transaction id
            PluginFirmaWebPerfilFirmaConfig perfilFirma = obtienerPerfilFirmaFromSesionId(ficheroAFirmar.getSesion());
            String transactionId = obtenerTransactionIdFromSesionId(ficheroAFirmar.getSesion());

            RDocument document = new RDocument();
            document.setData(ficheroAFirmar.getFichero());
            document.setMime(ficheroAFirmar.getMimetypeFichero());
            document.setName(ficheroAFirmar.getNombreFichero());

            RFileInfoSignature fileInfoSignature = new RFileInfoSignature();
            fileInfoSignature.setSignID(ficheroAFirmar.getSignID());
            fileInfoSignature.setFileToSign(document);
            fileInfoSignature.setName(ficheroAFirmar.getNombreFichero());
            fileInfoSignature.setReason(ficheroAFirmar.getRazon());
            fileInfoSignature.setLocation(ficheroAFirmar.getLocalizacion());
            fileInfoSignature.setSignNumber(ficheroAFirmar.getSignNumber());
            fileInfoSignature.setLanguageSign(ficheroAFirmar.getIdioma());
            fileInfoSignature.setDocumentType(convertTypeTipoDocumental(ficheroAFirmar.getTipoDocumental()));


            RAddFileToSignRequest fileToSignRequest = new RAddFileToSignRequest();
            fileToSignRequest.setTransactionID(transactionId);
            fileToSignRequest.setFileInfoSignature(fileInfoSignature);


            // Realizamos peticion para obtener transaction id
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RAddFileToSignRequest> entity = new HttpEntity<>(fileToSignRequest, headers);
            RestTemplate restTemplate = createRestTemplate(perfilFirma.getUsuario(), perfilFirma.getPassword());
            ResponseEntity<String> response = restTemplate.postForEntity(
                    getPropiedad("url") + "/secure/directsignatureonweb/v1/addFileToSign",
                    entity,
                    String.class
            );
            String data = response.getBody();
            System.out.println("Respuesta addFileToSign: " + data);

        } catch (final Exception e) {
            throw new FirmaPluginException("Error generando una sesion para firmar.", e);
        }
    }

    @Override
    public String iniciarSesionFirma(final String idSesionFirma, final String urlCallBack, final String paramAdic)
            throws FirmaPluginException {
        try {
            // Perfil de firma y transaction id
            PluginFirmaWebPerfilFirmaConfig perfilFirma = obtienerPerfilFirmaFromSesionId(idSesionFirma);
            String transactionId = obtenerTransactionIdFromSesionId(idSesionFirma);

            // Establecemos parametros peticion
            RStartTransactionRequest startTransactionInfo = new RStartTransactionRequest();
            startTransactionInfo.setTransactionID(transactionId);
            startTransactionInfo.setView(("true".equalsIgnoreCase(getPropiedad("iframe"))) ? "iframe" : "fullview");
            startTransactionInfo.setReturnUrl(urlCallBack);

            // Realizamos peticion para empezar sesion firma
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RStartTransactionRequest> entity = new HttpEntity<>(startTransactionInfo, headers);
            RestTemplate restTemplate = createRestTemplate(perfilFirma.getUsuario(), perfilFirma.getPassword());
            ResponseEntity<String> response = restTemplate.postForEntity(
                    getPropiedad("url") + "/secure/directsignatureonweb/v1/startTransaction",
                    entity,
                    String.class
            );
            String url = response.getBody();

            // Retornamos url para redirigir al usuario a realizar la firma
            return url;
        } catch (final Exception e) {
            throw new FirmaPluginException("Error empezando la transaction", e);
        }
    }

    @Override
    public EstadoFirma obtenerEstadoSesionFirma(final String idSesionFirma) throws FirmaPluginException {
        try {
            // Perfil de firma y transaction id
            PluginFirmaWebPerfilFirmaConfig perfilFirma = obtienerPerfilFirmaFromSesionId(idSesionFirma);
            String transactionId = obtenerTransactionIdFromSesionId(idSesionFirma);

            // Invoca para obtener estado
            RestTemplate restTemplate = createRestTemplate(perfilFirma.getUsuario(), perfilFirma.getPassword());
            ResponseEntity<RTransactionStatusResponse> response = restTemplate.getForEntity(
                    getPropiedad("url") + "/secure/directsignatureonweb/v1/getTransactionStatus/" + transactionId,
                    RTransactionStatusResponse.class
            );
            RTransactionStatusResponse fullTransactionStatus = response.getBody();

            // Calcula estado firma a partir del status de la transaction
            RProcessStatus transactionStatus = fullTransactionStatus.getTransactionStatus();
            TypeEstadoFirmado estadoFirmado = TypeEstadoFirmado.fromInt(transactionStatus.getStatus());
            final String errorCode = transactionStatus.getErrorCode();
            final String errorMessage = transactionStatus.getErrorMessage();

            // Calcula metodo firma
            String metodoFirma = null;
            if (fullTransactionStatus.getSignPlugin() != null) {
                metodoFirma = fullTransactionStatus.getSignPlugin().getSignaturePluginNameInternal() +
                        " - " + fullTransactionStatus.getSignPlugin().getSignaturePluginNamePublic();
            }

            // Retorna estado firma
            EstadoFirma estado = new EstadoFirma();
            estado.setCodigoError(errorCode);
            estado.setEstadoFirmado(estadoFirmado);
            estado.setMensajeError(errorMessage);
            estado.setMetodoFirma(metodoFirma);
            return estado;
        } catch (final Exception e) {
            throw new FirmaPluginException("Error viendo el status de la transaction", e);
        }
    }

    @Override
    public FicheroFirmado obtenerFirmaFichero(final String idSesionFirma, final String signID)
            throws FirmaPluginException {
        try {

            // Perfil de firma y transaction id
            PluginFirmaWebPerfilFirmaConfig perfilFirma = obtienerPerfilFirmaFromSesionId(idSesionFirma);
            String transactionId = obtenerTransactionIdFromSesionId(idSesionFirma);

            // Invoca para obtener firma del fichero
            RestTemplate restTemplate = createRestTemplate(perfilFirma.getUsuario(), perfilFirma.getPassword());
            ResponseEntity<RSignatureResponse> response = restTemplate.getForEntity(
                    getPropiedad("url") + "/secure/directsignatureonweb/v1/getSignatureResult/" + transactionId + "/" + signID,
                    RSignatureResponse.class
            );
            RSignatureResponse fssr = response.getBody();

            // Evalua estado firma y en funcion de ello, retorna el fichero firmado o el error
            FicheroFirmado fic = null;
            final TypeEstadoFirmado fst = TypeEstadoFirmado.fromInt(fssr.getStatus().getStatus());
            if (fst != TypeEstadoFirmado.FINALIZADO_OK) {
                // Retornamos estado de firma no correcto con el mensaje de error
                EstadoFirma estadoFirma = new EstadoFirma();
                estadoFirma.setEstadoFirmado(fst);
                estadoFirma.setCodigoError(fssr.getStatus().getErrorCode());
                estadoFirma.setMensajeError(fssr.getStatus().getErrorMessage());
                fic = new FicheroFirmado();
                fic.setEstadoFirma(estadoFirma);
            } else {
                // Retornamos firma correcta
                // - Tipo firma
                final TypeFirmaDigital tipoFirma = TypeFirmaDigital.fromString(fssr.getSignedFileInfo().getEniTipoFirma());
                if (tipoFirma == null) {
                    throw new FirmaPluginException(
                            "No se reconoce tipo de firma " + fssr.getSignedFileInfo().getEniTipoFirma());
                }
                // - Metodo firma
                String metodoFirma = null;
                // TODO VER SI LIST SOLO TIENE 1 FIRMANTE
                if (fssr.getSignedFileInfo().getSigners() != null &&
                        !fssr.getSignedFileInfo().getSigners().isEmpty() &&
                        fssr.getSignedFileInfo().getSigners().get(0).getAdditionalInformation() != null &&
                        fssr.getSignedFileInfo().getSigners().get(0).getSignPlugin() != null) {
                    String pluginName = fssr.getSignedFileInfo().getSigners().get(0).getSignPlugin().getSignaturePluginNameInternal();
                    String pluginDesc = fssr.getSignedFileInfo().getSigners().get(0).getSignPlugin().getSignaturePluginNamePublic();
                    if (pluginName != null || pluginDesc != null) {
                        metodoFirma = pluginName + " - " + pluginDesc;
                    }
                }
                // - Retornamos fichero firmado
                EstadoFirma estadoFirma = new EstadoFirma();
                estadoFirma.setEstadoFirmado(TypeEstadoFirmado.FINALIZADO_OK);
                RDocument fsf = fssr.getSignedFile();
                fic = new FicheroFirmado();
                fic.setEstadoFirma(estadoFirma);
                fic.setFirmaFichero(fsf.getData());
                fic.setMimetypeFichero(fsf.getMime());
                fic.setNombreFichero(fsf.getName());
                fic.setFirmaTipo(tipoFirma);
                fic.setMetodoFirma(metodoFirma);
            }

            // Retorna fichero (con estado de firma y error en caso de que lo haya)
            return fic;

        } catch (final Exception e) {
            throw new FirmaPluginException("Error obtenido el resultado del fichero", e);
        }

    }

    @Override
    public void cerrarSesionFirma(final String idSesionFirma) throws FirmaPluginException {
        try {
            // Perfil de firma y transaction id
            PluginFirmaWebPerfilFirmaConfig perfilFirma = obtienerPerfilFirmaFromSesionId(idSesionFirma);
            String transactionId = obtenerTransactionIdFromSesionId(idSesionFirma);

            // Invoca para cerrar sesión firma
            RestTemplate restTemplate = createRestTemplate(perfilFirma.getUsuario(), perfilFirma.getPassword());
            ResponseEntity<String> response = restTemplate.getForEntity(
                    getPropiedad("url") + "/secure/directsignatureonweb/v1/closeTransaction/" + transactionId,
                    String.class
            );
        } catch (final Exception e) {
            throw new FirmaPluginException("Error cerrando la sesion firma", e);
        }
    }

    @Override
    public boolean isVerificarFirma() throws FirmaPluginException {
        return new Boolean(getPropiedad("verificarFirma"));
    }

    @Override
    public boolean isIframe() throws FirmaPluginException {
        return new Boolean(getPropiedad("iframe"));
    }


    // -------------------------------------------------------------------------------------------------------------
    // Métodos privados
    // -------------------------------------------------------------------------------------------------------------

    /**
     * Obtiene el perfil de firma según el método de autenticación y nivel de
     * seguridad.
     *
     * @param metodoAutenticacion Método de autenticación
     * @param nivelSeguridad      Nivel de seguridad
     * @param validarFirmante     Indica si se debe validar el firmante
     * @return perfil de firma
     */
    private PluginFirmaWebPerfilFirmaConfig obtenerPerfilFirma(TipoMetodoAutenticacion metodoAutenticacion,
                                                               TipoNivelSeguridad nivelSeguridad, boolean validarFirmante) throws FirmaPluginException {

        PluginFirmaWebPerfilFirmaConfig perfilFirma = null;
        List<PluginFirmaWebMetodoNivelConfig> config;

        // Parseamos JSON configuración
        try {
            String configJson = getPropiedad("configuracion");
            config = (List) JSONUtil.fromListJSON(configJson, PluginFirmaWebMetodoNivelConfig.class);
        } catch (Exception e) {
            throw new FirmaPluginException("Error leyendo propiedad configuracion de perfiles de firma", e);
        }

        // Obtenemos perfil de firma según método autenticación y nivel seguridad
        for (PluginFirmaWebMetodoNivelConfig cfg : config) {
            if (cfg.getMetodoAutenticacion() == metodoAutenticacion && cfg.getNivelesSeguridad().contains(nivelSeguridad)) {
                perfilFirma = (validarFirmante ? cfg.getPerfilFirmaConVerificacion() : cfg.getPerfilFirmaSinVerificacion());
                break;
            }
        }

        // Si no se ha encontrado perfil de firma, lanzamos excepción
        if (perfilFirma == null) {
            throw new FirmaPluginException("No se ha encontrado perfil de firma para método de autenticación "
                    + metodoAutenticacion + " y nivel de seguridad " + nivelSeguridad + " "
                    + (validarFirmante ? "con" : "sin") + " validación de firmante");
        }

        // Retornamos perfil de firma
        return perfilFirma;
    }

    /**
     * Obtiene propiedad (obligatoria).
     *
     * @param propiedad propiedad
     * @return valor
     * @throws FirmaPluginException
     */
    private String getPropiedad(final String propiedad) throws FirmaPluginException {
        final String res = getProperty(FIRMACLIENTE_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
        if (res == null) {
            throw new FirmaPluginException("No se ha especificado parametro " + propiedad + " en propiedades");
        }
        return res;
    }

    /**
     * Obtiene propiedad (opcional)
     * .
     *
     * @param propiedad propiedad
     * @return valor
     */
    private String getPropiedadOpcional(final String propiedad) {
        final String res = getProperty(FIRMACLIENTE_BASE_PROPERTY + IMPLEMENTATION_BASE_PROPERTY + propiedad);
        return res;
    }

    /**
     * Convierte tipo documental a código.
     *
     * @param tipoDocumental Tipo documental
     * @return código
     * @throws FirmaPluginException
     */
    private Long convertTypeTipoDocumental(final TypeTipoDocumental tipoDocumental) throws FirmaPluginException {
        // Quitamos el TD y nos quedamos con el numero
        // TD99 --> 99
        Long res = null;
        if (tipoDocumental != null) {
            try {
                res = Long.parseLong(tipoDocumental.toString().substring(2));
            } catch (final NumberFormatException nfe) {
                throw new FirmaPluginException("Error mapeo código para tipo documental " + tipoDocumental);
            }
        }
        return res;
    }

    /**
     * Obtiene perfil de firma a partir del id de sesión.
     *
     * @param sessionId id de sesión
     * @return perfil de firma
     * @throws FirmaPluginException
     */
    private PluginFirmaWebPerfilFirmaConfig obtienerPerfilFirmaFromSesionId(String sessionId) throws FirmaPluginException {
        try {
            String perfilFirmaB64 = sessionId.substring(0, sessionId.indexOf('.'));
            String perfilFirmaJson = new String(Base64.decodeBase64(perfilFirmaB64.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            return (PluginFirmaWebPerfilFirmaConfig) JSONUtil.fromJSON(perfilFirmaJson, PluginFirmaWebPerfilFirmaConfig.class);
        } catch (Exception e) {
            throw new FirmaPluginException("No se ha podido obtener perfil de firma del sesion id: " + sessionId, e);
        }
    }

    /**
     * Obtiene transaction id a partir del id de sesión
     *
     * @param sessionId id de sesión
     * @return transaction id
     */
    private String obtenerTransactionIdFromSesionId(String sessionId) {
        return sessionId.substring(sessionId.indexOf('.') + 1);
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
