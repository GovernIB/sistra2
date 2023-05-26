package es.caib.sistramit.frontend.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.exception.ServiceException;
import es.caib.sistramit.core.api.exception.WarningFrontException;
import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.PagoVerificacion;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.frontend.Errores;
import es.caib.sistramit.frontend.SesionHttp;
import es.caib.sistramit.frontend.literales.LiteralesFront;
import es.caib.sistramit.frontend.model.ErrorGeneral;
import es.caib.sistramit.frontend.model.MensajeAsistente;
import es.caib.sistramit.frontend.model.MensajeUsuario;
import es.caib.sistramit.frontend.model.RespuestaJSON;
import es.caib.sistramit.frontend.model.types.TypeRespuestaJSON;
import es.caib.sistramit.frontend.security.SecurityUtils;
import es.caib.sistramit.frontend.view.DownloadFileView;
import es.caib.sistramit.frontend.view.HtmlView;
import es.caib.sistramit.frontend.view.JsonView;
import es.caib.sistramit.frontend.view.UploadFileView;

/**
 *
 * Controller de tramitación.
 *
 * @author Indra
 *
 */
public abstract class TramitacionController {

	/** Flujo tramitacion service. */
	@Autowired
	FlujoTramitacionService flujoTramitacionService;

	/** Configuracion. */
	@Autowired
	private SystemService systemService;

	/** Atributo literales. */
	@Autowired
	private LiteralesFront literalesFront;

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(getClass());

	/** Informacion de sesion de tramitacion. */
	@Autowired
	private SesionHttp sesionHttp;

	/**
	 * Generacion errores.
	 */
	@Resource(name = "errores")
	private Errores errores;

	/**
	 * Guarda en la sesión http la referencia al flujo de tramitación.
	 *
	 * @param pidSesionTramitacion
	 *                                 Id sesión
	 */
	protected final void registraSesionTramitacion(final DetalleTramite dt) {
		sesionHttp.setIdSesionTramitacion(dt.getTramite().getIdSesion());
		sesionHttp.setIdioma(dt.getTramite().getIdioma());
		sesionHttp.setIdTramite(dt.getTramite().getId());
		sesionHttp.setVersion(dt.getTramite().getVersion());
		sesionHttp.setDebugSesionTramitacion(dt.getDebug() == TypeSiNo.SI);
	}

	/**
	 * Al iniciar un formulario interno guarda en la sesión http la referencia a la
	 * sesión en el gestor de formularios interno.
	 *
	 * @param pidSesionFormulario
	 *                                Sesión en el gestor de formularios interno
	 */
	protected final void registraSesionFormulario(final String pidSesionFormulario) {
		sesionHttp.setIdSesionFormulario(pidSesionFormulario);
	}

	/**
	 * Obtiene el flujo de formulario generando una excepcion si no existe.
	 *
	 * @return sesionFormulario
	 */
	protected final String getIdSesionFormularioActiva() {
		final String ft = getIdSesionFormulario();
		if (ft == null) {
			throw new WarningFrontException("No existeix instància sessió de formulari en sessió");
		}
		return ft;
	}

	/**
	 * Método que obtiene el id de sesion de formulario.
	 *
	 * @return sesionFormulario
	 */
	protected final String getIdSesionFormulario() {
		String idSesion = null;
		if (sesionHttp != null) {
			idSesion = sesionHttp.getIdSesionFormulario();
		}
		return idSesion;
	}

	/**
	 * Obtiene el flujo de tramitación generando una excepcion si no existe.
	 *
	 * @return sesionTramitacion
	 */
	protected final String getIdSesionTramitacionActiva() {
		final String ft = getIdSesionTramitacion();
		if (ft == null) {
			throw new WarningFrontException("No existeix instància sesió de tramitació en sessió");
		}
		return ft;
	}

	/**
	 * Método que obtiene el id de sesion de tramitacion.
	 *
	 * @return sesionTramitacion
	 */
	protected final String getIdSesionTramitacion() {
		String idSesion = null;
		if (sesionHttp != null) {
			idSesion = sesionHttp.getIdSesionTramitacion();
		}
		return idSesion;
	}

