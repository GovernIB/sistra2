package es.caib.sistramit.frontend.security;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistra2.commons.utils.UserAgentUtil;
import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.exception.LoginException;
import es.caib.sistramit.core.api.model.comun.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.security.InfoLoginTramite;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.service.SecurityService;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.frontend.Errores;
import es.caib.sistramit.frontend.SesionHttp;
import es.caib.sistramit.frontend.model.ErrorGeneral;
import es.caib.sistramit.frontend.model.LoginFormInfo;
import es.caib.sistramit.frontend.model.LoginTicketInfo;
import es.caib.sistramit.frontend.model.RespuestaJSON;

/**
 * Controlador de login.
 *
 *
 * @author Indra
 */
@Controller
public final class LoginController {

    /** Log. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(LoginController.class);

    /** Atributo constante LOGIN de LoginController. */
    private static final String LOGIN = "login";

    /** Atributo constante IDIOMA de LoginController. */
    private static final String IDIOMA = "idioma";

    /** Configuracion. */
    @Autowired
    private SystemService systemService;

    /** Informacion de sesion de tramitacion. */
    @Autowired
    private SesionHttp sesionHttp;

    /** Generacion errores. */
    @Autowired
    private Errores errores;

    /** Servicio de seguridad. */
    @Autowired
    private SecurityService securityService;

    /**
     * Muestra pantalla de login según el punto de entrada.
     *
     * @param request
     *            Request
     * @param response
     *            Response
     * @return Login
     */
    @RequestMapping("/login.html")
    public ModelAndView login(final HttpServletRequest request,
            final HttpServletResponse response) {

        // Error login
        if ("true".equals(request.getParameter("error"))) {
            throw new ErrorFrontException("Error login");
        }

        // Establecemos user agent en info sesion
        final String userAgent = request.getHeader("User-Agent");
        final String userAgentNormalized = UserAgentUtil
                .serializeUserAgent(userAgent);
        sesionHttp.setUserAgent(userAgentNormalized);

        // Obtenemos url original
        final SavedRequest savedRequest = new HttpSessionRequestCache()
                .getRequest(request, response);
        if (savedRequest == null) {
            throw new ErrorFrontException(
                    "Punto de entrada a la aplicación no válido");
        }
        final String url = savedRequest.getRedirectUrl();

        // Obtiene punto entrada
        final String puntoEntrada = getPuntoEntrada(url);

        // Guardamos url original para poder redirigir clave
        if (!ConstantesSeguridad.PUNTOENTRADA_ACCESO_CLAVE
                .equals(puntoEntrada)) {
            sesionHttp.setUrlInicio(url);
        }

        // Establecemos idioma que viene en la saved request
        final String idiomaSavedRequest = StringUtils
                .defaultIfEmpty(getParamValue(savedRequest, IDIOMA), "es");
        sanitizeIdioma(idiomaSavedRequest);
        RequestContextUtils.getLocaleResolver(request).setLocale(request,
                response, new Locale(idiomaSavedRequest));
        sesionHttp.setIdioma(idiomaSavedRequest);

        // En función del punto de entrada realizamos login
        ModelAndView login = null;
        if (ConstantesSeguridad.PUNTOENTRADA_INICIAR_TRAMITE
                .equals(puntoEntrada)) {
            // Inicio tramite: mostrar pagina login
            login = autenticarFormLogin(savedRequest);
        } else if (ConstantesSeguridad.PUNTOENTRADA_CARGAR_TRAMITE
                .equals(puntoEntrada)) {
            // Si existe ticket carpeta autenticamos via ticket, sino
            // autenticamos de forma
            // anonima automaticamente
            if (existeTicket(savedRequest, ConstantesSeguridad.TICKET_PARAM)) {
                login = autenticarTicket(savedRequest,
                        ConstantesSeguridad.TICKET_USER_CARPETA,
                        ConstantesSeguridad.TICKET_PARAM);
            } else {
                login = autenticarFormLoginAnonimo();
            }
        } else if (ConstantesSeguridad.PUNTOENTRADA_ACCESO_CLAVE
                .equals(puntoEntrada)) {
            login = autenticarTicket(savedRequest,
                    ConstantesSeguridad.TICKET_USER_CLAVE,
                    ConstantesSeguridad.TICKET_PARAM);
        } else if (ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_FORMULARIO_EXTERNO
                .equals(puntoEntrada)) {
            login = autenticarTicket(savedRequest,
                    ConstantesSeguridad.TICKET_USER_GF,
                    ConstantesSeguridad.TICKET_PARAM);
        } else if (ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_PAGO_EXTERNO
                .equals(puntoEntrada)) {
            login = autenticarTicket(savedRequest,
                    ConstantesSeguridad.TICKET_USER_CARPETA,
                    ConstantesSeguridad.TICKET_PARAM);
        } else {
            throw new ErrorFrontException(
                    "Punto de entrada a la aplicación no válido: " + url);
        }

        // Metemos atributo en la session para que el filtro de autologout no
        // cierre la sesion
        request.getSession().setAttribute(
                ConstantesSeguridad.AUTOLOGOUT_FROMLOGIN,
                ConstantesSeguridad.AUTOLOGOUT_FROMLOGIN);

        // Redirigimos a vista para realizar el login
        return login;
    }

