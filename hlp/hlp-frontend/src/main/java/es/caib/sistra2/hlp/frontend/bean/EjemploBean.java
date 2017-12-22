package es.caib.sistra2.hlp.frontend.bean;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import es.caib.sistra2.hlp.core.api.model.EjemploDto;
import es.caib.sistra2.hlp.core.api.service.EjemploService;

@Named
public class EjemploBean {

	@Autowired
	private EjemploService ejemploService;

	public EjemploDto recuperaDato() {
		return ejemploService.recuperaDato();
	}

}


