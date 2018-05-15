package es.caib.sistramit.frontend.controller.asistente;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.core.api.model.flujo.DetalleTramite;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;
import es.caib.sistramit.frontend.ModuleConfig;
import es.caib.sistramit.frontend.SesionHttp;

@Controller
@RequestMapping(value = "/asistente")
public class AsistenteTramitacionController {

	@Autowired
	FlujoTramitacionService flujoTramitacionService;

	@Autowired
	SesionHttp sesionHttp;

	/** Url redireccion asistente. */
	private static final String URL_REDIRIGIR_ASISTENTE = "asistente/redirigirAsistente";

	@RequestMapping(value = "/iniciarTramite.html")
	public ModelAndView iniciarTramite(@RequestParam("tramite") final String tramite,
			@RequestParam("version") final int version, @RequestParam("idioma") final String idioma,
			@RequestParam("procedimiento") final String procedimiento,
			@RequestParam(value = "parametros", required = false) final String parametros,
			final HttpServletRequest request) {

		// Url inicio
		final String urlInicio = ModuleConfig.urlSistramit + "/asistente/iniciarTramite.html?"
				+ request.getQueryString();

		// Parametros inicio
		// TODO Pendiente (como json?)
		final Map<String, String> parametrosInicio = new HashMap<>();

		// Inicia flujo tramitacion y almacena en la sesion
		final String idSesionTramitacion = flujoTramitacionService.iniciarTramite(tramite, version, idioma,
				procedimiento, urlInicio, parametrosInicio);
		sesionHttp.setIdSesionTramitacion(idSesionTramitacion);

		final ModelAndView mav = new ModelAndView(URL_REDIRIGIR_ASISTENTE);
		return mav;
	}

	@RequestMapping(value = "/asistente.html")
	public ModelAndView asistente() {

		// TODO Pasar a clase padre
		final String idSesionTramitacion = sesionHttp.getIdSesionTramitacion();
		final DetalleTramite dt = flujoTramitacionService.obtenerDetalleTramite(idSesionTramitacion);

		final ModelAndView mav = new ModelAndView("asistente/asistente");
		return mav;
	}

}
