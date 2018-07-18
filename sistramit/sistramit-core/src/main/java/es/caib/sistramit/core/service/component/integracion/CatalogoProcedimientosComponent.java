package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistra2.commons.plugins.catalogoprocedimientos.DefinicionTramiteCP;

/**
 * Acceso a componente Catálogo de procedimientos.
 *
 * @author Indra
 *
 */
public interface CatalogoProcedimientosComponent {

    /**
     * Recupera configuración trámite.
     *
     * @param idEntidad
     *            id entidad
     *
     * @param idTramiteCP
     *            id trámite
     * @param idioma
     *            idioma
     */
    DefinicionTramiteCP obtenerDefinicionTramite(String idEntidad,
            String idTramiteCP, String idioma);

}
