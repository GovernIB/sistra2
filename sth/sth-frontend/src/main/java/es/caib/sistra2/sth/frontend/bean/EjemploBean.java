package es.caib.sistra2.sth.frontend.bean;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import es.caib.sistra2.sth.core.api.model.EjemploDto;
import es.caib.sistra2.sth.core.api.service.EjemploService;

@Named
public class EjemploBean {

	@Autowired
	private EjemploService ejemploService;

	public EjemploDto recuperaDato() {
		return ejemploService.recuperaDato();
	}

}


