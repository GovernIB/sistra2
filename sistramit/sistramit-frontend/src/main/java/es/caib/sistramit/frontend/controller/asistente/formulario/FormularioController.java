package es.caib.sistramit.frontend.controller.asistente.formulario;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.core.api.model.formulario.Captcha;
import es.caib.sistramit.core.api.model.formulario.PaginaFormulario;
import es.caib.sistramit.core.api.model.formulario.ResultadoBuscadorDinamico;
import es.caib.sistramit.core.api.model.formulario.ResultadoEvaluarCambioCampo;
import es.caib.sistramit.core.api.model.formulario.ResultadoGuardarPagina;
import es.caib.sistramit.core.api.model.formulario.ValorCampo;
import es.caib.sistramit.core.api.model.formulario.types.TypePalabraReservada;
import es.caib.sistramit.core.api.service.FlujoFormularioInternoService;
import es.caib.sistramit.frontend.controller.TramitacionController;
import es.caib.sistramit.frontend.model.MensajeUsuario;
import es.caib.sistramit.frontend.model.RespuestaJSON;
import es.caib.sistramit.frontend.model.types.TypeRespuestaJSON;

/**
 * Interacción con gestor formularios interno.
 *
 * @author Indra
 *
 */
@Controller
@RequestMapping(value = "/asistente/fm")
public final class FormularioController extends TramitacionController {

	/** Gestor formulario interno. */
	@Autowired
	private FlujoFormularioInternoService formService;

	/**
	 * Carga la sesión iniciada en el gestor de formularios.
	 *
	 * @param ticket
	 *                   Ticket de acceso a la sesión del formulario
	 * @return Datos página inicial del formulario.
	 */
	@RequestMapping("/cargarFormulario.json")
	public ModelAndView cargarFormulario(@RequestParam("ticket") final String ticket) {

		// Carga sesion formulario y registrarlo en la sesion
		final String idSesionFormulario = formService.cargarSesion(ticket);
		formService.inicializarSesion(idSesionFormulario);
		this.registraSesionFormulario(idSesionFormulario);

		// Carga pagina inicial
		return cargarPaginaActual();
	}

	/**
	 * Realiza la carga de la página actual.
	 *
	 * @return Datos de la página.
	 */
	@RequestMapping("/cargarPaginaActual.json")
	public ModelAndView cargarPaginaActual() {
		final String idSesionFormulario = getIdSesionFormuarioActiva();
		final PaginaFormulario pagina = formService.cargarPaginaActual(idSesionFormulario);
		final RespuestaJSON res = new RespuestaJSON();
		res.setDatos(pagina);
		return generarJsonView(res);
	}

	/**
	 * Realiza la carga de la página anterior.
	 *
	 * @return Datos de la página.
	 */
	@RequestMapping("/cargarPaginaAnterior.json")
	public ModelAndView cargarPaginaAnterior(final HttpServletRequest request) {
		final String idSesionFormulario = getIdSesionFormuarioActiva();

		// Recuperamos valores pagina
		final Map<String, String> valoresRequest = extraerValoresCampo(request);

		// Deserializamos valores
		final List<ValorCampo> lista = formService.deserializarValoresCampos(idSesionFormulario, valoresRequest);

		// Cargamos pagina anterior
		final PaginaFormulario pagina = formService.cargarPaginaAnterior(idSesionFormulario, lista);
		final RespuestaJSON res = new RespuestaJSON();
		res.setDatos(pagina);
		return generarJsonView(res);
	}

	/**
	 * Evalua el cambio de una página de un formulario y calcula el valor los campos
	 * según los scripts del formulario.
	 *
	 * @param idCampo
	 *                    Id campo que se esta modificando
	 * @param request
	 *                    Request para obtener los datos actuales de la página en el
	 *                    cliente
	 * @return Datos de la página resultantes que deben refrescarse en el cliente
	 */
	@RequestMapping("/evaluarCambioCampo.json")
	public ModelAndView evaluarCambioCampoPagina(@RequestParam("idCampo") final String idCampo,
			final HttpServletRequest request) {

		final String idSesionFormulario = getIdSesionFormuarioActiva();

		// Recuperamos valores pagina
		final Map<String, String> valoresRequest = extraerValoresCampo(request);

		// Deserializamos valores
		final List<ValorCampo> lista = formService.deserializarValoresCampos(idSesionFormulario, valoresRequest);

		// Invocamos al controlador para evaluar el cambio
		final ResultadoEvaluarCambioCampo re = formService.evaluarCambioCampoPagina(idSesionFormulario, idCampo, lista);

		// Devolvemos respuesta
		final RespuestaJSON resp = new RespuestaJSON();
		resp.setDatos(re);
		resp.setMensaje(new MensajeUsuario("", ""));
		resp.setEstado(TypeRespuestaJSON.SUCCESS);

		return generarJsonView(resp);

	}

	@RequestMapping("/buscadorDinamico.json")
	public ModelAndView buscadorDinamico(@RequestParam("idCampo") final String idCampo,
			@RequestParam("textoCampo") final String textoCampo, final HttpServletRequest request) {

		final String idSesionFormulario = getIdSesionFormuarioActiva();

		// Recuperamos valores pagina
		final Map<String, String> valoresRequest = extraerValoresCampo(request);

		// Deserializamos valores
		final List<ValorCampo> lista = formService.deserializarValoresCampos(idSesionFormulario, valoresRequest);

		// Invocamos al controlador para evaluar el cambio
		final ResultadoBuscadorDinamico re = formService.buscadorDinamico(idSesionFormulario, idCampo, textoCampo,
				lista);

		// Devolvemos respuesta
		final RespuestaJSON resp = new RespuestaJSON();
		resp.setDatos(re);
		resp.setMensaje(new MensajeUsuario("", ""));
		resp.setEstado(TypeRespuestaJSON.SUCCESS);

		return generarJsonView(resp);

	}

