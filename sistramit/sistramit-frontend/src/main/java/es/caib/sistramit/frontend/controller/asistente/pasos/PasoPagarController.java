package es.caib.sistramit.frontend.controller.asistente.pasos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.frontend.controller.TramitacionController;

/**
 * Interacción con paso Pagar.
 *
 * @author Indra
 *
 */
@Controller
@RequestMapping(value = "/asistente/pt")
public final class PasoPagarController extends TramitacionController {

    /** Constantes parametro id paso. */
    private static final String PARAM_ID_PASO = "idPaso";

    /** Constantes parametro id pago. */
    private static final String PARAM_ID_PAGO = "idPago";

    /**
     * Devuelve información para realizar un pago.
     *
     * @param idPaso
     *            Identificador paso.
     * @param idPago
     *            Identificador del pago.
     * @param reiniciar
     *            Indica si se fuerza a iniciar un pago.
     * @return Devuelve JSON con la información para realizar un pago (salto a
     *         componente externo de pagos).
     */
    @RequestMapping(value = "/iniciarPago.json", method = RequestMethod.POST)
    public ModelAndView iniciarPago(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_PAGO) final String idPago,
            @RequestParam(value = "reiniciar", required = false) final String reiniciar) {

        // TODO Pendiente
        return null;

    }

    /**
     * Cancela pago pasarela iniciado.
     *
     * @param idPaso
     *            id paso
     * @param idPago
     *            id pago
     * @return indica si se ha realizado correctamente
     */
    @RequestMapping(value = "/cancelarPagoIniciado.json", method = RequestMethod.POST)
    public ModelAndView cancelarPagoIniciado(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_PAGO) final String idPago) {

        // TODO PENDIENTE
        return null;

    }

    /**
     * Verifica si un pago se ha realizado.
     *
     * @param idPaso
     *            Identificador paso.
     * @param idPago
     *            Identificador del pago.
     * @return Devuelve JSON con la información de verificación de un pago.
     */
    @RequestMapping(value = "/verificarPagoIniciado.json", method = RequestMethod.POST)
    public ModelAndView verificarPagoIniciado(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_PAGO) final String idPago) {

        // TODO PENDIENTE
        return null;

    }

    /**
     * Realiza download del justificante de pago.
     *
     * @param idPaso
     *            Identificador paso.
     * @param idPago
     *            Identificador pago.
     * @return Documento para descargar.
     */
    @RequestMapping("/descargarJustificante.html")
    public ModelAndView descargarJustificante(
            @RequestParam(PARAM_ID_PASO) final String idPaso,
            @RequestParam(PARAM_ID_PAGO) final String idPago) {

        // TODO PENDIENTE
        return null;

    }

}
