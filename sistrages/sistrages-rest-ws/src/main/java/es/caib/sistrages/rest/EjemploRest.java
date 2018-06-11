package es.caib.sistrages.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import es.caib.sistrages.core.api.model.comun.ConstantesRolesAcceso;
import es.caib.sistrages.core.api.service.RestApiService;

@Path("ejemploRest")
public class EjemploRest {

    @Autowired
    private RestApiService restApiService;

    @GET
    @Path("recuperaDato")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(ConstantesRolesAcceso.REST)
    public String recuperaDato() {
        return restApiService.test("hola");
    }

    @GET
    @Path("recuperaDato2")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(ConstantesRolesAcceso.REST)
    public EntityExample recuperaDato2(
            @QueryParam("param1") final String param1,
            @QueryParam("param2") final String param2) {
        final EntityExample e = new EntityExample();
        e.setDato1(param1);
        e.setDato2(param2);
        return e;
    }

    @GET
    @Path("recuperaDato3")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(ConstantesRolesAcceso.REST)
    public String recuperaDato3(@Context final UriInfo uriInfo) {
        return uriInfo.getQueryParameters().getFirst("param1") + " - "
                + uriInfo.getQueryParameters().getFirst("param2");
    }

    @GET
    @Path("recuperaDato4")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(ConstantesRolesAcceso.REST)
    public EntityExample recuperaDato4(
            @QueryParam("entity") final EntityExample entity,
            @QueryParam("entity2") final EntityExample entity2) {

        final EntityExample e1 = (entity != null ? entity
                : new EntityExample());
        final EntityExample e2 = (entity2 != null ? entity2
                : new EntityExample());

        final EntityExample e = new EntityExample();
        e.setDato1(e1.getDato1() + " -- " + e2.getDato1());
        e.setDato2(e1.getDato2() + " -- " + e2.getDato2());

        return e;
    }

    @POST
    @Path("recuperaDato5")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(ConstantesRolesAcceso.REST)
    public EntityExample recuperaDato5(final EntityExample entity) {

        final EntityExample e1 = (entity != null ? entity
                : new EntityExample());

        final EntityExample e = new EntityExample();
        e.setDato1(e1.getDato1());
        e.setDato2(e1.getDato2());

        return e;
    }

    @POST
    @Path("recuperaDato6b")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(ConstantesRolesAcceso.REST)
    public RespuestaServerExample recuperaDato6BAckup(
            @FormParam("entity") final EntityExample entity,
            @FormParam("entity2") final EntityExample entity2) {

        final EntityExample e1 = (entity != null ? entity
                : new EntityExample());
        final EntityExample e2 = (entity2 != null ? entity2
                : new EntityExample());

        final List<EntityExample> r = new ArrayList<EntityExample>();
        r.add(e1);
        r.add(e2);

        final RespuestaServerExample res = new RespuestaServerExample();
        final Status s = new Status();
        s.setStatusCode("6");
        s.setMessage("sss");
        res.setStatus(s);
        res.setResult(r);

        return res;

    }

    @POST
    @Path("recuperaDato6")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(ConstantesRolesAcceso.REST)
    public Response recuperaDato6(
            @FormParam("entity") final EntityExample entity,
            @FormParam("entity2") final EntityExample entity2) {

        final EntityExample e1 = (entity != null ? entity
                : new EntityExample());
        final EntityExample e2 = (entity2 != null ? entity2
                : new EntityExample());

        final List<EntityExample> l = new ArrayList<EntityExample>();
        l.add(e1);
        l.add(e2);

        final EntityExample[] r = new EntityExample[2];
        r[0] = e1;
        r[1] = e2;

        return Response.ok(l).build();

    }

    @POST
    @Path("recuperaDato7")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(ConstantesRolesAcceso.REST)
    public RespuestaServerExample recuperaDato7(
            @FormParam("entity") final EntityExample entity,
            @FormParam("entity2") final EntityExample entity2) {

        final EntityExample e1 = (entity != null ? entity
                : new EntityExample());
        final EntityExample e2 = (entity2 != null ? entity2
                : new EntityExample());

        final List<EntityExample> l = new ArrayList<EntityExample>();
        l.add(e1);
        l.add(e2);

        final RespuestaServerExample res = new RespuestaServerExample();
        final Status s = new Status();
        s.setStatusCode("6");
        s.setMessage("sss");
        res.setStatus(s);
        res.setResult(l);

        return res;

    }

}