	/**
	 * Obtiene idioma de tramitacón.
	 *
	 * @return Idioma
	 */
	public final String getIdioma() {
		String id = null;
		if (sesionHttp != null) {
			id = sesionHttp.getIdioma();
		}
		if (id == null) {
			id = "es";
		}
		return id;
	}

	/**
	 * Obtiene id trámite.
	 *
	 * @return id trámite
	 */
	public final String getIdTramite() {
		String idTramite = null;
		if (sesionHttp != null) {
			idTramite = sesionHttp.getIdTramite();
		}
		return idTramite;
	}

	/**
	 * Obtiene version trámite.
	 *
	 * @return version trámite
	 */
	public final Integer getVersionTramite() {
		Integer res = null;
		if (sesionHttp != null) {
			res = sesionHttp.getVersion();
		}
		return res;
	}

	/**
	 * Genera JsonView.
	 *
	 * @param res
	 *                RepuestaJSON
	 * @return JsonView
	 */
	protected final ModelAndView generarJsonView(final RespuestaJSON res) {
		final ModelAndView mav = new ModelAndView(JsonView.getInstanceJsonview());
		mav.addObject(JsonView.JSON_OBJECT, res);
		return mav;
	}

	/**
	 * Genera view para descarga de fichero.
	 *
	 * @param nomFichero
	 *                       Nombre fichero con extensión
	 * @param contenido
	 *                       Contenido del fichero
	 * @return DownloadView
	 */
	protected final ModelAndView generarDownloadView(final String nomFichero, final byte[] contenido) {
		final ModelAndView mav = new ModelAndView(DownloadFileView.getInstanceDownloadview());
		mav.addObject(DownloadFileView.PARAMETER_FILENAME, nomFichero);
		mav.addObject(DownloadFileView.PARAMETER_FILEDATA, contenido);
		return mav;
	}

	/**
	 * Genera view para upload de fichero. Se invocará desde el iframe de upload a
	 * la función fileUploaded del html parent pasando la respuesta json.
	 *
	 * @param res
	 *                RepuestaJSON
	 * @return DownloadView
	 */
	protected final ModelAndView generarUploadView(final RespuestaJSON res) {
		final ModelAndView mav = new ModelAndView(UploadFileView.getInstanceUploadview());
		mav.addObject(UploadFileView.JSON_OBJECT, res);
		return mav;
	}

	/**
	 * Genera view para generar un html de forma dinámica.
	 *
	 * @param html
	 *                 Html
	 * @return HtmlView
	 */
	protected final ModelAndView generarHtmlView(final byte[] html) {
		final ModelAndView mav = new ModelAndView(HtmlView.getInstanceHtmlview());
		mav.addObject(HtmlView.CONTENT, html);
		mav.addObject(HtmlView.CONTENT_TYPE, HtmlView.CONTENT_TYPE_HTML);
		return mav;
	}

	/**
	 * Genera view para generar un css de forma dinámica.
	 *
	 * @param css
	 *                Css
	 * @return HtmlView
	 */
	protected final ModelAndView generarCssView(final byte[] css) {
		final ModelAndView mav = new ModelAndView(HtmlView.getInstanceHtmlview());
		mav.addObject(HtmlView.CONTENT, css);
		mav.addObject(HtmlView.CONTENT_TYPE, HtmlView.CONTENT_TYPE_CSS);
		return mav;
	}

	/**
	 * Genera view para generar un imagen de forma dinámica.
	 *
	 * @param imagen
	 *                   imagen
	 * @return HtmlView
	 */
	protected final ModelAndView generarImageView(final byte[] imagen) {
		final ModelAndView mav = new ModelAndView(HtmlView.getInstanceHtmlview());
		mav.addObject(HtmlView.CONTENT, imagen);
		mav.addObject(HtmlView.CONTENT_TYPE, HtmlView.CONTENT_TYPE_IMAGE);
		return mav;
	}

	/**
	 * Método de acceso a log.
	 *
	 * @return log
	 */
	protected final Logger getLog() {
		return log;
	}

	/**
	 * Muestra mensaje de debug.
	 *
	 * @param message
	 *                      Mensaje a mostrar.
	 * @param exception
	 *                      Excepcion.
	 */
	protected final void debug(final String message) {
		String idSesion = null;
		boolean debug = false;
		idSesion = sesionHttp.getIdSesionTramitacion();
		debug = sesionHttp.isDebugSesionTramitacion();
		if (debug) {
			log.debug(idSesion + " - " + message);
		}
	}

