package es.caib.sistrages.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.rest.adapter.ConfiguracionGlobalAdapter;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Operaciones requeridas desde el resto de módulos de Sistra2. No requieren
 * versionado.
 *
 * @author Indra
 *
 */
@RestController
@RequestMapping("/externa")
@Api(value = "externa", produces = "application/json")
public class ApiExternaRestController {

    /** Servicio negocio. */
    @Autowired
    private RestApiInternaService restApiService;

    /**
     * conf Global Adapter
     */
    @Autowired
    private ConfiguracionGlobalAdapter confGlobalAdapter;

    // TODO Ver gestion errores, tanto generados en capa Rest como los que
    // vengan de negocio. Ver de gestionar con interceptor. DE MOMENTO QUE
    // DEVUELVA 500 Y LUEGO YA VEMOS.

    /**
     * Recupera configuración global.
     *
     * @return Configuracion global
     */

    @ApiOperation(value = "Lista de Propiedades de configuracion global", notes = "Lista de Propiedades de configuracion global", response = RConfiguracionGlobal.class)
    @RequestMapping(value = "/testExterna", method = RequestMethod.GET)
    public RConfiguracionGlobal pruebaRetornaConfGlobal() {
        final List<ConfiguracionGlobal> cg = restApiService
                .listConfiguracionGlobal(null);
        final List<Plugin> pg = restApiService.listPlugin(TypeAmbito.GLOBAL,
                (long) 0, null);
        return confGlobalAdapter.convertir(cg, pg);
    }

}