	/**
	 * Guarda los datos de la página.
	 *
	 * @param request
	 *                    Datos de la página
	 * @return Resultado de guardar la página: indica si se ha guardado bien, si se
	 *         ha llegado al fin del formulario, si no ha pasado la validación y se
	 *         ha generado un mensaje, etc. En caso de llegar al fin del formulario
	 *         se indicará el ticket de acceso al XML y PDF generados.
	 */
	@RequestMapping("/guardarPagina.json")
	public ModelAndView guardarPagina(final HttpServletRequest request) {

		final String idSesionFormulario = getIdSesionFormuarioActiva();

		// Recuperamos valores pagina
		final Map<String, String> valoresRequest = extraerValoresCampo(request);

		// Deserializamos valores
		final List<ValorCampo> lista = formService.deserializarValoresCampos(idSesionFormulario, valoresRequest);

		// Invocamos a controlador para guardar la pagina
		final ResultadoGuardarPagina rgp = formService.guardarPagina(idSesionFormulario, lista,
				request.getParameter("accion"));

		// Devolvemos respuesta
		final RespuestaJSON res = new RespuestaJSON();
		res.setDatos(rgp);
		return generarJsonView(res);
	}

	/**
	 * Guarda los datos de la página y sale del formulario sin terminar.
	 *
	 * @param request
	 *                    Datos de la página
	 * @return Resultado de guardar la página: indica redireccion para volver.
	 */
	@RequestMapping("/guardarSalirPagina.json")
	public ModelAndView guardarSalirPagina(final HttpServletRequest request) {

		final String idSesionFormulario = getIdSesionFormuarioActiva();

		// Recuperamos valores pagina
		final Map<String, String> valoresRequest = extraerValoresCampo(request);

		// Deserializamos valores
		final List<ValorCampo> lista = formService.deserializarValoresCampos(idSesionFormulario, valoresRequest);

		// Invocamos a controlador para guardar la pagina sin terminar formulario
		final ResultadoGuardarPagina rgp = formService.guardarSalirPagina(idSesionFormulario, lista);

		// Devolvemos respuesta
		final RespuestaJSON res = new RespuestaJSON();
		res.setDatos(rgp);
		return generarJsonView(res);
	}

	/**
	 * Cancela el rellenado del formulario.
	 *
	 * @return No retorna ningún valor especial. Si tiene éxito se deberá recargar
	 *         el paso actual.
	 */
	@RequestMapping("/cancelar.json")
	public ModelAndView cancelar() {
		final String idSesionFormulario = getIdSesionFormuarioActiva();
		formService.cancelarFormulario(idSesionFormulario);
		final RespuestaJSON res = new RespuestaJSON();
		return generarJsonView(res);
	}

	/**
	 * Método para generar imagen de un captcha (XDP).
	 *
	 * @param idCampo
	 *                    idCampo
	 * @return Imagen con el captcha
	 */
	@RequestMapping("/generarImagenCaptcha.html")
	public ModelAndView generarImagenCaptcha(@RequestParam("id") final String idCampo) {
		final String idSesionFormulario = getIdSesionFormuarioActiva();
		final Captcha captcha = formService.generarImagenCaptcha(idSesionFormulario, idCampo);
		return generarDownloadView(captcha.getFichero(), captcha.getContenido());
	}

	/**
	 * Método para generar sonido de un captcha.
	 *
	 * @param idCampo
	 *                    idCampo
	 * @return sonido con el captcha
	 */
	@RequestMapping("/generarSonidoCaptcha.html")
	public ModelAndView generarSonidoCaptcha(@RequestParam("id") final String idCampo) {
		final String idSesionFormulario = getIdSesionFormuarioActiva();
		final Captcha captcha = formService.generarSonidoCaptcha(idSesionFormulario, idCampo);
		return generarDownloadView(captcha.getFichero(), captcha.getContenido());
	}

	/**
	 * Método para regenerar captcha.
	 *
	 * @param idCampo
	 *                    idCampo
	 * @return Indica que se ha regenerado para que se vuelva a cargar
	 */
	@RequestMapping("/regenerarCaptcha.html")
	public ModelAndView regenerarCaptcha(@RequestParam("id") final String idCampo) {
		final String idSesionFormulario = getIdSesionFormuarioActiva();
		formService.regenerarCaptcha(idSesionFormulario, idCampo);
		final RespuestaJSON res = new RespuestaJSON();
		res.setEstado(TypeRespuestaJSON.SUCCESS);
		return generarJsonView(res);
	}

	// ----------------------------------------------------
	// METODOS PRIVADOS
	// ----------------------------------------------------

	/**
	 * Extrae valores campos de la request.
	 *
	 * @param request
	 *                    Request
	 * @return valores campos
	 */
	private Map<String, String> extraerValoresCampo(final HttpServletRequest request) {
		final Enumeration<String> enumer = request.getParameterNames();
		final Map<String, String> valoresRequest = new LinkedHashMap<String, String>();
		while (enumer.hasMoreElements()) {
			final String enumString = enumer.nextElement();
			if (TypePalabraReservada.fromString(enumString) != null) {
				continue;
			}
			final String valor = request.getParameter(enumString);
			valoresRequest.put(enumString, valor);
		}
		return valoresRequest;
	}

}
