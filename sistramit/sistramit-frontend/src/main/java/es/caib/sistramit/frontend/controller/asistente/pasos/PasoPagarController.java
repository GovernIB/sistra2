package es.caib.sistramit.frontend.controller.asistente.pasos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.PagoVerificacion;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.RedireccionPago;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoPagar;
import es.caib.sistramit.frontend.controller.TramitacionController;
import es.caib.sistramit.frontend.literales.LiteralesFront;
import es.caib.sistramit.frontend.model.MensajeAsistente;
import es.caib.sistramit.frontend.model.MensajeUsuario;
import es.caib.sistramit.frontend.model.RespuestaJSON;

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
	 * @param idPaso    Identificador paso.
	 * @param idPago    Identificador del pago.
	 * @param reiniciar Indica si se fuerza a iniciar un pago.
	 * @return Devuelve JSON con la información para realizar un pago (salto a
	 *         componente externo de pagos).
	 */
	@RequestMapping(value = "/iniciarPago.json", method = RequestMethod.POST)
	public ModelAndView iniciarPago(@RequestParam(PARAM_ID_PASO) final String idPaso,
			@RequestParam(PARAM_ID_PAGO) final String idPago,
			@RequestParam(value = "reiniciar", required = false) final String reiniciar) {

		debug("Iniciar pago electronico: " + idPago);

		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		final ParametrosAccionPaso params = new ParametrosAccionPaso();
		params.addParametroEntrada(PARAM_ID_PAGO, idPago);
		final ResultadoAccionPaso rp = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoPagar.INICIAR_PAGO, params);
		final String url = (String) rp.getParametroRetorno("url");
		final TypeSiNo simulado = (TypeSiNo) rp.getParametroRetorno("simulado");

		debug("Iniciar pago electronico - redireccion url: " + url);
		final RedireccionPago redireccion = new RedireccionPago();
		redireccion.setUrl(url);

		final RespuestaJSON resPagar = new RespuestaJSON();
		resPagar.setDatos(redireccion);
		if (simulado == TypeSiNo.SI) {
			final String tituloMensaje = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, "atencion",
					getIdioma());
			final String textoMensaje = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, "pagoSimulado",
					getIdioma());
			resPagar.setMensaje(new MensajeUsuario(tituloMensaje, textoMensaje));
		}
		return generarJsonView(resPagar);

	}

	/**
	 * Cancela pago pasarela iniciado.
	 *
	 * @param idPaso id paso
	 * @param idPago id pago
	 * @return indica si se ha realizado correctamente
	 */
	@RequestMapping(value = "/cancelarPagoIniciado.json", method = RequestMethod.POST)
	public ModelAndView cancelarPagoIniciado(@RequestParam(PARAM_ID_PASO) final String idPaso,
			@RequestParam(PARAM_ID_PAGO) final String idPago) {

		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		final ParametrosAccionPaso params = new ParametrosAccionPaso();
		params.addParametroEntrada(PARAM_ID_PAGO, idPago);
		final ResultadoAccionPaso ra = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoPagar.CANCELAR_PAGO_INICIADO, params);

		final RespuestaJSON resPagar = new RespuestaJSON();
		final String tituloMensaje = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, "atencion",
				getIdioma());
		final String textoMensaje = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, "pagoCancelado",
				getIdioma());
		resPagar.setMensaje(new MensajeUsuario(tituloMensaje, textoMensaje));
		return generarJsonView(resPagar);

	}

	/**
	 * Verifica si un pago se ha realizado.
	 *
	 * @param idPaso Identificador paso.
	 * @param idPago Identificador del pago.
	 * @return Devuelve JSON con la información de verificación de un pago.
	 */
	@RequestMapping(value = "/verificarPagoIniciado.json", method = RequestMethod.POST)
	public ModelAndView verificarPagoIniciado(@RequestParam(PARAM_ID_PASO) final String idPaso,
			@RequestParam(PARAM_ID_PAGO) final String idPago) {

		debug("Validar pago iniciado");

		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		final ParametrosAccionPaso params = new ParametrosAccionPaso();
		params.addParametroEntrada(PARAM_ID_PAGO, idPago);
		final ResultadoAccionPaso ra = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoPagar.VERIFICAR_PAGO_PASARELA, params);
		final PagoVerificacion verificacionPago = (PagoVerificacion) ra.getParametroRetorno("verificacion");

		debug("Estado pago: Verificado = " + verificacionPago.getVerificado() + " - Realizado = "
				+ verificacionPago.getRealizado());

		final MensajeAsistente ma = generarMensajeValidacionPago(verificacionPago);

		final RespuestaJSON res = new RespuestaJSON();
		res.setEstado(ma.getTipo());
		res.setMensaje(ma.getMensaje());
		res.setDatos(verificacionPago);
		return generarJsonView(res);

	}

	/**
	 * Genera carta de pago para pago presencial.
	 *
	 * @param idPaso Identificador paso.
	 * @param idPago Identificador del pago.
	 * @return Devuelve JSON con la información de verificación de un pago.
	 */
	@RequestMapping(value = "/cartaPago.json", method = RequestMethod.POST)
	public ModelAndView cartaPago(@RequestParam(PARAM_ID_PASO) final String idPaso,
			@RequestParam(PARAM_ID_PAGO) final String idPago) {

		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		final ParametrosAccionPaso params = new ParametrosAccionPaso();
		params.addParametroEntrada(PARAM_ID_PAGO, idPago);
		final ResultadoAccionPaso ra = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoPagar.CARTA_PAGO_PRESENCIAL, params);

		final RespuestaJSON resPagar = new RespuestaJSON();
		final String tituloMensaje = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, "atencion",
				getIdioma());
		final String textoMensaje = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, "pagoCartaPago",
				getIdioma());
		resPagar.setMensaje(new MensajeUsuario(tituloMensaje, textoMensaje));
		return generarJsonView(resPagar);

	}

	/**
	 * Realiza download del justificante de pago.
	 *
	 * @param idPaso Identificador paso.
	 * @param idPago Identificador pago.
	 * @return Documento para descargar.
	 */
	@RequestMapping("/descargarJustificante.html")
	public ModelAndView descargarJustificante(@RequestParam(PARAM_ID_PASO) final String idPaso,
			@RequestParam(PARAM_ID_PAGO) final String idPago) {

		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		final ParametrosAccionPaso params = new ParametrosAccionPaso();
		params.addParametroEntrada(PARAM_ID_PAGO, idPago);
		final ResultadoAccionPaso ra = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoPagar.DESCARGAR_JUSTIFICANTE, params);
		final byte[] just = (byte[]) ra.getParametroRetorno("datos");

		final String nombre = getLiteralesFront().getLiteralFront(LiteralesFront.APLICACION, "justificanteNombre",
				getIdioma());
		final byte[] datosFichero = just;
		return generarDownloadView(nombre, datosFichero);
	}

}
