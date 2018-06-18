package es.caib.sistrages.rest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.caib.sistrages.core.api.service.RestApiService;
import es.caib.sistrages.rest.api.AvisosEntidad;
import es.caib.sistrages.rest.api.ConfiguracionGlobal;
import es.caib.sistrages.rest.api.ConfiguracionEntidad;
import es.caib.sistrages.rest.api.ListaParametros;
import es.caib.sistrages.rest.api.ValoresDominio;
import es.caib.sistrages.rest.api.VersionTramite;
import es.caib.sistrages.rest.api.util.JsonException;
import es.caib.sistrages.rest.api.util.JsonUtil;
import es.caib.sistrages.rest.api.util.XTestJson;

/**
 * Operaciones requeridas desde el asistente de tramitación. No requieren
 * versionado.
 *
 * @author Indra
 *
 */
@RestController
@RequestMapping("/asistente")
public class AsistenteRestController {

    /** Servicio negocio. */
    @Autowired
    private RestApiService restApiService;

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
    public ConfiguracionGlobal obtenerConfiguracionGlobal() {
        restApiService.test("hola");
        return XTestJson.crearConfiguracionGlobal();
    }

    /**
     * Recupera configuración entidad.
     *
     * @param id
     *            id entidad
     * @return Entidad
     */
    @RequestMapping(value = "/entidad/{id}", method = RequestMethod.GET)
    public ConfiguracionEntidad obtenerConfiguracionEntidad(@PathVariable("id") String id) {
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
    public VersionTramite obtenerDefinicionVersionTramite(
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
    public AvisosEntidad obtenerAvisosEntidad(
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
    public ValoresDominio obtenerValoresDominioFD(
            @PathVariable("idDominio") String idDominio,
            @RequestParam(name = "parametros", required = false) String parametrosJSON) {

        // TODO Ver si gestionamos así los parametros compuestos JSON
        ListaParametros pars = null;
        if (StringUtils.isNotBlank(parametrosJSON)) {
            try {
                pars = (ListaParametros) JsonUtil.fromJson(parametrosJSON,
                        ListaParametros.class);
            } catch (final JsonException e) {
                // TODO Gestion excepciones
                throw new RuntimeException(e);
            }
        }

        return XTestJson.crearValoresDominio();
    }

}
