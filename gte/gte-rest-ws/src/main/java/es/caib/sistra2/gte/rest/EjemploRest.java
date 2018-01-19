package es.caib.sistra2.gte.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.caib.sistra2.gte.core.api.model.TestData;
import es.caib.sistra2.gte.core.api.service.TestService;

@Path("/ejemploRest")
public class EjemploRest {

	@Autowired
	TestService testService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("STR2_TEST")
	public List<TestData> recuperaDato() {
		return testService.list("");
	}
}
