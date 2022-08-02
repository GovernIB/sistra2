package es.caib.sistramit.frontend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistramit.core.api.model.flujo.Entidad;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.frontend.literales.LiteralesFront;

/**
 *
 * Controller página accesibilidad.
 *
 * @author Indra
 *
 */
@Controller
@RequestMapping(value = "/accesibilidad")
public class AccesibilidadController {

	/** Configuracion. */
	@Autowired
	private SystemService systemService;

	/** Literales. */
	@Autowired
	private LiteralesFront literalesFront;

	@RequestMapping(value = "/declaracion/{idioma}/{identificadorEntidad}.html", method = RequestMethod.GET)
	public ModelAndView accesibilidad(@PathVariable("idioma") final String idioma,
			@PathVariable("identificadorEntidad") final String identificadorEntidad) {
		// Datos entidad
		final Entidad entidad = systemService.obtenerInfoEntidad(identificadorEntidad, idioma);
		// Literales pagina
		final Map<String, String> literales = new HashMap<>();
		final Properties literalesProps = literalesFront.getLiteralesSeccion("accesibilidad", idioma);
		final Set<String> keys = literalesProps.stringPropertyNames();
		for (final String key : keys) {
			literales.put(key, literalesProps.getProperty(key));
		}

		// Modelo vista
		final Map<String, Object> model = new HashMap<>();
		model.put("idioma", idioma);
		model.put("entidad", entidad);
		model.put("literales", literales);

		return new ModelAndView("accesibilidad", model);
	}

}
