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
@RequestMapping("/interna")
@Api(value = "interna", produces = "application/json")
public class ApiInternaRestController {

    /** Servicio negocio. */
    @Autowired
    private RestApiInternaService restApiService;

    // TODO Hacer que todas las clases del modelo api rest empiecen con "R" para
    // evitar conflictos con modelo core? RENOMBRAMOS QUE EMPIECEN CON "R"

    // TODO Ver gestion errores, tanto generados en capa Rest como los que
    // vengan de negocio. Ver de gestionar con interceptor. DE MOMENTO QUE
    // DEVUELVA 500 Y LUEGO YA VEMOS.

    /**
     * Recupera configuración global.
     *
     * @return Configuracion global
     */

    @ApiOperation(value = "Lista de Propiedades de configuracion global", notes = "Lista de Propiedades de configuracion global", response = RConfiguracionGlobal.class)
    @RequestMapping(value = "/configuracionGlobal", method = RequestMethod.GET)
    public RConfiguracionGlobal obtenerConfiguracionGlobal() {
        final List<ConfiguracionGlobal> cg = restApiService
                .listConfiguracionGlobal(null);
        final List<Plugin> pg = restApiService.listPlugin(TypeAmbito.GLOBAL,
                (long) 0, null);
        final RConfiguracionGlobalAdapter adapter = new RConfiguracionGlobalAdapter(
                cg, pg);
        return adapter.getrConfiguracionGlobal();
    }

    /**
     * Recupera configuración entidad.
     *
     * @param id
     *            id entidad
     * @return Entidad
     */
    @RequestMapping(value = "/entidad/{id}", method = RequestMethod.GET)
    public RConfiguracionEntidad obtenerConfiguracionEntidad(
            @PathVariable("id") String id) {
        final Entidad entidad = restApiService.loadEntidad(Long.parseLong(id));
        final RConfiguracionEntidadAdapter adapter = new RConfiguracionEntidadAdapter(
                entidad, restApiService);
        return adapter.getrConfiguracionEntidad();
    }

    /**
     * Recupera definición versión de trámite.
     *
     * @param idioma
     *            Idioma
     * @param idtramite
     *            Id Trámite
     * @param version
     *            Versión trámite
     * @return versión de trámite
     * @throws Exception
     */
    @RequestMapping(value = "/tramite/{idTramite}/{version}/{idioma}", method = RequestMethod.GET)
    public RVersionTramite obtenerDefinicionVersionTramite(
            @PathVariable("idTramite") String idtramite,
            @PathVariable("version") int version,
            @PathVariable("idioma") String idioma) throws Exception {

        final String idiomaDefecto = restApiService.getValorConfiguracionGlobal(
                "definicionTramite.lenguajeDefecto");
        final TramiteVersion tv = restApiService.loadTramiteVersion(idtramite,
                version);
        if (tv == null) {
            throw new Exception("El tramite especificado no existe");
        }
        final RVersionTramiteAdapter adapter = new RVersionTramiteAdapter(tv,
                idioma, idiomaDefecto, restApiService);
        return adapter.getrVersionTramite();
    }

    /**
     * Obtiene avisos activos entidad.
     *
     * @param idEntidad
     *            Id entidad
     * @return avisos
     */
    @RequestMapping(value = "/entidad/{id}/avisos", method = RequestMethod.GET)
    public RAvisosEntidad obtenerAvisosEntidad(
            @PathVariable("id") String idEntidad) {
        final RAvisosEntidadAdapter aE = new RAvisosEntidadAdapter(
                restApiService, idEntidad);
        return aE.getrAvisosEntidad();
    }

    /**
     * Recupera valores de un dominio de fuente de datos.
     *
     * @param idDominio
     *            id dominio
     * @param parametrosJSON
     *            parametros (en formato JSON)
     * @return Valores dominio
     */
    @RequestMapping(value = "/dominioFuenteDatos/{idDominio}", method = RequestMethod.POST)
    public RValoresDominio obtenerValoresDominioFD(
            @PathVariable("idDominio") String idDominio,
            @RequestParam(name = "parametros", required = false) String parametrosJSON) {

        // TODO Ver si gestionamos así los parametros compuestos JSON
        RListaParametros pars = null;
        if (StringUtils.isNotBlank(parametrosJSON)) {
            try {
                pars = (RListaParametros) JsonUtil.fromJson(parametrosJSON,
                        RListaParametros.class);
            } catch (final JsonException e) {
                // TODO Gestion excepciones
                throw new RuntimeException(e);
            }
        }

        // Convertimos los parametros a la clase necesaria
        final List<ValorParametroDominio> listaParams = new ArrayList<ValorParametroDominio>();
        if (pars != null && pars.getParametros() != null) {
            for (final RValorParametro p : pars.getParametros()) {
                final ValorParametroDominio param = new ValorParametroDominio();
                param.setCodigo(p.getCodigo());
                param.setValor(p.getValor());
                listaParams.add(param);
            }
        }

        final ValoresDominio res = restApiService
                .realizarConsultaFuenteDatos(idDominio, listaParams);
        final RValoresDominioAdapter adapter = new RValoresDominioAdapter(res);
        return adapter.getrValoresDominio();
    }

}
