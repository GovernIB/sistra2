package es.caib.sistramit.frontend.security;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.exception.LoginException;
import es.caib.sistramit.core.api.model.security.ConstantesSeguridad;
import es.caib.sistramit.core.api.model.security.InfoLoginTramite;
import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.service.SecurityService;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.frontend.Errores;
import es.caib.sistramit.frontend.SesionHttp;
import es.caib.sistramit.frontend.model.ErrorGeneral;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

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

	/** Cache peticiones. */
	@Autowired
	private LoginRequestCache loginRequestCache;

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
	public ModelAndView login(final HttpServletRequest request, final HttpServletResponse response) {

		// Error login
		if ("true".equals(request.getParameter("error"))) {
			throw new ErrorFrontException("Error login");
		}

		// Establecemos user agent en info sesion
		final String userAgent = request.getHeader("User-Agent");
		sesionHttp.setUserAgent(userAgent);

		// Obtenemos url original
		// final SavedRequest savedRequest = new
		// HttpSessionRequestCache().getRequest(request, response);
		final SavedRequest savedRequest = loginRequestCache.getRequest(request, response);

		if (savedRequest == null) {
			throw new ErrorFrontException("Punto de entrada a la aplicación no válido");
		}
		final String url = savedRequest.getRedirectUrl();

		// Obtiene punto entrada
		final String puntoEntrada = getPuntoEntrada(url);

		// Guardamos url original para poder redirigir clave
		if (!ConstantesSeguridad.PUNTOENTRADA_RETORNO_AUTENTICACION_LOGIN.equals(puntoEntrada)) {
			sesionHttp.setUrlInicio(url);
		}

		// Establecemos idioma que viene en la saved request y si no viene ninguno el
		// idioma por defecto
		final String idiomaSavedRequest = getParamValue(savedRequest, IDIOMA);
		final String idiomasSoportados = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMAS_SOPORTADOS);
		final String idioma = sanitizeIdioma(idiomaSavedRequest, idiomasSoportados);
		RequestContextUtils.getLocaleResolver(request).setLocale(request, response, new Locale(idioma));
		sesionHttp.setIdioma(idioma);

		// En función del punto de entrada realizamos login
		ModelAndView login = null;
		if (ConstantesSeguridad.PUNTOENTRADA_INICIAR_TRAMITE.equals(puntoEntrada)) {
			// Inicio tramite: mostrar pagina login
			login = autenticarFormLogin(savedRequest);
		} else if (ConstantesSeguridad.PUNTOENTRADA_CARGAR_TRAMITE.equals(puntoEntrada)) {
			// Si existe ticket carpeta autenticamos via ticket, sino
			// autenticamos de forma anonima automaticamente
			if (existeTicket(savedRequest, ConstantesSeguridad.TICKET_PARAM)) {
				login = autenticarTicket(savedRequest, ConstantesSeguridad.TICKET_USER_CARPETA,
						ConstantesSeguridad.TICKET_PARAM);
			} else {
				login = autenticarFormLoginPersistenteAnonimo(savedRequest);
			}
		} else if (ConstantesSeguridad.PUNTOENTRADA_RETORNO_AUTENTICACION_LOGIN.equals(puntoEntrada)) {
			login = autenticarTicket(savedRequest, ConstantesSeguridad.TICKET_USER_CLAVE,
					ConstantesSeguridad.TICKET_PARAM);
		} else if (ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_FORMULARIO_EXTERNO.equals(puntoEntrada)) {
			login = autenticarTicket(savedRequest, ConstantesSeguridad.TICKET_USER_GF,
					ConstantesSeguridad.TICKET_PARAM);
		} else if (ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_PAGO_EXTERNO.equals(puntoEntrada)) {
			login = autenticarTicket(savedRequest, ConstantesSeguridad.TICKET_USER_PAGO,
					ConstantesSeguridad.TICKET_PARAM);
		} else if (ConstantesSeguridad.PUNTOENTRADA_RETORNO_CARPETA.equals(puntoEntrada)) {
			login = autenticarTicket(savedRequest, ConstantesSeguridad.TICKET_USER_CARPETA,
					ConstantesSeguridad.TICKET_PARAM);
		} else {
			throw new ErrorFrontException("Punto de entrada a la aplicación no válido: " + url);
		}

		// Metemos atributo en la session para que el filtro de autologout no
		// cierre la sesion
		request.getSession().setAttribute(ConstantesSeguridad.AUTOLOGOUT_FROMLOGIN,
				ConstantesSeguridad.AUTOLOGOUT_FROMLOGIN);

		// Redirigimos a vista para realizar el login
		return login;
	}

	/**
	 * Redirige a componente de autenticacion.
	 *
	 * @param metodosAutenticacion
	 *            metodos autenticacion (separados por ;)
	 * @return redirige a componente de autenticacion.
	 */
	@RequestMapping("/redirigirAutenticacionLogin.html")
	public ModelAndView redirigirAutenticacionLogin(@RequestParam("entidad") String idEntidad,
			@RequestParam("metodosAutenticacion") String metodosAutenticacion,
			@RequestParam(name = "qaa", required = false) String qaa,
			@RequestParam(name = "debug", required = false) boolean debug) {
		String lang = sesionHttp.getIdioma();
		if (lang == null) {
			lang = "es";
		}

		final List<TypeAutenticacion> authList = new ArrayList<>();
		final String[] auths = metodosAutenticacion.split(";");
		for (final String a : auths) {
			authList.add(TypeAutenticacion.fromString(a));
		}

		final String urlCallback = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL)
				+ ConstantesSeguridad.PUNTOENTRADA_RETORNO_AUTENTICACION_LOGIN + "?idioma=" + lang;
		final String urlCallbackError = sesionHttp.getUrlInicio();
		return new ModelAndView("redirect:" + securityService.iniciarSesionAutenticacion(idEntidad, lang, authList, qaa,
				urlCallback, urlCallbackError, debug));
	}

	/**
	 * Retorno de componente de autenticacion.
	 *
	 * @return retorno de componente de autenticación redirigiendo a url de inicio.
	 */
	@CrossOrigin
	@RequestMapping(ConstantesSeguridad.PUNTOENTRADA_RETORNO_AUTENTICACION_LOGIN)
	public ModelAndView retornoAutenticacionLogin(final HttpServletRequest request,
			final HttpServletResponse response) {
		try {
			response.sendRedirect(sesionHttp.getUrlInicio());
		} catch (final IOException e) {
			throw new LoginException("Error al redirigir tras acceso componente autenticacion.");
		}
		return null;
	}

	/**
	 * Redirección a componente autenticación para realizar logout.
	 *
	 * @return Redirección a componente autenticación para realizar logout.
	 */
	@CrossOrigin
	@RequestMapping("/redirigirAutenticacionLogout.html")
	public ModelAndView redirigirAutenticacionLogout(@RequestParam("entidad") String idEntidad,
			@RequestParam(name = "debug", required = false) boolean debug) {
		String lang = sesionHttp.getIdioma();
		if (lang == null) {
			lang = "es";
		}
		final String urlCallback = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL)
				+ ConstantesSeguridad.PUNTOENTRADA_RETORNO_AUTENTICACION_LOGOUT;
		return new ModelAndView("redirect:" + securityService.iniciarLogoutSesion(idEntidad, lang, urlCallback, debug));
	}

	/**
	 * Retorno del componente de autenticación tras logout.
	 *
	 * @param logout
	 *            indica si se ha realizado logout
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @return redirección cierre sesión
	 */
	@CrossOrigin
	@RequestMapping(ConstantesSeguridad.PUNTOENTRADA_RETORNO_AUTENTICACION_LOGOUT)
	public ModelAndView retornoAutenticacionLogout(final @RequestParam(required = true) boolean logout,
			final HttpServletRequest request, final HttpServletResponse response) {
		ModelAndView view = null;
		if (logout) {
			view = new ModelAndView("redirect:" + "/asistente/cerrarSesion.html");
		} else {
			// Cierre de sesión exception
			throw new ErrorFrontException("Ha ocurrido un error al realizar el cierre de sesión");
		}
		return view;
	}

	/**
	 * Sanitiza idioma. En caso de no estar soportado generará una LoginException.
	 *
	 * @param pIdiomaSavedRequest
	 *            Idioma login
	 */
	private String sanitizeIdioma(final String pIdiomaSavedRequest, final String idiomasSoportados) {
		String res = pIdiomaSavedRequest;
		final String langs[] = idiomasSoportados.split(",");
		boolean soportado = false;
		for (final String lang : langs) {
			if (lang.equals(pIdiomaSavedRequest)) {
				soportado = true;
				break;
			}
		}
		if (!soportado) {
			res = langs[0];
		}
		return res;
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
	private ModelAndView autenticarTicket(final SavedRequest pSavedRequest, final String pTicketUser,
			final String pTicketName) {
		// Obtenemos ticket de la peticion
		final String[] tickets = pSavedRequest.getParameterMap().get(pTicketName);
		if (tickets == null || tickets.length != ConstantesNumero.N1) {
			throw new ErrorFrontException("No existe ticket");
		}
		final String ticket = tickets[0];

		// Autenticamos automaticamente
		final LoginTicketInfo li = new LoginTicketInfo();
		li.setTicketName(pTicketUser);
		li.setTicketValue(ticket);
		li.setIdioma(sesionHttp.getIdioma());
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
		final String paramCodigoTramite = getParamValue(savedRequest, "tramite");
		final String paramVersionTramite = getParamValue(savedRequest, "version");
		final String paramLogin = getParamValue(savedRequest, LOGIN);
		final String paramIdTramiteCP = getParamValue(savedRequest, "idTramiteCatalogo");

		// Obtenemos info login tramite
		final InfoLoginTramite infoLoginTramite = securityService.obtenerInfoLoginTramite(paramCodigoTramite,
				Integer.parseInt(paramVersionTramite), paramIdTramiteCP, sesionHttp.getIdioma(),
				savedRequest.getRedirectUrl());

		// Comprobamos si hay que filtrar el metodo de autenticacion
		TypeAutenticacion filtroAutenticacion = null;
		if (paramLogin != null) {
			filtroAutenticacion = TypeAutenticacion.fromString(paramLogin);
			// Si no esta permitido el filtro, no lo aplicacmos
			if (!infoLoginTramite.getNiveles().contains(filtroAutenticacion)) {
				filtroAutenticacion = null;
			}
		}
		if (filtroAutenticacion != null) {
			infoLoginTramite.getNiveles().clear();
			infoLoginTramite.getNiveles().add(filtroAutenticacion);
		}

		// Devolvemos formulario de login
		return new ModelAndView(LOGIN, LOGIN, infoLoginTramite);
	}

	/**
	 * Autentica via formulario de login persistente anónimo.
	 *
	 * @param savedRequest
	 *            Request
	 *
	 * @return Vista para autenticar con formulario de login
	 */
	private ModelAndView autenticarFormLoginPersistenteAnonimo(final SavedRequest savedRequest) {

		// Obtenemos parametros inicio tramite
		final String idSesionTramitacion = getParamValue(savedRequest, ConstantesSeguridad.ANONIMO_PARAM_IDSESION);

		// Obtenemos info login tramite
		final InfoLoginTramite infoLoginTramite = securityService
				.obtenerInfoLoginTramiteAnonimoPersistente(idSesionTramitacion);

		// Devolvemos formulario de login
		return new ModelAndView(LOGIN, LOGIN, infoLoginTramite);
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
	protected String getParamValue(final SavedRequest savedRequest, final String paramName) {
		final String[] paramValues = savedRequest.getParameterMap().get(paramName);
		String param = null;
		if (paramValues != null && paramValues.length > 0) {
			param = paramValues[0];
		}
		return param;
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
	private boolean existeTicket(final SavedRequest pSavedRequest, final String pTicketName) {
		boolean res = true;
		final String[] tickets = pSavedRequest.getParameterMap().get(pTicketName);
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
	@ExceptionHandler({ Exception.class })
	public ModelAndView handleServiceException(final Exception ex, final HttpServletRequest request) {

		// TODO V0 Auditar ErrorFrontException en login
		LOGGER.error("Excepcion login", ex);

		// Obtenemos idioma
		String idioma;
		idioma = sesionHttp.getIdioma();
		if (StringUtils.isEmpty(idioma)) {
			if (request.getParameter(IDIOMA) != null) {
				idioma = request.getParameter(IDIOMA);
			} else {
				idioma = "ca";
			}
		}

		// Generamos respuesta JSON
		final RespuestaJSON res = errores.generarRespuestaJsonExcepcion(ex, idioma);

		// Redirigimos a pagina de error
		final ErrorGeneral error = new ErrorGeneral();
		error.setIdioma(idioma);
		error.setMensaje(res.getMensaje());
		error.setUrl(res.getUrl());
		return new ModelAndView("error", "error", error);

	}

}
