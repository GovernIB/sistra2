package es.caib.sistramit.frontend.controller.asistente.pasos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.RedireccionFirmaCliente;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoRegistrar;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoRegistrar;
import es.caib.sistramit.frontend.controller.TramitacionController;
import es.caib.sistramit.frontend.literales.LiteralesFront;
import es.caib.sistramit.frontend.model.MensajeUsuario;
import es.caib.sistramit.frontend.model.RespuestaJSON;
import es.caib.sistramit.frontend.model.types.TypeRespuestaJSON;

/**
 * Interacción con paso Registrar.
 *
 * @author Indra
 *
 */
@Controller
@RequestMapping(value = "/asistente/rt")
public class PasoRegistrarController extends TramitacionController {

	/** Constante parametro id paso. */
	private static final String PARAM_ID_PASO = "idPaso";

	/** Constante parametro id documento. */
	private static final String PARAM_ID_DOCUMENTO = "idDocumento";

	/** Constante parametro instancia. */
	private static final String PARAM_INSTANCIA = "instancia";

	/** Constante parametro firmante. */
	private static final String PARAM_FIRMANTE = "firmante";

	/**
	 * Realiza download de un documento rellenado en el trámite.
	 *
	 * @param idPaso
	 *                        Identificador paso.
	 * @param idDocumento
	 *                        Identificador documento.
	 * @param instancia
	 *                        Instancia documento.
	 * @return Documento para descargar.
	 */
	@RequestMapping("/descargarDocumento.html")
	public ModelAndView descargarDocumento(@RequestParam(PARAM_ID_PASO) final String idPaso,
			@RequestParam(PARAM_ID_DOCUMENTO) final String idDocumento,
			@RequestParam(PARAM_INSTANCIA) final String instancia) {

		debug("Obteniendo documento registro: " + idDocumento + " - " + instancia);

		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		ParametrosAccionPaso pParametros;
		pParametros = new ParametrosAccionPaso();
		pParametros.addParametroEntrada(PARAM_ID_DOCUMENTO, idDocumento);
		pParametros.addParametroEntrada(PARAM_INSTANCIA, instancia);

		final ResultadoAccionPaso rap = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoRegistrar.DESCARGAR_DOCUMENTO, pParametros);
		final byte[] datos = (byte[]) rap.getParametroRetorno("datosFichero");
		final String nombreFichero = (String) rap.getParametroRetorno("nombreFichero");

