package es.caib.sistramit.frontend.controller.asistente.pasos;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.frontend.controller.TramitacionController;

/**
 * Interacción con paso Anexar.
 *
 * @author Indra
 *
 */
@Controller
@RequestMapping(value = "/asistente/ad")
public final class PasoAnexarController extends TramitacionController {

    /** Constante parametro id paso. */
    private static final String PARAM_ID_PASO = "idPaso";
    /** Constante parametro id anexo. */
    private static final String PARAM_ID_ANEXO = "idAnexo";
    /** Constante parametro instancia. */
    private static final String PARAM_INSTANCIA = "instancia";

    /**
     * Realiza download de la plantilla de un anexo.
     *
     * @param idPaso
     *            Identificador paso.
     * @param idAnexo
     *            Identificador anexo.
     * @return Documento para descargar.
     */
    @RequestMapping("/descargarPlantilla.html")
    public ModelAndView descargarPlantilla(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_ANEXO) final String idAnexo) {

        // TODO Pendiente
        return null;

    }

    /**
     * Anexa un documento. El upload del documento se realizará desde un iframe
     * de forma que como respuesta se generará un HTML que se cargará en el
     * iframe e invocará a la función fileUploaded del parent pasando el estado
     * actual del trámite.
     *
     * @param idPaso
     *            Identificador paso.
     * @param idAnexo
     *            Identificador anexo.
     * @param titulo
     *            Título del anexo (para genéricos).
     * @param request
     *            Request para extraer el fichero (busca fichero en el parámetro
     *            de la request "fichero").
     * @return Genera un HTML que invoca a la función fileUploaded del parent
     */
    @RequestMapping(value = "/anexarDocumento.html", method = RequestMethod.POST)
    public ModelAndView anexarDocumento(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_ANEXO) final String idAnexo,
            @RequestParam(value = "titulo", required = false) final String titulo,
            final HttpServletRequest request) {

        // TODO PENDIENTE
        // return generarUploadView(resAnexar);

        return null;
    }

    /**
     * Borra un documento.
     *
     * @param idPaso
     *            Id paso
     * @param idAnexo
     *            Id anexo
     * @param instancia
     *            Instancia (en caso de ser multiinstancia, es decir, genérico)
     * @return Devuelve JSON con estado actual del paso
     */
    @RequestMapping(value = "/asistente/ad/borrarDocumento.json", method = RequestMethod.POST)
    public ModelAndView borrarDocumento(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_ANEXO) final String idAnexo,
            @RequestParam(value = PARAM_INSTANCIA, required = false) final String instancia) {

        // TODO PENDIENTE
        return null;

    }

    /**
     * Devuelve contenido del anexo.
     *
     * @param idPaso
     *            Identificador paso.
     * @param idAnexo
     *            Identificador del anexo.
     * @param instancia
     *            Instancia (en caso de ser multiinstancia, es decir, genérico)
     * @return Genera descarga documento
     */
    @RequestMapping(value = "/descargarDocumento.html")
    public ModelAndView descargarDocumento(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_ANEXO) final String idAnexo,
            @RequestParam(value = PARAM_INSTANCIA, required = false) final String instancia) {

        // TODO PENDIENTE
        return null;

    }

}
