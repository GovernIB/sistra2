package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistrages.rest.api.interna.RAvisosEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionEntidad;
import es.caib.sistrages.rest.api.interna.RConfiguracionGlobal;
import es.caib.sistrages.rest.api.interna.RVersionTramite;

/**
 * Acceso a componente SISTRAGES.
 *
 * @author Indra
 *
 */
public interface SistragesApiComponent {

    /**
     * Recupera configuración global.
     */
    RConfiguracionGlobal obtenerConfiguracionGlobal();

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
    RVersionTramite recuperarDefinicionTramite(String idTramite, int version,
            String idioma);

}
