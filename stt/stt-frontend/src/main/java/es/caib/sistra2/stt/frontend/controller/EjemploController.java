package es.caib.sistra2.stt.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistra2.stt.core.api.model.EjemploDto;
import es.caib.sistra2.stt.core.api.service.EjemploService;

@Controller
public class EjemploController {

	@Autowired
	private EjemploService ejemploService;

	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	public ModelAndView recuperaDato() {
		ModelAndView mav = null;

		EjemploDto dato = ejemploService.recuperaDato();

		mav = new ModelAndView("inicio");

		// Obtenemos tb usuario y role en la parte web
		mav.addObject("nombre", dato.getNombre() + " - WEB: usuario " + SecurityUtils.getUserName() + " role: " + SecurityUtils.hasRole("STR2_TEST") );

		return mav;
	}

}