    @RequestMapping("/redirigirClave.html")
    public ModelAndView redirigirClave() {
        String lang = sesionHttp.getIdioma();
        if (lang == null) {
            lang = "es";
        }
        final String urlCallback = systemService.obtenerPropiedadConfiguracion(
                TypePropiedadConfiguracion.URL_SISTRAMIT)
                + ConstantesSeguridad.PUNTOENTRADA_ACCESO_CLAVE;
        return new ModelAndView("redirect:"
                + securityService.iniciarSesionClave(lang, urlCallback));
    }

    @RequestMapping(ConstantesSeguridad.PUNTOENTRADA_ACCESO_CLAVE)
    public ModelAndView accesoClave(final HttpServletRequest request,
            final HttpServletResponse response) {
        try {
            response.sendRedirect(sesionHttp.getUrlInicio());
        } catch (final IOException e) {
            throw new LoginException("Error al redirigir tras acceso clave.");
        }
        return null;
    }

    @RequestMapping("/logout.html")
    public ModelAndView redirigirLogout() {
        String lang = sesionHttp.getIdioma();
        if (lang == null) {
            lang = "es";
        }
        final String urlCallback = systemService.obtenerPropiedadConfiguracion(
                TypePropiedadConfiguracion.URL_SISTRAMIT)
                + ConstantesSeguridad.PUNTOENTRADA_LOGOUT_CLAVE;
        return new ModelAndView("redirect:"
                + securityService.iniciarLogoutSesionClave(lang, urlCallback));
    }

    @RequestMapping(ConstantesSeguridad.PUNTOENTRADA_LOGOUT_CLAVE)
    public ModelAndView logoutClave(
            final @RequestParam(required = true) boolean logout,
            final HttpServletRequest request,
            final HttpServletResponse response) {
        ModelAndView view = null;
        if (logout) {
            view = new ModelAndView(
                    "redirect:" + "/asistente/cerrarSesion.html");
        } else {
            // Cierre de sesión exception
            throw new ErrorFrontException(
                    "Ha ocurrido un error al realizar el cierre de sesión");
        }
        return view;
    }

    /**
     * Sanitiza idioma. En caso de no estar soportado generará una
     * LoginException.
     *
     * @param pIdiomaSavedRequest
     *            Idioma login
     */
    private void sanitizeIdioma(final String pIdiomaSavedRequest) {
        final String idiomasSoportados = systemService
                .obtenerPropiedadConfiguracion(
                        TypePropiedadConfiguracion.IDIOMAS_SOPORTADOS);
        final String langs[] = idiomasSoportados.split(",");
        boolean soportado = false;
        for (final String lang : langs) {
            if (lang.equals(pIdiomaSavedRequest)) {
                soportado = true;
                break;
            }
        }
        if (!soportado) {
            throw new ErrorFrontException("Idioma no soportado");
        }
    }

    /**
     * Autentica via ticket.
     *
     * @param pSavedRequest
     *            Request
     * @param pTicketName
     *            Nombre ticket
     * @param pTicketUser
     *            Usuario asociado al tipo de ticket
     * @return Vista que realiza el login automáticamente
     */
    private ModelAndView autenticarTicket(final SavedRequest pSavedRequest,
            final String pTicketUser, final String pTicketName) {
        // Obtenemos ticket de la peticion
        final String[] tickets = pSavedRequest.getParameterMap()
                .get(pTicketName);
        if (tickets == null || tickets.length != ConstantesNumero.N1) {
            throw new ErrorFrontException("No existe ticket");
        }
        final String ticket = tickets[0];

        // Autenticamos automaticamente
        final LoginTicketInfo li = new LoginTicketInfo();
        li.setTicketName(pTicketUser);
        li.setTicketValue(ticket);
        final String idiomaSavedRequest = StringUtils
                .defaultIfEmpty(getParamValue(pSavedRequest, IDIOMA), "es");
        li.setIdioma(idiomaSavedRequest);
        return new ModelAndView("loginTicket", LOGIN, li);
    }

