package es.caib.sistra2.stg.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import es.caib.sistra2.stg.core.api.model.TestData;
import es.caib.sistra2.stg.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistra2.stg.core.api.service.TestService;

@Path("ejemploRest")
public class EjemploRest {

	@Inject
	TestService testService;

	@GET
	@Path("recuperaDato")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(ConstantesRolesAcceso.SUPER_ADMIN)
	public List<TestData> recuperaDato() {
		return testService.list("");
	}

	@GET
	@Path("recuperaDato2")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(ConstantesRolesAcceso.SUPER_ADMIN)
	public EntityExample recuperaDato2(@QueryParam("param1") final String param1,
			@QueryParam("param2") final String param2) {
		final EntityExample e = new EntityExample();
		e.setDato1(param1);
		e.setDato2(param2);
		return e;
	}

	@GET
	@Path("recuperaDato3")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(ConstantesRolesAcceso.SUPER_ADMIN)
	public String recuperaDato3(@Context final UriInfo uriInfo) {
		return uriInfo.getQueryParameters().getFirst("param1") + " - "
				+ uriInfo.getQueryParameters().getFirst("param2");
	}

	@GET
	@Path("recuperaDato4")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(ConstantesRolesAcceso.SUPER_ADMIN)
	public EntityExample recuperaDato4(@QueryParam("entity") final EntityExample entity,
			@QueryParam("entity2") final EntityExample entity2) {

		final EntityExample e1 = (entity != null ? entity : new EntityExample());
		final EntityExample e2 = (entity2 != null ? entity2 : new EntityExample());

		final EntityExample e = new EntityExample();
		e.setDato1(e1.getDato1() + " - " + e2.getDato1());
		e.setDato2(e1.getDato2() + " - " + e2.getDato2());

		return e;
	}

}
