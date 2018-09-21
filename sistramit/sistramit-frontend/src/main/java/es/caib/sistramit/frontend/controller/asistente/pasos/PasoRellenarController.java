package es.caib.sistramit.frontend.controller.asistente.pasos;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.AbrirFormulario;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoRellenar;
import es.caib.sistramit.frontend.controller.TramitacionController;
import es.caib.sistramit.frontend.literales.LiteralesFront;
import es.caib.sistramit.frontend.model.MensajeUsuario;
import es.caib.sistramit.frontend.model.MockRellenarFormData;
import es.caib.sistramit.frontend.model.RespuestaJSON;
import es.caib.sistramit.frontend.model.types.TypeRespuestaJSON;

/**
 * Interacción con paso Rellenar.
 *
 * @author Indra
 *
 */
@Controller
@RequestMapping(value = "/asistente/rf")
public final class PasoRellenarController extends TramitacionController {

    /** Constante parametro Id Paso. */
    private static final String PARAM_ID_PASO = "idPaso";

    /** Constante parametro id formulario. */
    private static final String PARAM_ID_FORMULARIO = "idFormulario";

    /** Constante parametro ticket */
    private static final String PARAM_TICKET = "ticket";

    /** Constante parametro referencia. */
    private static final String PARAM_REFERENCIA = "referencia";