    /**
     * Autentica via formulario de login.
     *
     * @param savedRequest
     *            Request
     *
     * @return Vista para autenticar con formulario de login
     */
    private ModelAndView autenticarFormLogin(final SavedRequest savedRequest) {

        // Obtenemos parametros inicio tramite
        final String paramCodigoTramite = getParamValue(savedRequest,
                "tramite");
        final String paramVersionTramite = getParamValue(savedRequest,
                "version");
        final String paramIdioma = getParamValue(savedRequest, IDIOMA);
        final String paramLogin = getParamValue(savedRequest, LOGIN);
        final String paramIdTramiteCP = getParamValue(savedRequest,
                "idTramiteCatalogo");

        // Obtenemos info login tramite
        final InfoLoginTramite infoLoginTramite = securityService
                .obtenerInfoLoginTramite(paramCodigoTramite,
                        Integer.parseInt(paramVersionTramite), paramIdTramiteCP,
                        paramIdioma, savedRequest.getRedirectUrl());

        // Comprobamos si hay que filtrar el metodo de autenticacion
        boolean anonimo = false;
        boolean autenticado = false;
        TypeAutenticacion filtroAutenticacion = null;
        if (paramLogin != null) {
            filtroAutenticacion = TypeAutenticacion.fromString(paramLogin);
            // Si no esta permitido el filtro, no lo aplicacmos
            if (!infoLoginTramite.getNiveles().contains(filtroAutenticacion)) {
                filtroAutenticacion = null;
            }
        }
        if (filtroAutenticacion == TypeAutenticacion.ANONIMO) {
            anonimo = true;
        } else if (filtroAutenticacion == TypeAutenticacion.AUTENTICADO) {
            autenticado = true;
        } else {
            autenticado = infoLoginTramite.getNiveles()
                    .contains(TypeAutenticacion.AUTENTICADO);
            anonimo = infoLoginTramite.getNiveles()
                    .contains(TypeAutenticacion.ANONIMO);
        }

        // Devolvemos formulario de login
        final LoginFormInfo li = new LoginFormInfo();
        li.setTituloTramite(infoLoginTramite.getTitulo());
        li.setIdioma(StringUtils.defaultIfEmpty(paramIdioma, "es"));
        li.setLoginAnonimo(anonimo);
        li.setLoginAutenticado(autenticado);
        li.setUrlLogout(infoLoginTramite.getUrlEntidad());
        li.setAvisos(infoLoginTramite.getAvisos());
        return new ModelAndView(LOGIN, LOGIN, li);
    }

    /**
     * Obtiene valor parametro de la request.
     *
     * @param savedRequest
     *            Saved request
     * @param paramName
     *            Nombre parametro
     * @return Valor parametro (null si no existe)
     */
    protected String getParamValue(final SavedRequest savedRequest,
            final String paramName) {
        final String[] paramValues = savedRequest.getParameterMap()
                .get(paramName);
        String param = null;
        if (paramValues != null && paramValues.length > 0) {
            param = paramValues[0];
        }
        return param;
    }

    /**
     * Autentica via formulario de login de forma anonima (automatico).
     *
     * @return Vista para autenticar con formulario de login
     */
    private ModelAndView autenticarFormLoginAnonimo() {
        final LoginFormInfo li = new LoginFormInfo();
        li.setLoginAnonimo(true);
        li.setIdioma("es");
        return new ModelAndView(LOGIN, LOGIN, li);
    }

    /**
     * Obtiene punto de entrada.
     *
     * @param urlOrg
     *            Url origen
     * @return Punto de entrada
     */
    private String getPuntoEntrada(final String urlOrg) {
        try {
            final URL url = new URL(urlOrg);
            String path = url.getPath();
            path = path.substring(ConstantesNumero.N1);
            int pos = path.indexOf("/");
            if (pos == ConstantesNumero.N_1) {
                pos = 0;
            }
            return path.substring(pos);
        } catch (final MalformedURLException mfe) {
            throw new ErrorFrontException("Url incorrecta: " + urlOrg);
        }

    }

    /**
     * Comprueba si existe ticket en request.
     *
     * @param pSavedRequest
     *            Request
     * @param pTicketName
     *            Ticket
     * @return boolean
     */
    private boolean existeTicket(final SavedRequest pSavedRequest,
            final String pTicketName) {
        boolean res = true;
        final String[] tickets = pSavedRequest.getParameterMap()
                .get(pTicketName);
        if (tickets == null || tickets.length != ConstantesNumero.N1) {
            res = false;
        }
        return res;
    }

    /**
     * Handler de excepciones de negocio.
     *
     * @param ex
     *            Excepción de la capa de servicio
     * @param request
     *            Request
     * @return Respuesta JSON indicando el mensaje producido
     */
    @ExceptionHandler({Exception.class})
    public ModelAndView handleServiceException(final Exception ex,
            final HttpServletRequest request) {

        // TODO Auditar ErrorFrontException en login
        LOGGER.error("Excepcion login", ex);

        // Obtenemos idioma
        String idioma;
        if (request.getParameter(IDIOMA) != null) {
            idioma = request.getParameter(IDIOMA);
        } else {
            idioma = "es";
        }

        // Generamos respuesta JSON
        final RespuestaJSON res = errores.generarRespuestaJsonExcepcion(ex,
                idioma);

        // Redirigimos a pagina de error
        final ErrorGeneral error = new ErrorGeneral();
        error.setIdioma(idioma);
        error.setMensaje(res.getMensaje());
        error.setUrl(res.getUrl());
        return new ModelAndView("error", "error", error);

    }

}
