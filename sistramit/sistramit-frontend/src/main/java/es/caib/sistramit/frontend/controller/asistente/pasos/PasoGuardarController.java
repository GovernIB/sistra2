package es.caib.sistramit.frontend.controller.asistente.pasos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.frontend.controller.TramitacionController;

/**
 * Interacción con paso Guardar.
 *
 * @author Indra
 *
 */
@Controller
@RequestMapping(value = "/asistente/gj")
public final class PasoGuardarController extends TramitacionController {

    /** Constante parametro id paso. */
    private static final String PARAM_ID_PASO = "idPaso";

    /** Constante parametro id documento. */
    private static final String PARAM_ID_DOCUMENTO = "idDocumento";

    /** Constante parametro instancia. */
    private static final String PARAM_INSTANCIA = "instancia";

    /**
     * Realiza download justificante registro.
     *
     * @param idPaso
     *            Identificador paso.
     * @param idDocumento
     *            Identificador documento.
     * @param instancia
     *            Instancia documento.
     * @return Documento para descargar.
     */
    @RequestMapping("/descargarJustificante.html")
    public ModelAndView descargarJustificante(
            @RequestParam(PARAM_ID_PASO) final String idPaso) {

        // TODO PENDIENTE
        return null;

    }

    /**
     * Realiza download de un documento rellenado en el trámite.
     *
     * @param idPaso
     *            Identificador paso.
     * @param idDocumento
     *            Identificador documento.
     * @param instancia
     *            Instancia documento.
     * @return Documento para descargar.
     */
    @RequestMapping("/descargarDocumento.html")
    public ModelAndView descargarDocumento(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_DOCUMENTO) final String idDocumento,
            @RequestParam(PARAM_INSTANCIA) final String instancia) {

        // TODO PENDIENTE
        return null;

    }

}