    /**
     * Devuelve información para abrir un formulario.
     *
     * @param idPaso
     *            Identificador paso.
     * @param idForm
     *            Identificador del formulario.
     * @return Devuelve JSON con la información para abrir un formulario
     */
    // TODO Quitar tras simulacion
    // @RequestMapping(value = "/abrirFormulario.json", method =
    // RequestMethod.POST)
    @RequestMapping(value = "/abrirFormulario.json")
    public ModelAndView abrirFormulario(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_FORMULARIO) final String idForm,
            final HttpServletResponse response) {

        debug("Abriendo formulario: " + idForm);

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        final ParametrosAccionPaso pParametros = new ParametrosAccionPaso();
        pParametros.addParametroEntrada(PARAM_ID_FORMULARIO, idForm);

        // Cancela trámite
        final ResultadoAccionPaso respuestaAbrirFormulario = getFlujoTramitacionService()
                .accionPaso(idSesionTramitacion, idPaso,
                        TypeAccionPasoRellenar.ABRIR_FORMULARIO, pParametros);

        final AbrirFormulario af = (AbrirFormulario) respuestaAbrirFormulario
                .getParametroRetorno(PARAM_REFERENCIA);

        final RespuestaJSON res = new RespuestaJSON();
        final MensajeUsuario mu = new MensajeUsuario("", "");
        res.setMensaje(mu);
        res.setDatos(af);
        return generarJsonView(res);
    }

    /**
     * Guarda formulario del paso rellenar.
     *
     * @param idPaso
     *            Identificador paso.
     * @param idForm
     *            Identificador del formulario.
     * @param ticket
     *            Ticket de acceso generado por el gestor de formularios al
     *            finalizar el formulario.
     * @return Devuelve JSON con estado actual del trámite
     */
    // TODO Quitar tras simulacion
    // @RequestMapping(value = "/guardarFormulario.json", method =
    // RequestMethod.POST)
    @RequestMapping(value = "/guardarFormulario.json")
    public ModelAndView guardarFormulario(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_FORMULARIO) final String idForm,
            @RequestParam(PARAM_TICKET) final String ticket) {

        debug("Guardardando formulario: " + idForm);

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        ParametrosAccionPaso pParametros;
        pParametros = new ParametrosAccionPaso();
        pParametros.addParametroEntrada(PARAM_ID_FORMULARIO, idForm);
        pParametros.addParametroEntrada(PARAM_TICKET, ticket);

        final ResultadoAccionPaso respuestaGuardarFormulario = getFlujoTramitacionService()
                .accionPaso(idSesionTramitacion, idPaso,
                        TypeAccionPasoRellenar.GUARDAR_FORMULARIO, pParametros);

        final TypeSiNo cancelado = (TypeSiNo) respuestaGuardarFormulario
                .getParametroRetorno("cancelado");

        final TypeSiNo correcto = (TypeSiNo) respuestaGuardarFormulario
                .getParametroRetorno("correcto");

        final String mensajeIncorrecto = (String) respuestaGuardarFormulario
                .getParametroRetorno("mensajeIncorrecto");

        // Obtiene detalle tramite
        final DetalleTramite dt = getFlujoTramitacionService()
                .obtenerDetalleTramite(idSesionTramitacion);

        // Especificamos en la respuesta como queda la lista de pasos y el paso
        // actual
        final RespuestaJSON res = new RespuestaJSON();
        res.setDatos(dt);
        if (cancelado == TypeSiNo.NO && correcto == TypeSiNo.NO) {
            final MensajeUsuario mu = new MensajeUsuario(
                    getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES,
                            "noGuardado", getIdioma()),
                    mensajeIncorrecto);
            res.setMensaje(mu);
            res.setEstado(TypeRespuestaJSON.WARNING);
        }

        return generarJsonView(res);
    }

    /**
     * Cancela formulario opcional.
     *
     * @param idPaso
     *            Identificador paso.
     * @param idForm
     *            Identificador del formulario.
     * @return Devuelve JSON con estado actual del trámite
     */
    @RequestMapping(value = "/cancelarFormularioOpcional.json", method = RequestMethod.POST)
    public ModelAndView cancelarFormularioOpcional(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_FORMULARIO) final String idForm) {

        debug("Cancelando formulario: " + idForm);

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        // Ejecuta acción
        ParametrosAccionPaso pParametros;
        pParametros = new ParametrosAccionPaso();
        pParametros.addParametroEntrada(PARAM_ID_FORMULARIO, idForm);
        pParametros.addParametroEntrada("borrarOpcional", TypeSiNo.SI);

        getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
                TypeAccionPasoRellenar.GUARDAR_FORMULARIO, pParametros);

        // Obtiene detalle tramite
        final DetalleTramite dt = getFlujoTramitacionService()
                .obtenerDetalleTramite(idSesionTramitacion);

        // Especificamos en la respuesta como queda la lista de pasos y el paso
        // actual
        final RespuestaJSON res = new RespuestaJSON();
        res.setDatos(dt);
        return generarJsonView(res);
    }

    /**
     * Realiza download de un formulario.
     *
     * @param idPaso
     *            Identificador paso.
     * @param id
     *            Identificador documento.
     * @return Documento para descargar.
     */
    @RequestMapping("/descargarFormulario.html")
    public ModelAndView descargarDocumento(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_FORMULARIO) final String id) {

        debug("Descargar formulario: " + id);

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        ParametrosAccionPaso pParametros;
        pParametros = new ParametrosAccionPaso();
        pParametros.addParametroEntrada(PARAM_ID_FORMULARIO, id);
        final ResultadoAccionPaso resp = getFlujoTramitacionService()
                .accionPaso(idSesionTramitacion, idPaso,
                        TypeAccionPasoRellenar.DESCARGAR_FORMULARIO,
                        pParametros);
        final String nombre = (String) resp
                .getParametroRetorno("nombreFichero");
        final byte[] datosFichero = (byte[]) resp
                .getParametroRetorno("datosFichero");

        return generarDownloadView(nombre, datosFichero);
    }

    /**
     * Realiza download del xml de un formulario.
     *
     * @param idPaso
     *            Identificador paso.
     * @param id
     *            Identificador documento.
     * @return Documento para descargar.
     */
    @RequestMapping("/descargarXmlFormulario.html")
    public ModelAndView descargarXml(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_FORMULARIO) final String id) {

        debug("Descargar xml: " + id);

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        ParametrosAccionPaso pParametros;
        pParametros = new ParametrosAccionPaso();
        pParametros.addParametroEntrada(PARAM_ID_FORMULARIO, id);
        final ResultadoAccionPaso resp = getFlujoTramitacionService()
                .accionPaso(idSesionTramitacion, idPaso,
                        TypeAccionPasoRellenar.DESCARGAR_XML, pParametros);
        final String nombre = id + ".xml";
        final byte[] datosFichero = (byte[]) resp.getParametroRetorno("xml");

        return generarDownloadView(nombre, datosFichero);
    }

    // TODO BORRAR
    @RequestMapping("/simularRellenarFormulario.html")
    public ModelAndView simularRellenarFormulario(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_FORMULARIO) final String idForm) {

        final String idSesionTramitacion = getIdSesionTramitacionActiva();

        ParametrosAccionPaso pParametros;
        pParametros = new ParametrosAccionPaso();
        pParametros.addParametroEntrada(PARAM_ID_FORMULARIO, idForm);
        final ResultadoAccionPaso resp = getFlujoTramitacionService()
                .accionPaso(idSesionTramitacion, idPaso,
                        TypeAccionPasoRellenar.DESCARGAR_XML, pParametros);
        final byte[] datosFichero = (byte[]) resp.getParametroRetorno("xml");

        final MockRellenarFormData m = new MockRellenarFormData();
        m.setIdPaso(idPaso);
        m.setIdFormulario(idForm);
        try {
            m.setXml(new String(datosFichero, "UTF-8"));
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // Redirigimos a carga asistente
        return new ModelAndView("asistente/simularRellenarFormulario", "datos",
                m);

    }

    // TODO BORRAR
    @RequestMapping("/simularGuardarFormulario.html")
    public ModelAndView simularGuardarFormulario(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_FORMULARIO) final String idForm,
            @RequestParam("xml") final String xml) {

        final String idSesionTramitacion = getIdSesionTramitacionActiva();
        final String ticket = getFlujoTramitacionService()
                .simularRellenarFormulario(idSesionTramitacion, xml.trim());

        guardarFormulario(idPaso, idForm, ticket);

        // Redirigimos a carga asistente
        return new ModelAndView("asistente/redirigirAsistente");

    }

}
