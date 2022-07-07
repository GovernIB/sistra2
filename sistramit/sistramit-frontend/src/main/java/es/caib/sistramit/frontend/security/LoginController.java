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
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeQAA;
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

	/** View Login Ticket. */
	private static final String VIEW_LOGIN = "login";

	/** View Login Ticket. */
	private static final String VIEW_LOGINTICKET = "loginTicket";

	/** View Login: info login. */
	private static final String VIEW_LOGINMODEL = "login";

	/** Parámetro trámite catálogo. */
	private static final String PARAM_TRAMITECP = "idTramiteCatalogo";

	/** Parámetro servicio catálogo. */
	private static final String PARAM_SERVICIOCP = "servicioCatalogo";

	/** Parámetro trámite. */
	private static final String PARAM_VERSION = "version";

	/** Parámetro trámite. */
	private static final String PARAM_TRAMITE = "tramite";

	/** Parámetro idioma. */
	private static final String PARAM_IDIOMA = "idioma";

	/** Parámetro para forzar tipo autenticación. */
	private static final String PARAM_LOGIN = "login";

	/** Log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

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
	 *                     Request
	 * @param response
	 *                     Response
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
		// SI SE ACTIVA CSRF LA CACHE POR DEFECTO NO CACHEA LAS PETICIONES QUE NO TENGAN
		// LA VALIDACION
		// final SavedRequest savedRequest = new
		// HttpSessionRequestCache().getRequest(request, response);
		final SavedRequest savedRequest = loginRequestCache.getRequest(request, response);

		if (savedRequest == null) {
			throw new ErrorFrontException("Punt d'entrada a la aplicació no vàlid");
		}
		final String url = savedRequest.getRedirectUrl();

		// Obtiene punto entrada
		final String puntoEntrada = getPuntoEntrada(url);

		// Establecemos idioma que viene en la saved request y si no viene ninguno el
		// idioma por defecto
		final String idiomaSavedRequest = getParamValue(savedRequest, PARAM_IDIOMA);
		final String idiomasSoportados = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.IDIOMAS_SOPORTADOS);
		final String idioma = sanitizeIdioma(idiomaSavedRequest, idiomasSoportados);
		RequestContextUtils.getLocaleResolver(request).setLocale(request, response, new Locale(idioma));
		sesionHttp.setIdioma(idioma);

		// En función del punto de entrada realizamos login
		ModelAndView login = null;
		if (ConstantesSeguridad.PUNTOENTRADA_INICIAR_TRAMITE.equals(puntoEntrada)) {
			// Inicio tramite: mostrar pagina login
			login = autenticarFormLogin(savedRequest, true);
		} else if (ConstantesSeguridad.PUNTOENTRADA_CARGAR_TRAMITE.equals(puntoEntrada)) {
			// Si existe ticket carpeta autenticamos via ticket
			if (existeParametro(savedRequest, ConstantesSeguridad.PARAM_TICKETAUTH)) {
				login = autenticarTicket(savedRequest, ConstantesSeguridad.TICKET_USER_CARPETA,
						ConstantesSeguridad.PARAM_TICKETAUTH);
			} else {
				// Si no existe ticket carpeta, autenticamos form login (anonimo o mediante
				// clave)
				login = autenticarFormLogin(savedRequest, false);
			}
		} else if (ConstantesSeguridad.PUNTOENTRADA_RETORNO_AUTENTICACION_LOGIN.equals(puntoEntrada)) {
			login = autenticarTicket(savedRequest, ConstantesSeguridad.TICKET_USER_CLAVE,
					ConstantesSeguridad.PARAM_TICKETAUTH);
		} else if (ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_FORMULARIO_EXTERNO.equals(puntoEntrada)) {
			login = autenticarTicket(savedRequest, ConstantesSeguridad.TICKET_USER_GF,
					ConstantesSeguridad.PARAM_TICKETAUTH);
		} else if (ConstantesSeguridad.PUNTOENTRADA_RETORNO_GESTOR_PAGO_EXTERNO.equals(puntoEntrada)) {
			login = autenticarTicket(savedRequest, ConstantesSeguridad.TICKET_USER_PAGO,
					ConstantesSeguridad.PARAM_TICKETAUTH);
		} else if (ConstantesSeguridad.PUNTOENTRADA_RETORNO_CARPETA.equals(puntoEntrada)) {
			login = autenticarTicket(savedRequest, ConstantesSeguridad.TICKET_USER_CARPETA,
					ConstantesSeguridad.PARAM_TICKETAUTH);
		} else {
			throw new ErrorFrontException("Punt de entrada a la aplicació no vàlido: " + url);
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
	 * @param nivelesAutenticacion
	 *                                 metodos autenticacion (separados por ;)
	 * @return redirige a componente de autenticacion.
	 */
	@RequestMapping("/redirigirAutenticacionLogin.html")
	public ModelAndView redirigirAutenticacionLogin(@RequestParam("entidad") final String idEntidad,
			@RequestParam("nivelesAutenticacion") final String nivelesAutenticacion,
			@RequestParam("metodosAutenticado") final String metodosAutenticado,
			@RequestParam(name = "qaa", required = false) final String qaa,
			@RequestParam(name = "debug", required = false) final boolean debug) {
		String lang = sesionHttp.getIdioma();
		if (lang == null) {
			lang = "es";
		}

		final List<TypeAutenticacion> authList = new ArrayList<>();
		final String[] auths = nivelesAutenticacion.split(";");
		for (final String a : auths) {
			authList.add(TypeAutenticacion.fromString(a));
		}

		final List<TypeMetodoAutenticacion> metAuthList = new ArrayList<>();
		if (authList.contains(TypeAutenticacion.ANONIMO)) {
			metAuthList.add(TypeMetodoAutenticacion.ANONIMO);
		}
		if (authList.contains(TypeAutenticacion.AUTENTICADO)) {
			final String[] mAuths = metodosAutenticado.split(";");
			for (final String ma : mAuths) {
				metAuthList.add(TypeMetodoAutenticacion.fromString(ma));
			}
		}

		final String urlCallback = systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL)
				+ ConstantesSeguridad.PUNTOENTRADA_RETORNO_AUTENTICACION_LOGIN + "?idioma=" + lang;
		final String urlCallbackError = sesionHttp.getUrlInicio();
		TypeQAA tipoQAA = null;
		if (StringUtils.isNotBlank(qaa)) {
			tipoQAA = TypeQAA.fromString(qaa);
		}

		return new ModelAndView("redirect:" + securityService.iniciarSesionAutenticacion(idEntidad, lang, metAuthList,
				tipoQAA, urlCallback, urlCallbackError, debug));
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
			throw new LoginException("Error al redirigir rere accés component autenticació.");
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
	public ModelAndView redirigirAutenticacionLogout(@RequestParam("entidad") final String idEntidad,
			@RequestParam(name = "debug", required = false) final boolean debug) {
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
	 *                     indica si se ha realizado logout
	 * @param request
	 *                     request
	 * @param response
	 *                     response
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
			throw new ErrorFrontException("Ha ocorregut un error al realitzar el tancament de sessió");
		}
		return view;
	}

	/**
	 * Si no se puede cargar trámite por restricción QAA se procede de nuevo con la
	 * recarga para forzar autenticación.
	 *
	 * @param idSesionTramitacion
	 *                                Id sesión tramitación
	 * @return Redirección a cargar trámite
	 */
	@RequestMapping("/reautenticar.html")
	public ModelAndView reautenticar(@RequestParam("idSesionTramitacion") final String idSesionTramitacion) {
		return new ModelAndView("redirect:" + ConstantesSeguridad.PUNTOENTRADA_CARGAR_TRAMITE + "?idSesionTramitacion="
				+ idSesionTramitacion + "&login=" + TypeAutenticacion.AUTENTICADO);
	}

	/**
	 * Sanitiza idioma. En caso de no estar soportado generará una LoginException.
	 *
	 * @param pIdiomaSavedRequest
	 *                                Idioma login
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
	 *                          Request
	 * @param pTicketName
	 *                          Nombre ticket
	 * @param pTicketUser
	 *                          Usuario asociado al tipo de ticket
	 * @return Vista que realiza el login automáticamente
	 */
	private ModelAndView autenticarTicket(final SavedRequest pSavedRequest, final String pTicketUser,
			final String pTicketName) {
		// Obtenemos ticket de la peticion
		final String[] tickets = pSavedRequest.getParameterMap().get(pTicketName);
		if (tickets == null || tickets.length != ConstantesNumero.N1) {
			throw new ErrorFrontException("No existeix ticket");
		}
		final String ticket = tickets[0];

		// Autenticamos automaticamente
		final LoginTicketInfo li = new LoginTicketInfo();
		li.setTicketName(pTicketUser);
		li.setTicketValue(ticket);
		li.setIdioma(sesionHttp.getIdioma());
		return new ModelAndView(VIEW_LOGINTICKET, VIEW_LOGINMODEL, li);
	}

	/**
	 * Autentica via formulario de login.
	 *
	 * @param savedRequest
	 *                          Request
	 * @param inicioTramite
	 *                          Indica si es inicio trámite.
	 *
	 * @return Vista para autenticar con formulario de login
	 */
	private ModelAndView autenticarFormLogin(final SavedRequest savedRequest, final boolean inicioTramite) {

		// Obtiene info login tramite
		InfoLoginTramite infoLoginTramite = null;
		if (inicioTramite) {
			// - Inicio trámite: a través parámetros inicio
			final String paramCodigoTramite = getParamValue(savedRequest, PARAM_TRAMITE);
			final String paramVersionTramite = getParamValue(savedRequest, PARAM_VERSION);
			final String paramIdTramiteCP = getParamValue(savedRequest, PARAM_TRAMITECP);
			final String servicioCP = getParamValue(savedRequest, PARAM_SERVICIOCP, "false");
			infoLoginTramite = securityService.obtenerInfoLoginTramite(paramCodigoTramite,
					Integer.parseInt(paramVersionTramite), paramIdTramiteCP,
					Boolean.parseBoolean(servicioCP.toLowerCase()), sesionHttp.getIdioma(),
					savedRequest.getRedirectUrl());
		} else {
			// Carga trámite: a través info persistencia
			final String paramIdSesionTramitacion = getParamValue(savedRequest, ConstantesSeguridad.PARAM_IDSESION);
			infoLoginTramite = securityService.obtenerInfoLoginTramite(paramIdSesionTramitacion);
		}

		// Guardamos url original para poder redirigir clave
		sesionHttp.setUrlInicio(savedRequest.getRedirectUrl());
		sesionHttp.setIdTramite(infoLoginTramite.getIdTramite());
		sesionHttp.setVersion(infoLoginTramite.getVersion());

		// Filtro metodo de autenticacion:
		final String paramLogin = getParamValue(savedRequest, PARAM_LOGIN);
		TypeAutenticacion filtroAutenticacion = TypeAutenticacion.fromString(paramLogin);
		// Si no esta permitido el filtro, no lo aplicacmos
		if (filtroAutenticacion != null && !infoLoginTramite.getNiveles().contains(filtroAutenticacion)) {
			filtroAutenticacion = null;
		}
		// Si se carga trámite y no se especifica filtro, marcamos para login anónimo
		// automático
		if (!inicioTramite && filtroAutenticacion != TypeAutenticacion.AUTENTICADO
				&& infoLoginTramite.getNiveles().contains(TypeAutenticacion.ANONIMO)) {
			infoLoginTramite.setLoginAnonimoAuto(true);
		}
		// Aplicamos filtro autenticación
		if (filtroAutenticacion != null) {
			infoLoginTramite.getNiveles().clear();
			infoLoginTramite.getNiveles().add(filtroAutenticacion);
		}

		// Devolvemos formulario de login
		return new ModelAndView(VIEW_LOGIN, VIEW_LOGINMODEL, infoLoginTramite);
	}

	/**
	 * Obtiene valor parametro de la request.
	 *
	 * @param savedRequest
	 *                         Saved request
	 * @param paramName
	 *                         Nombre parametro
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
	 * Obtiene valor parametro de la request.
	 *
	 * @param savedRequest
	 *                         Saved request
	 * @param paramName
	 *                         Nombre parametro
	 * @param defaultValue
	 *                         Valor por defecto si no existe
	 * @return Valor parametro
	 */
	protected String getParamValue(final SavedRequest savedRequest, final String paramName, final String defaultValue) {
		final String param = getParamValue(savedRequest, paramName);
		return StringUtils.defaultString(param, defaultValue);
	}

	/**
	 * Obtiene punto de entrada.
	 *
	 * @param urlOrg
	 *                   Url origen
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
	 * Comprueba si existe parametro en request.
	 *
	 * @param pSavedRequest
	 *                          Request
	 * @param pParamName
	 *                          Ticket
	 * @return boolean
	 */
	private boolean existeParametro(final SavedRequest pSavedRequest, final String pParamName) {
		boolean res = true;
		final String[] tickets = pSavedRequest.getParameterMap().get(pParamName);
		if (tickets == null || tickets.length != ConstantesNumero.N1) {
			res = false;
		}
		return res;
	}

	/**
	 * Handler de excepciones de negocio.
	 *
	 * @param ex
	 *                    Excepción de la capa de servicio
	 * @param request
	 *                    Request
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
			if (request.getParameter(PARAM_IDIOMA) != null) {
				idioma = request.getParameter(PARAM_IDIOMA);
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
