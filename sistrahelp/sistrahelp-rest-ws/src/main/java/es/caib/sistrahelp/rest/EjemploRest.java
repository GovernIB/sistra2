package es.caib.sistrahelp.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.caib.sistrahelp.core.api.model.EjemploDto;
import es.caib.sistrahelp.core.api.service.EjemploService;

@Path("/ejemploRest")
public class EjemploRest {

	@Autowired
	EjemploService ejemploService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public EjemploDto recuperaDato() {
		return ejemploService.recuperaDato();
	}
}