	/**
	 * Handler de excepciones de negocio.
	 *
	 * @param pex
	 *                    Excepción
	 * @param request
	 *                    Request
	 * @return Respuesta JSON indicando el mensaje producido
	 */
	@ExceptionHandler({ Exception.class })
	public final ModelAndView handleServiceException(final Exception pex, final HttpServletRequest request) {
		return generarViewForException(pex, null, request);
	}

	/**
	 * Genera view para excepcion.
	 *
	 * @param pex
	 *                              Excepcion
	 * @param urlRedirectForced
	 *                              Fuerza que la url de redirección sea la indicada
	 * @param request
	 *                              Request
	 * @return view
	 */
	protected ModelAndView generarViewForException(final Exception pex, final String urlRedirectForced,
			final HttpServletRequest request) {
		// Si no es una excepcion de negocio ni una excepcion generada
		// explicitamente desde front lo tomamos como una excepcion no
		// controlada de front
		// Si viene de la capa EJB viene envuelta en una EJBException
		Exception ex = pex;

		if (pex instanceof EJBException && pex.getCause() instanceof ServiceException) {
			ex = (Exception) pex.getCause();
		} else if (!(pex instanceof ServiceException) && !(pex instanceof ErrorFrontException)) {
			ex = new ErrorFrontException("Excepcion no controlada en front: " + pex.getMessage(), pex);
		}

		// Generamos respuesta JSON
		final RespuestaJSON res = errores.generarRespuestaJsonExcepcion(ex, getIdioma());

		// Para FATAL establecemos info debug
		if (res.getEstado() == TypeRespuestaJSON.FATAL) {
			// Fecha
			String debug = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(new Date());
			// Host
			debug += " - " + SecurityUtils.getJbossNodeName();
			// Sesion tramitacion
			if (getIdSesionTramitacion() != null) {
				debug += " - " + getIdSesionTramitacion();
			}
			res.getMensaje().setDebug(debug);
		}

		// Comprobamos si se fuerza url redireccion
		if (StringUtils.isNotBlank(urlRedirectForced)) {
			res.setUrl(urlRedirectForced);
		}

		// Diferenciamos si es una llamada json
		ModelAndView view;
		if (request.getRequestURI().endsWith(".json")) {
			debug("JSON: " + request.getRequestURI());
			view = this.generarJsonView(res);
		} else {
			debug("HMTL: " + request.getRequestURI());
			final ErrorGeneral error = new ErrorGeneral();
			error.setIdioma(getIdioma());
			error.setMensaje(res.getMensaje());
			error.setUrl(res.getUrl());
			view = new ModelAndView("error", "error", error);
		}

		// Si es una FrontException auditamos error
		if (ex instanceof ErrorFrontException) {
			systemService.auditarErrorFront(getIdSesionTramitacion(), (ErrorFrontException) ex);
		}

		return view;
	}

