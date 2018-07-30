package es.caib.sistrages.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.ValoresDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.rest.adapter.RAvisosEntidadAdapter;
import es.caib.sistrages.rest.adapter.RConfiguracionEntidadAdapter;
import es.caib.sistrages.rest.adapter.RConfiguracionGlobalAdapter;
import es.caib.sistrages.rest.adapter.RValoresDominioAdapter;
import es.caib.sistrages.rest.adapter.RVersionTramiteAdapter;
import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistrages.rest.api.util.JsonException;
import es.caib.sistrages.rest.api.util.JsonUtil;
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
        final RConfiguracionGlobalAdapter adapter = new RConfiguracionGlobalAdapter(
                cg, pg);
        return adapter.getrConfiguracionGlobal();
    }

}
