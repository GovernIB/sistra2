package es.caib.sistra2.ate.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import es.caib.sistra2.ate.core.api.model.EjemploDto;
import es.caib.sistra2.ate.core.api.service.EjemploService;

@Controller
public class EjemploController {

	@Autowired
	private EjemploService ejemploService;

	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	public ModelAndView recuperaDato() {
		ModelAndView mav = null;

		EjemploDto dato = ejemploService.recuperaDato();

		mav = new ModelAndView("inicio");
		mav.addObject("nombre", dato.getNombre());

		return mav;
	}

}
