package es.caib.sistramit.frontend.controller.asistente.pasos;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.flujo.ParametrosAccionPaso;
import es.caib.sistramit.core.api.model.flujo.ResultadoAccionPaso;
import es.caib.sistramit.core.api.model.flujo.types.TypeAccionPasoGuardar;
import es.caib.sistramit.frontend.controller.TramitacionController;
import es.caib.sistramit.frontend.model.RedireccionUrl;
import es.caib.sistramit.frontend.model.RespuestaJSON;

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
	public ModelAndView descargarJustificante(@RequestParam(PARAM_ID_PASO) final String idPaso) {

		debug("Obteniendo justificante registro");

		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		ParametrosAccionPaso pParametros;
		pParametros = new ParametrosAccionPaso();

		final ResultadoAccionPaso rap = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoGuardar.DESCARGAR_JUSTIFICANTE, pParametros);
		final byte[] datos = (byte[]) rap.getParametroRetorno("datosFichero");
		final String nombreFichero = (String) rap.getParametroRetorno("nombreFichero");
		return generarDownloadView(nombreFichero, datos);

	}

	/**
	 * Realiza download justificante registro mediante redirección.
	 *
	 * @param idPaso
	 *            Identificador paso.
	 * @param idDocumento
	 *            Identificador documento.
	 * @param instancia
	 *            Instancia documento.
	 * @return Documento para descargar.
	 */
	@RequestMapping(value = "/redirigirJustificante.json", method = RequestMethod.POST)
	public ModelAndView redirigirJustificante(@RequestParam(PARAM_ID_PASO) final String idPaso) {

		debug("Obteniendo justificante registro para redirección");

		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		ParametrosAccionPaso pParametros;
		pParametros = new ParametrosAccionPaso();

		final ResultadoAccionPaso rap = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoGuardar.DESCARGAR_JUSTIFICANTE, pParametros);
		final String url = (String) rap.getParametroRetorno("url");

		final RedireccionUrl redireccion = new RedireccionUrl();
		redireccion.setUrl(url);
		final RespuestaJSON respuesta = new RespuestaJSON();
		respuesta.setDatos(redireccion);
		return generarJsonView(respuesta);

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
				TypeAccionPasoGuardar.DESCARGAR_DOCUMENTO, pParametros);
		final byte[] datos = (byte[]) rap.getParametroRetorno("datosFichero");
		final String nombreFichero = (String) rap.getParametroRetorno("nombreFichero");

		return generarDownloadView(nombreFichero, datos);

	}

	/**
	 * Valoración trámite.
	 *
	 * @param idPaso
	 *            Identificador paso.
	 * @param idDocumento
	 *            Identificador documento.
	 * @param instancia
	 *            Instancia documento.
	 * @return Documento para descargar.
	 */
	@RequestMapping(value = "/valorarTramite.json", method = RequestMethod.POST)
	public ModelAndView valorarTramite(@RequestParam(PARAM_ID_PASO) final String idPaso,
			@RequestParam("valoracion") final String valoracion,
			@RequestParam("problemas") final List<String> problemas,
			@RequestParam("observaciones") final String observaciones) {

		debug("Valoración trámite");

		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		// Serializamos lista problemas a un string separado por ;
		String problemasStr = null;
		if (problemas != null) {
			problemasStr = "";
			for (final String p : problemas) {
				problemasStr += (p + ";");
			}
		}

		ParametrosAccionPaso pParametros;
		pParametros = new ParametrosAccionPaso();
		pParametros.addParametroEntrada("valoracion", valoracion);
		pParametros.addParametroEntrada("problemas", problemasStr);
		pParametros.addParametroEntrada("observaciones", observaciones);

		final ResultadoAccionPaso rap = getFlujoTramitacionService().accionPaso(idSesionTramitacion, idPaso,
				TypeAccionPasoGuardar.VALORACION_TRAMITE, pParametros);

		final RespuestaJSON respuesta = new RespuestaJSON();
		return generarJsonView(respuesta);

	}

	/**
	 * Sale del trámite.
	 *
	 * @return Sale del trámite.
	 */
	@RequestMapping("/salirTramite.html")
	public ModelAndView salirTramite() {

		debug("Salir trámite");

		final String idSesionTramitacion = getIdSesionTramitacionActiva();

		// Obtenemos url tras finalizar
		final DetalleTramite detalleTramite = getFlujoTramitacionService().obtenerDetalleTramite(idSesionTramitacion);
		final String url = detalleTramite.getEntidad().getUrlCarpeta();

		// Invalidamos flujo
		getFlujoTramitacionService().invalidarFlujoTramitacion(idSesionTramitacion);

		return new ModelAndView("redirect:" + url);

	}

}
