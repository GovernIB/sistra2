package es.caib.sistramit.frontend.controller.asistente.debug;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistra2.commons.utils.ConstantesNumero;
import es.caib.sistramit.core.api.exception.WarningFrontException;
import es.caib.sistramit.core.api.model.comun.ResultadoProcesoProgramado;
import es.caib.sistramit.core.api.model.comun.types.TypeEntorno;
import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.model.system.EventoAuditoria;
import es.caib.sistramit.core.api.service.PurgaService;
import es.caib.sistramit.core.api.service.SecurityService;
import es.caib.sistramit.frontend.controller.TramitacionController;
import es.caib.sistramit.frontend.literales.LiteralesFront;
import es.caib.sistramit.frontend.model.DebugPurga;
import es.caib.sistramit.frontend.model.DebugSesionTramitacion;
import es.caib.sistramit.frontend.model.MensajeUsuario;
import es.caib.sistramit.frontend.model.RespuestaJSON;
import es.caib.sistramit.frontend.model.types.TypeRespuestaJSON;

/**
 * Controlador que permite el debug de una sesion de tramitación.
 *
 * @author Indra
 */
@Controller
@RequestMapping("/debug")
public final class DebugTramitacionController extends TramitacionController {

	/** Security service. */
	@Autowired
	SecurityService securityService;

	/** Literales front. **/
	@Autowired
	LiteralesFront literalesFront;

	/** Purga service. */
	@Autowired
	PurgaService purgaService;

	/**
	 * Carga pantalla debug.
	 *
	 * @return Pantalla debug.
	 */
	@RequestMapping("/logSesionTramitacion.html")
	public ModelAndView logSesionTramitacion(@RequestParam(value = "idioma", required = false) String idioma) {
		if (idioma == null || idioma.isEmpty()) {
			idioma = "ca";
		}

		final DebugPurga resultado = new DebugPurga();
		resultado.setIdioma(idioma);
		final Properties propsLiterales = literalesFront.getLiteralesSeccion("debug", idioma);
		resultado.setLiterales(propsLiterales);
		return new ModelAndView("debug/logSesionTramitacion", "datos", resultado);
	}

	/**
	 * Ejecuta la purga (y redirige a la pantalla debug para no meter mas codigo)
	 *
	 * @return Pantalla debug.
	 */
	@RequestMapping("/purga.html")
	public ModelAndView purga(@RequestParam(value = "idioma", required = false) String idioma) {
		if (idioma == null || idioma.isEmpty()) {
			idioma = "ca";
		}
		// Debug no permitido en produción
		final TypeEntorno entorno = this.getEntorno();
		if (entorno == TypeEntorno.PRODUCCION) {
			throw new WarningFrontException("Accés a DEBUG TRAMITACIÓ no permès per a producció");
		}

		final ResultadoProcesoProgramado r = purgaService.purgar();
		final DebugPurga resultado = new DebugPurga();
		resultado.setResultado(r);
		resultado.setIdioma(idioma);
		final Properties propsLiterales = literalesFront.getLiteralesSeccion("debug", idioma);
		resultado.setLiterales(propsLiterales);
		/*
		 * final Map<String, String> maps = new HashMap<>(); for (final Object fila :
		 * propsLiterales.keySet().toArray()) { maps.put(fila.toString(),
		 * propsLiterales.getProperty(fila.toString())); } resultado.setMaps(maps);
		 */
		// Redirigimos a carga asistente
		return new ModelAndView("debug/logPurga", "datos", resultado);

	}

	/**
	 * Carga log últimos eventos.
	 *
	 * @param minutos
	 *            Minutos antes de la hora actual a partir de la cual se recuperaran
	 *            los eventos.
	 * @return lista eventos
	 */
	@RequestMapping("/recuperarEventosSesionTramitacion.json")
	public ModelAndView logSesionTramitacion(@RequestParam("minutos") final long minutos) {

		final RespuestaJSON res = new RespuestaJSON();

		// Debug no permitido en produción
		final TypeEntorno entorno = this.getEntorno();
		if (entorno == TypeEntorno.PRODUCCION) {
			throw new WarningFrontException("Acceso a DEBUG TRAMITACION no permitido para producción");
		}

		// Obtiene sesion activa
		final String idSesionTramitacion = this.getIdSesionTramitacion();

		if (idSesionTramitacion != null) {
			// Obtiene detalle flujo
			final DetalleTramite detalleTramite = this.getFlujoTramitacionService()
					.obtenerDetalleTramite(idSesionTramitacion);
			// Calcula fecha a partir de la que se recuperan los eventos
			final Date fechaDesde = new Date(
					System.currentTimeMillis() - (minutos * ConstantesNumero.MILISEGUNDOSMINUTO));
			// Consulta eventos
			final List<EventoAuditoria> eventos = this.getSystemService()
					.recuperarLogSesionTramitacion(idSesionTramitacion, fechaDesde, null, false);
			// Devuelve datos debug
			final DebugSesionTramitacion datosDebug = new DebugSesionTramitacion();
			datosDebug.setIdSesionTramitacion(idSesionTramitacion);
			datosDebug.setIdTramite(detalleTramite.getTramite().getId());
			datosDebug.setEntorno(this.getEntorno().toString());
			datosDebug.setEventos(eventos);
			res.setDatos(datosDebug);
		} else {
			// No hay sesion tramitacion activa
			res.setEstado(TypeRespuestaJSON.ERROR);
			res.setMensaje(new MensajeUsuario("Atención", "No existe sesion de tramitacion"));
		}

		return generarJsonView(res);
	}

}
