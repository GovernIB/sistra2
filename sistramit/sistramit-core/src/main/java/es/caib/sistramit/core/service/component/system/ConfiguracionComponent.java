package es.caib.sistramit.core.service.component.system;

import org.fundaciobit.plugins.IPlugin;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePluginEntidad;
import es.caib.sistramit.core.api.model.system.types.TypePluginGlobal;
import es.caib.sistramit.core.api.model.system.types.TypePropiedadConfiguracion;
import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteSTG;

/**
 * Componente para acceder a configuracion.
 *
 * @author Indra
 *
 */
public interface ConfiguracionComponent {

    /**
     * Obtiene configuración.
     *
     * @param propiedad
     *            Propiedad configuración
     *
     * @return configuración
     */
    String obtenerPropiedadConfiguracion(TypePropiedadConfiguracion propiedad);

    /**
     * Recupera definición versión de trámite.
     *
     * @param idTramite
     *            id trámite
     * @param version
     *            versión
     * @param idioma
     *            idioma
     * @return Definición versión trámite
     */
    DefinicionTramiteSTG recuperarDefinicionTramite(String idTramite,
            int version, String idioma);

    /**
     * Recupera configuracion entidad.
     *
     * @param idEntidad
     * @return configuracion entidad.
     */
    RConfiguracionEntidad obtenerConfiguracionEntidad(String idEntidad);

    /**
     * Recupera avisos entidad.
     *
     * @param idEntidad
     * @return configuracion entidad.
     */
    RAvisosEntidad obtenerAvisosEntidad(String idEntidad);

    /**
     * Obtiene tipo plugin global.
     *
     * @param tipoPlugin
     *            tipo plugin
     * @return Plugin
     */
    IPlugin obtenerPluginGlobal(TypePluginGlobal tipoPlugin);

    /**
     * Obtiene tipo plugin entidad.
     *
     * @param tipoPlugin
     *            tipo plugin
     * @return Plugin
     */
    IPlugin obtenerPluginEntidad(TypePluginEntidad tipoPlugin,
            String idEntidad);

}