		return generarDownloadView(nombreFichero, datos);

	}

	/**
	 * Realiza download de una firma de un documento.
	 *
	 * @param idPaso
	 *                        Identificador paso.
	 * @param idDocumento
	 *                        Identificador documento.
	 * @param instancia
	 *                        Instancia documento.
	 * @param firmante
	 *                        Nif firmante.
	 * @return Firma para descargar.
	 */
	@RequestMapping("/descargarFirma.html")
	public ModelAndView descargarFirma(@RequestParam(PARAM_ID_PASO) final String idPaso,
			@RequestParam(PARAM_ID_DOCUMENTO) final String idDocumento,
			@RequestParam(PARAM_INSTANCIA) final String instancia,
			@RequestParam(PARAM_FIRMANTE) final String firmante) {

		debug("Obteniendo firma documento registro: " + idDocumento + " - " + instancia + " para nif " + firmante);

		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		ParametrosAccionPaso pParametros;
		pParametros = new ParametrosAccionPaso();
		pParametros.addParametroEntrada(PARAM_ID_DOCUMENTO, idDocumento);
		pParametros.addParametroEntrada(PARAM_INSTANCIA, instancia);
		pParametros.addParametroEntrada(PARAM_FIRMANTE, firmante);

		final ResultadoAccionPaso rap = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoRegistrar.DESCARGAR_FIRMA, pParametros);
		final byte[] datos = (byte[]) rap.getParametroRetorno("datosFichero");
		final String nombreFichero = (String) rap.getParametroRetorno("nombreFichero");

		return generarDownloadView(nombreFichero, datos);

	}

	/**
	 * Firmar documento: redirección a componente externo de firma.
	 *
	 * @param idPaso
	 *                        Identificador paso.
	 * @param idDocumento
	 *                        Identificador documento.
	 * @param instancia
	 *                        Instancia documento.
	 * @return Devuelve JSON indicando redireccion para firmar.
	 */
	@RequestMapping(value = "/firmarDocumento.json", method = RequestMethod.POST)
	public ModelAndView firmarDocumento(@RequestParam(PARAM_ID_PASO) final String idPaso,
			@RequestParam(PARAM_ID_DOCUMENTO) final String idDocumento,
			@RequestParam(PARAM_INSTANCIA) final String instancia,
			@RequestParam(PARAM_FIRMANTE) final String firmante) {

		debug("Redirigir firma documento: " + idDocumento + " - " + instancia + " para firmante " + firmante);

		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		ParametrosAccionPaso pParametros;
		pParametros = new ParametrosAccionPaso();
		pParametros.addParametroEntrada(PARAM_ID_DOCUMENTO, idDocumento);
		pParametros.addParametroEntrada(PARAM_INSTANCIA, instancia);
		pParametros.addParametroEntrada(PARAM_FIRMANTE, firmante);

		final ResultadoAccionPaso rap = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoRegistrar.INICIAR_FIRMA_DOCUMENTO, pParametros);
		final String url = (String) rap.getParametroRetorno("url");

		debug("Iniciar firma electronico - redireccion url: " + url);
		final RedireccionFirmaCliente redireccion = new RedireccionFirmaCliente();
		redireccion.setUrl(url);
		final RespuestaJSON respuesta = new RespuestaJSON();
		respuesta.setDatos(redireccion);
		final String tituloMensaje = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES, "atencion",
				getIdioma());
		final String textoMensaje = getLiteralesFront().getLiteralFront(LiteralesFront.MENSAJES,
				"redireccionFirmaCliente", getIdioma());
		respuesta.setMensaje(new MensajeUsuario(tituloMensaje, textoMensaje));

		return generarJsonView(respuesta);

	}

	/**
	 * Registra trámite.
	 *
	 * @param idPaso
	 *                   Identificador paso.
	 * @return Devuelve JSON con estado actual del trámite.
	 */
	@RequestMapping(value = "/registrarTramite.json", method = RequestMethod.POST)
	public ModelAndView registrarTramite(@RequestParam(PARAM_ID_PASO) final String idPaso) {
		debug("Registrar tramite");
		return procesoRegistrar(idPaso, TypeSiNo.NO);
	}

	/**
	 * Reintenta registro trámite.
	 *
	 * @param idPaso
	 *                   Identificador paso.
	 * @return Devuelve JSON con estado actual del trámite.
	 */
	@RequestMapping(value = "/reintentar.json", method = RequestMethod.POST)
	public ModelAndView reintentarRegistroTramite(@RequestParam(PARAM_ID_PASO) final String idPaso) {
		debug("Reintentar registro tramite");
		return procesoRegistrar(idPaso, TypeSiNo.SI);
	}

	/**
	 * Proceso registrar trámite.
	 *
	 * @param idPaso
	 *                       id paso
	 * @param reintentar
	 *                       reintentar
	 * @return resultado registro
	 */
	private ModelAndView procesoRegistrar(final String idPaso, final TypeSiNo reintentar) {
		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		ParametrosAccionPaso pParametros;

		// Si no es reintentar, iniciamos sesion registro
		if (reintentar == TypeSiNo.NO) {
			pParametros = new ParametrosAccionPaso();
			final ResultadoAccionPaso rap = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
					TypeAccionPasoRegistrar.INICIAR_SESION_REGISTRO, null);
			final String idSesionRegistro = (String) rap.getParametroRetorno("idSesionRegistro");
			this.debug("Sesión registro: " + idSesionRegistro);
		}

		// Registramos / reintentamos
		pParametros = new ParametrosAccionPaso();
		pParametros.addParametroEntrada("reintentar", reintentar);
		final ResultadoAccionPaso rap = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoRegistrar.REGISTRAR_TRAMITE, pParametros);
		final ResultadoRegistrar resReg = (ResultadoRegistrar) rap.getParametroRetorno("resultado");

		// Generamos mensaje respuesta
		TypeRespuestaJSON estado = null;
		String titulo = null;
		String literal = null;
		switch (resReg.getResultado()) {
		case CORRECTO:
			estado = TypeRespuestaJSON.SUCCESS;
			titulo = "registroRealizado";
			literal = "registroRealizado.literal";
			break;
		case ERROR:
			estado = TypeRespuestaJSON.ERROR;
			titulo = "registroError";
			literal = "registroError.literal";
			break;
		case REINTENTAR:
			estado = TypeRespuestaJSON.WARNING;
			titulo = "registroReintentar";
			literal = "registroReintentar.literal";
			break;
		default:
			estado = TypeRespuestaJSON.FATAL;
			titulo = "registroError";
			literal = "registroError.literal";
			break;
		}

		final RespuestaJSON respuesta = new RespuestaJSON();
		respuesta.setEstado(estado);
		respuesta.setMensaje(generarMensajeUsuario(titulo, literal));
		respuesta.setUrl("asistente.html");
		return generarJsonView(respuesta);
	}

}
