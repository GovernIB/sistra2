package es.caib.sistramit.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import springfox.documentation.annotations.ApiIgnore;

/**
 * Redireccion.
 *
 * @author Indra
 *
 */
@Controller
@ApiIgnore
public class IndexController {

	/**
	 * Redirige a swagger.
	 *
	 * @return Indice
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView obtenerConfiguracionGlobal() {
		return new ModelAndView("redirect:/rest/swagger-ui.html");
	}

}