	/**
	 * Obtiene url asistente.
	 *
	 * @return url asistente
	 */
	protected String getUrlAsistente() {
		return systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_URL);
	}

	/**
	 * Obtiene entorno.
	 *
	 * @return url asistente
	 */
	protected TypeEntorno getEntorno() {
		return TypeEntorno.fromString(systemService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.ENTORNO));
	}

	/**
	 * Método de acceso a literalesFront.
	 *
	 * @return el literalesFront
	 */
	public final LiteralesFront getLiteralesFront() {
		return literalesFront;
	}

	/**
	 * Establece mensaje a mostrar al cargar el asistente.
	 *
	 * @param mensaje
	 *                    mensaje
	 */
	protected final void setMensajeAsistente(final MensajeAsistente mensaje) {
		if (sesionHttp != null) {
			sesionHttp.setMensajeAsistente(mensaje);
		}
	}

	/**
	 * Obtiene mensaje a mostrar al cargar el asistente.
	 *
	 * @return mensaje
	 *
	 */
	protected final MensajeAsistente getMensajeAsistente() {
		MensajeAsistente res = null;
		if (sesionHttp != null) {
			res = sesionHttp.getMensajeAsistente();
		}
		return res;

	}

	/**
	 * Genera mensaje asistente.
	 *
	 * @param literalTitulo
	 *                              Literal titulo
	 * @param literalMensaje
	 *                              Literal mensaje
	 * @param mensajeIncorrecto
	 *                              Detalle mensaje error
	 * @param tipoRespuesta
	 *                              Tipo respuesta
	 * @param recargarTramite
	 *                              Si se recarga el trámite
	 * @return
	 */
	protected final MensajeAsistente generarMensajeErrorAsistente(final String literalTitulo,
			final String literalMensaje, final String mensajeIncorrecto, final TypeRespuestaJSON tipoRespuesta) {
		final String textoDetalleError = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, "detalleError",
				getIdioma());
		final String tituloMsg = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, literalTitulo,
				getIdioma());

		final String textoMsg = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, literalMensaje,
				getIdioma());
		String detalleError = "";
		if (StringUtils.isNotBlank(mensajeIncorrecto)) {
			detalleError += " (" + textoDetalleError + mensajeIncorrecto + ")";
		}
		final MensajeUsuario mu = new MensajeUsuario(tituloMsg, textoMsg + detalleError);
		final MensajeAsistente ma = new MensajeAsistente();
		ma.setMensaje(mu);
		ma.setTipo(tipoRespuesta);
		return ma;
	}

	/**
	 * Genera mensaje asistente.
	 *
	 * @param literalTitulo
	 *                              Literal titulo
	 * @param literalMensaje
	 *                              Literal mensaje
	 * @param mensajeIncorrecto
	 *                              Detalle mensaje error
	 * @param tipoRespuesta
	 *                              Tipo respuesta
	 * @return
	 */
	protected final MensajeUsuario generarMensajeUsuario(final String literalTitulo, final String literalMensaje) {
		final String tituloMsg = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, literalTitulo,
				getIdioma());
		final String textoMsg = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, literalMensaje,
				getIdioma());
		final MensajeUsuario mu = new MensajeUsuario(tituloMsg, textoMsg);
		return mu;
	}

	/**
	 * Obtiene tramitacion service.
	 *
	 * @return tramitacion service.
	 */
	protected FlujoTramitacionService getFlujoTramitacionService() {
		return flujoTramitacionService;
	}

	/**
	 * Obtiene system service.
	 *
	 * @return system service.
	 */
	protected SystemService getSystemService() {
		return systemService;
	}

	/**
	 * Genera mensaje para pagos.
	 *
	 * @param verificacion
	 *                         verificacion pago
	 * @return mensaje asistente
	 */
	protected MensajeAsistente generarMensajeValidacionPago(final PagoVerificacion verificacion) {

		TypeRespuestaJSON tipoMensaje;
		String literalTextoMensaje;

		if (verificacion.getVerificado() == TypeSiNo.NO) {
			// Mensaje no se ha podido verificar
			tipoMensaje = TypeRespuestaJSON.WARNING;
			literalTextoMensaje = "pagoNoVerificado";
		} else {
			if (verificacion.getRealizado() == TypeSiNo.NO) {
				// Mensaje no se ha podido realizar pago
				if (verificacion.getEstadoIncorrecto() != null
						&& verificacion.getEstadoIncorrecto().getCodigoErrorPasarela() != null) {
					// Error verificar pago
					tipoMensaje = TypeRespuestaJSON.WARNING;
					literalTextoMensaje = "pagoError";
				} else {
					// Pago no realizado, sin indicar error -> no pagado
					tipoMensaje = TypeRespuestaJSON.WARNING;
					literalTextoMensaje = "pagoNoRealizado";
				}
			} else {
				// Mensaje de que se ha podido realizar pago
				tipoMensaje = TypeRespuestaJSON.SUCCESS;
				literalTextoMensaje = "pagoRealizado";
			}
		}

		final String tituloMensaje = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, "atencion",
				getIdioma());
		final String textoMensaje = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, literalTextoMensaje,
				getIdioma());
		final MensajeUsuario mensajeUsuario = new MensajeUsuario(tituloMensaje, textoMensaje);
		final MensajeAsistente ma = new MensajeAsistente();
		ma.setTipo(tipoMensaje);
		ma.setMensaje(mensajeUsuario);
		return ma;
	}

	/**
	 * Método de acceso a errores.
	 *
	 * @return errores
	 */
	public Errores getErrores() {
		return errores;
	}

}
