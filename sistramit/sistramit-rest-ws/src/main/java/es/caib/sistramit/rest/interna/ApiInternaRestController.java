package es.caib.sistramit.rest.interna;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.caib.sistramit.core.api.model.system.Invalidacion;
import es.caib.sistramit.core.api.model.system.types.TypeInvalidacion;
import es.caib.sistramit.core.api.service.SystemService;
import es.caib.sistramit.rest.api.interna.RInvalidacion;
import es.caib.sistramit.rest.api.util.JsonException;
import es.caib.sistramit.rest.api.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Operaciones requeridas desde el resto de módulos de Sistra2. No requieren
 * versionado.
 *
 * @author Indra
 *
 */
@RestController
@RequestMapping("/interna")
@Api(value = "interna", produces = "application/json")
public class ApiInternaRestController {

    /** Service system. */
    @Autowired
    private SystemService systemService;

    /**
     * Invalidación.
     *
     */
    @ApiOperation(value = "Invalidación caché", notes = "Invalidación caché")
    @RequestMapping(value = "/invalidacion", method = RequestMethod.POST)
    public boolean invalidacion(
            @ApiParam("{\"tipo\":\"tipo\",\"identificador\":\"id\"}") @RequestParam(name = "invalidacion") String invalidacionJSON) {

        // Parseamos parametro enviado por POST
        RInvalidacion pars = null;
        if (StringUtils.isNotBlank(invalidacionJSON)) {
            try {
                pars = (RInvalidacion) JsonUtil.fromJson(invalidacionJSON,
                        RInvalidacion.class);
            } catch (final JsonException e) {
                throw new RuntimeException(e);
            }
        }

        // Añade invalidación
        final Invalidacion invalidacion = new Invalidacion();
        invalidacion.setTipo(TypeInvalidacion.fromString(pars.getTipo()));
        invalidacion.setIdentificador(pars.getIdentificador());
        systemService.invalidar(invalidacion);

        return true;

    }

}
