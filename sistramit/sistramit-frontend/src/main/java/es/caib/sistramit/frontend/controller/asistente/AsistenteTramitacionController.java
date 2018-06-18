package es.caib.sistramit.frontend.controller.asistente;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.ResultadoIrAPaso;
import es.caib.sistramit.frontend.SesionHttp;
import es.caib.sistramit.frontend.controller.TramitacionController;
import es.caib.sistramit.frontend.literales.LiteralesFront;
import es.caib.sistramit.frontend.model.AsistenteInfo;
import es.caib.sistramit.frontend.model.MensajeAsistente;
import es.caib.sistramit.frontend.model.MensajeUsuario;
import es.caib.sistramit.frontend.model.RespuestaJSON;
import es.caib.sistramit.frontend.security.SecurityUtils;
import es.caib.sistramit.frontend.security.UsuarioAutenticado;

@Controller
@RequestMapping(value = "/asistente")
public class AsistenteTramitacionController extends TramitacionController {

    @Autowired
    SesionHttp sesionHttp;

    /** Url redireccion asistente. */
    private static final String URL_REDIRIGIR_ASISTENTE = "asistente/redirigirAsistente";

    /**
     * Inicia trámite.
     *
     * @param tramite
     *            Trámite
     * @param version
     *            Versión
     * @param idioma
     *            Idioma
     * @param idTramiteCatalogo
     *            Id trámite en catálogo de servicios
     * @param parametros
     *            Parameros de inicio
     * @param request
     *            request
     * @return Redireccion a mostrar asistente
     */
    @RequestMapping(value = "/iniciarTramite.html")
    public ModelAndView iniciarTramite(
            @RequestParam("tramite") final String tramite,
            @RequestParam("version") final int version,
            @RequestParam("idioma") final String idioma,
            @RequestParam("idTramiteCatalogo") final String idTramiteCatalogo,
            @RequestParam(value = "parametros", required = false) final String parametros,
            final HttpServletRequest request) {

        // Url inicio
        final String urlInicio = getUrlAsistente()
                + "/asistente/iniciarTramite.html?" + request.getQueryString();

        // Parametros inicio
        // TODO Pendiente (como json?)
        final Map<String, String> parametrosInicio = new HashMap<>();

        // Obtiene usuario autenticado
        final UsuarioAutenticado usuarioAutenticado = SecurityUtils
                .obtenerUsuarioAutenticado();

        // Inicia flujo tramitacion y almacena en la sesion
        final String idSesionTramitacion = getFlujoTramitacionService()
                .iniciarTramite(tramite, version, idioma, idTramiteCatalogo,
                        urlInicio, parametrosInicio,
                        usuarioAutenticado.getUsuario());
        sesionHttp.setIdSesionTramitacion(idSesionTramitacion);

        final ModelAndView mav = new ModelAndView(URL_REDIRIGIR_ASISTENTE);
        return mav;
    }

    /**
     * Muestra asistente de tramitación.
     *
     * @return Vista asistente
     */
    @RequestMapping(value = "/asistente.html")
    public ModelAndView asistente() {
        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        // Obtiene detalle tramite
        final DetalleTramite detalleTramite = getFlujoTramitacionService()
                .obtenerDetalleTramite(idSesionTramitacion);

        // Devolvemos informacion asistente
        final AsistenteInfo ai = new AsistenteInfo();
        ai.setIdSesionTramitacion(idSesionTramitacion);
        ai.setIdioma(detalleTramite.getUsuario().getSesionInfo().getIdioma());
        // TODO Pendiente ver info a pasar
        return new ModelAndView("asistente/asistente", "datos", ai);
    }

    /**
     * Cancela un trámite.
     *
     * @return Devuelve JSON indicando que se ha cancelado.
     *
     */
    @RequestMapping(value = "/cancelarTramite.html", method = RequestMethod.POST)
    public ModelAndView cancelarTramite() {

        debug("Cancelar tramite");
        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        // Cancela trámite
        getFlujoTramitacionService().cancelarTramite(idSesionTramitacion);

        final RespuestaJSON res = new RespuestaJSON();
        final MensajeUsuario mu = new MensajeUsuario(
                getLiteralesFront().getLiteralFront(LiteralesFront.ASISTENTE,
                        "atencion", getIdioma()),
                getLiteralesFront().getLiteralFront(LiteralesFront.ASISTENTE,
                        "tramite.cancelado", getIdioma()));
        res.setMensaje(mu);

        // Obtener url carpeta entidad
        // TODO PENDIENTE
        final String urlCarpeta = "http://www.google.es";
        res.setUrl(urlCarpeta);

        return generarJsonView(res);
    }

    /**
     * Devuelve un JSON con la información inicial trámite.
     *
     * @return JSON con la información inicial trámite.
     *
     */
    // TODO RESTRINGIR POST
    // @RequestMapping(value = "/informacionTramite.html", method =
    // RequestMethod.POST)
    @RequestMapping(value = "/informacionTramite.html")
    public ModelAndView informacionTramite() {
        debug("Obteniendo info tramite");

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        // Obtiene detalle tramite
        final DetalleTramite detalleTramite = getFlujoTramitacionService()
                .obtenerDetalleTramite(idSesionTramitacion);

        final RespuestaJSON res = new RespuestaJSON();
        res.setDatos(detalleTramite);

        // Mensaje asistente en sesion (recuperamos si existe mensaje y lo
        // reseteamos)
        final MensajeAsistente mensajeAsistente = this.getMensajeAsistente();
        if (mensajeAsistente != null) {
            this.setMensajeAsistente(null);
            final MensajeUsuario mu = mensajeAsistente.getMensaje();
            res.setEstado(mensajeAsistente.getTipo());
            res.setMensaje(mu);
        }

        return generarJsonView(res);
    }

    /**
     * Devuelve JSON con el paso indicado.
     *
     * @param idPaso
     *            Identificador del formulario.
     * @return Devuelve JSON con estado actual del trámite.
     */
    // TODO RESTRINGIR POST
    // @RequestMapping(value = "/asistente/irAPaso.html", method =
    // RequestMethod.POST)
    @RequestMapping(value = "/irAPaso.html")
    public ModelAndView irAPaso(@RequestParam("id") final String idPaso) {
        debug("Ir a paso " + idPaso);

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        // Intenta ir a paso indicado
        final ResultadoIrAPaso resPaso = getFlujoTramitacionService()
                .irAPaso(idSesionTramitacion, idPaso);

        // Obtiene info paso actual
        debug("Paso actual: " + resPaso.getIdPasoActual());

        // Recupera info tramite
        final DetalleTramite dt = getFlujoTramitacionService()
                .obtenerDetalleTramite(idSesionTramitacion);
        final RespuestaJSON res = new RespuestaJSON();
        res.setDatos(dt);

        final MensajeUsuario mu = new MensajeUsuario("", "");
        res.setMensaje(mu);

        return generarJsonView(res);
    }

    // TODO TEST PARA PRUEBAS. BORRAR.
    @RequestMapping(value = "/test.html")
    public ModelAndView test(@RequestParam("param") final String param) {

        final String idSesionTramitacion = getIdSesionTramitacionActiva();
        getFlujoTramitacionService().test(idSesionTramitacion, param);

        final ModelAndView mav = new ModelAndView("asistente/asistente");
        return mav;
    }

}
