package es.caib.sistramit.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.api.service.SystemService;

@Path("/ejemploRest")
public class EjemploRest {

    @Autowired
    private SystemService systemService;

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("STT_API")
    public String test() {
        return systemService.obtenerPropiedadConfiguracion(
                TypePropiedadConfiguracion.ENTORNO);
    }

}
