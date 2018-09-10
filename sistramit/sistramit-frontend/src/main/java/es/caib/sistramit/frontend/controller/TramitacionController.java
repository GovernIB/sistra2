package es.caib.sistramit.frontend.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.core.api.exception.AccesoNoPermitidoException;
import es.caib.sistramit.core.api.exception.ErrorFrontException;
import es.caib.sistramit.core.api.exception.LimiteTramitacionException;
import es.caib.sistramit.core.api.exception.LoginException;
import es.caib.sistramit.core.api.exception.ServiceException;
import es.caib.sistramit.core.api.exception.TramiteFinalizadoException;
import es.caib.sistramit.core.api.exception.TramiteNoExisteException;
import es.caib.sistramit.core.api.exception.UsuarioNoPermitidoException;
import es.caib.sistramit.core.api.exception.WarningFrontException;
import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;
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

    /**
     * Flujo tramitacion service.
     */
    @Autowired
    FlujoTramitacionService flujoTramitacionService;

    /** Atributo literales. */
    @Autowired
    private LiteralesFront literalesFront;

    /** Log. */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Informacion de sesion de tramitacion.
     */
    @Autowired
    private SesionHttp sesionHttp;

    /**
     * Generacion errores.
     */
    @Resource(name = "errores")
    private Errores errores;

    /** Configuracion. */
    @Autowired
    private SystemService systemService;

    /**
     * Service excepcion para las que no se intentara recargar el tramite.
     */
    private static final String[] SERVICE_EXCEPTION_NO_RECARGAR = {
            AccesoNoPermitidoException.class.getName(),
            LoginException.class.getName(),
            TramiteFinalizadoException.class.getName(),
            TramiteNoExisteException.class.getName(),
            UsuarioNoPermitidoException.class.getName(),
            LimiteTramitacionException.class.getName()};

    /**
     * Guarda en la sesión http la referencia al flujo de tramitación.
     *
     * @param pidSesionTramitacion
     *            Id sesión
     */
    protected final void registraSesionTramitacion(
            final String pidSesionTramitacion, final boolean debug) {
        sesionHttp.setIdSesionTramitacion(pidSesionTramitacion);
        sesionHttp.setDebugSesionTramitacion(debug);
    }

    /**
     * Obtiene el flujo de tramitación generando una excepcion si no existe.
     *
     * @return sesionTramitacion
     */
    protected final String getIdSesionTramitacionActiva() {
        final String ft = getIdSesionTramitacion();
        if (ft == null) {
            throw new WarningFrontException(
                    "No existe instancia sesion de tramitacion en sesion");
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
     * Genera JsonView.
     *
     * @param res
     *            RepuestaJSON
     * @return JsonView
     */
    protected final ModelAndView generarJsonView(final RespuestaJSON res) {
        final ModelAndView mav = new ModelAndView(
                JsonView.getInstanceJsonview());
        mav.addObject(JsonView.JSON_OBJECT, res);
        return mav;
    }

    /**
     * Genera view para descarga de fichero.
     *
     * @param nomFichero
     *            Nombre fichero con extensión
     * @param contenido
     *            Contenido del fichero
     * @return DownloadView
     */
    protected final ModelAndView generarDownloadView(final String nomFichero,
            final byte[] contenido) {
        final ModelAndView mav = new ModelAndView(
                DownloadFileView.getInstanceDownloadview());
        mav.addObject(DownloadFileView.PARAMETER_FILENAME, nomFichero);
        mav.addObject(DownloadFileView.PARAMETER_FILEDATA, contenido);
        return mav;
    }

    /**
     * Genera view para upload de fichero. Se invocará desde el iframe de upload
     * a la función fileUploaded del html parent pasando la respuesta json.
     *
     * @param res
     *            RepuestaJSON
     * @return DownloadView
     */
    protected final ModelAndView generarUploadView(final RespuestaJSON res) {
        final ModelAndView mav = new ModelAndView(
                UploadFileView.getInstanceUploadview());
        mav.addObject(UploadFileView.JSON_OBJECT, res);
        return mav;
    }

    /**
     * Genera view para generar un html de forma dinámica.
     *
     * @param html
     *            Html
     * @return HtmlView
     */
    protected final ModelAndView generarHtmlView(final byte[] html) {
        final ModelAndView mav = new ModelAndView(
                HtmlView.getInstanceHtmlview());
        mav.addObject(HtmlView.CONTENT, html);
        mav.addObject(HtmlView.CONTENT_TYPE, HtmlView.CONTENT_TYPE_HTML);
        return mav;
    }

    /**
     * Genera view para generar un css de forma dinámica.
     *
     * @param css
     *            Css
     * @return HtmlView
     */
    protected final ModelAndView generarCssView(final byte[] css) {
        final ModelAndView mav = new ModelAndView(
                HtmlView.getInstanceHtmlview());
        mav.addObject(HtmlView.CONTENT, css);
        mav.addObject(HtmlView.CONTENT_TYPE, HtmlView.CONTENT_TYPE_CSS);
        return mav;
    }

    /**
     * Genera view para generar un imagen de forma dinámica.
     *
     * @param imagen
     *            imagen
     * @return HtmlView
     */
    protected final ModelAndView generarImageView(final byte[] imagen) {
        final ModelAndView mav = new ModelAndView(
                HtmlView.getInstanceHtmlview());
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
     *            Mensaje a mostrar.
     * @param exception
     *            Excepcion.
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
     *            Excepción
     * @param request
     *            Request
     * @return Respuesta JSON indicando el mensaje producido
     */
    @ExceptionHandler({Exception.class})
    public final ModelAndView handleServiceException(final Exception pex,
            final HttpServletRequest request) {

        // Si no es una excepcion de negocio ni una excepcion generada
        // explicitamente desde front lo tomamos como una excepcion no
        // controlada de front
        Exception ex = pex;
        if (!(pex instanceof ServiceException)
                && !(pex instanceof ErrorFrontException)) {
            ex = new ErrorFrontException(
                    "Excepcion no controlada en front: " + pex.getMessage(),
                    pex);
        }

        // Generamos respuesta JSON
        final RespuestaJSON res = errores.generarRespuestaJsonExcepcion(ex,
                getIdioma());

        // Si es una excepcion de tipo service se intentara recargar el
        // tramite, excepto ciertas excepciones
        if (isExceptionServiceRecargar(ex)) {
            res.setUrl(getUrlAsistente() + "/asistente/recargarTramite.html");
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
            final String idSesionTramitacion = getIdSesionTramitacion();
            systemService.auditarErrorFront(idSesionTramitacion,
                    (ErrorFrontException) ex);
        }

        return view;
    }

    /**
     * Obtiene url asistente.
     *
     * @return url asistente
     */
    protected String getUrlAsistente() {
        // TODO Ver si cachear
        return systemService.obtenerPropiedadConfiguracion(
                TypePropiedadConfiguracion.SISTRAMIT_URL);
    }

    /**
     * Obtiene entorno.
     *
     * @return url asistente
     */
    protected TypeEntorno getEntorno() {
        // TODO Ver si cachear
        return TypeEntorno
                .fromString(systemService.obtenerPropiedadConfiguracion(
                        TypePropiedadConfiguracion.ENTORNO));
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
     *            mensaje
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
     * Comprueba si es una excepcion de servicio para la que se intentara
     * recargar el tramite.
     *
     * @param pEx
     *            Exception
     * @return true si se debe intentar recargar el tramite.
     */
    private boolean isExceptionServiceRecargar(final Exception pEx) {
        boolean res = false;
        if (pEx instanceof ServiceException && ((ServiceException) pEx)
                .getNivel() == TypeNivelExcepcion.FATAL) {
            res = true;
            for (final String exceptionName : SERVICE_EXCEPTION_NO_RECARGAR) {
                if (StringUtils.equals(exceptionName,
                        pEx.getClass().getName())) {
                    res = false;
                    break;
                }
            }
        }
        return res;
    }

    /**
     * Genera mensaje asistente.
     *
     * @param literalTitulo
     *            Literal titulo
     * @param literalMensaje
     *            Literal mensaje
     * @param mensajeIncorrecto
     *            Detalle mensaje error
     * @param tipoRespuesta
     *            Tipo respuesta
     * @return
     */
    protected final MensajeAsistente generarMensajeErrorAsistente(
            final String literalTitulo, final String literalMensaje,
            final String mensajeIncorrecto,
            final TypeRespuestaJSON tipoRespuesta) {
        final String textoDetalleError = getLiteralesFront().getLiteralFront(
                LiteralesFront.MENSAJES, "detalleError", getIdioma());
        final String tituloMsg = getLiteralesFront().getLiteralFront(
                LiteralesFront.MENSAJES, literalTitulo, getIdioma());

        final String textoMsg = getLiteralesFront().getLiteralFront(
                LiteralesFront.MENSAJES, literalMensaje, getIdioma());
        String detalleError = "";
        if (StringUtils.isNotBlank(mensajeIncorrecto)) {
            detalleError += " (" + textoDetalleError + mensajeIncorrecto + ")";
        }
        final MensajeUsuario mu = new MensajeUsuario(tituloMsg,
                textoMsg + detalleError);
        final MensajeAsistente ma = new MensajeAsistente();
        ma.setMensaje(mu);
        ma.setTipo(tipoRespuesta);
        return ma;
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

}
