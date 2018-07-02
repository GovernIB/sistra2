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
import es.caib.sistrages.core.api.model.Plugin;
import es.caib.sistrages.core.api.model.comun.Propiedad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.service.RestApiInternaService;
import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RListaParametros;
import es.caib.sistrages.rest.api.interna.RPlugin;
import es.caib.sistrages.rest.api.interna.RValorParametro;
import es.caib.sistrages.rest.api.interna.RValoresDominio;
import es.caib.sistrages.rest.api.interna.RVersionTramite;
import es.caib.sistrages.rest.api.util.JsonException;
import es.caib.sistrages.rest.api.util.JsonUtil;
import es.caib.sistrages.rest.api.util.XTestJson;

/**
 * Operaciones requeridas desde el resto de módulos de Sistra2. No requieren
 * versionado.
 *
 * @author Indra
 *
 */
@RestController
@RequestMapping("/interna")
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
    @RequestMapping(value = "/configuracionGlobal", method = RequestMethod.GET)
    public RConfiguracionGlobal obtenerConfiguracionGlobal() {
        List <ConfiguracionGlobal> cg= restApiService.listConfiguracionGlobal(null);
        List <Plugin> pg= restApiService.listPlugin(TypeAmbito.GLOBAL, (long) 0, null);


        final RConfiguracionGlobal c = new RConfiguracionGlobal();
        c.setPropiedades(toListaParametrosCG(cg));
        c.setPlugins(crearPlugins(pg));
        return c;

    }

    private static RListaParametros toListaParametrosCG(List <ConfiguracionGlobal> cg) {
        final RListaParametros params = new RListaParametros();
        params.setParametros(new ArrayList<>());
        for (ConfiguracionGlobal c : cg) {
            final RValorParametro p = new RValorParametro();
            p.setCodigo(c.getCodigo()+"");
            p.setValor(c.getValor());
            params.getParametros().add(p);
        }
        return params;
    }

    private static RListaParametros toListaParametrosP(List <Propiedad> lp) {
        final RListaParametros params = new RListaParametros();
        params.setParametros(new ArrayList<>());
        for (Propiedad p : lp) {
            final RValorParametro vp = new RValorParametro();
            vp.setCodigo(p.getCodigo()+"");
            vp.setValor(p.getValor());
            params.getParametros().add(vp);
        }
        return params;
    }

    private static List<RPlugin> crearPlugins(List<Plugin> lpg) {
        final List<RPlugin> plugins = new ArrayList<>();

        for(Plugin p : lpg) {
	        RPlugin plugin;
	        plugin = new RPlugin();
	        plugin.setTipo(p.getTipo().name());
	        plugin.setClassname(p.getClassname());
	        plugin.setParametros(toListaParametrosP(p.getPropiedades()));
	        plugins.add(plugin);
        }
        return plugins;
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
        return XTestJson.crearEntidad();
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
     */
    @RequestMapping(value = "/tramite/{idTramite}/{version}/{idioma}", method = RequestMethod.GET)
    public RVersionTramite obtenerDefinicionVersionTramite(
            @PathVariable("idTramite") String idtramite,
            @PathVariable("version") int version,
            @PathVariable("idioma") String idioma) {
        return XTestJson.crearVersionTramite();
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
        return XTestJson.crearAvisos();
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

        return XTestJson.crearValoresDominio();
    }

}
