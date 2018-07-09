package es.caib.sistramit.core.service.component.integracion;

import es.caib.sistramit.core.service.model.integracion.DefinicionTramiteCP;

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
     * @param idTramiteCP
     *            id trámite
     * @param idioma
     *            idioma
     */
    DefinicionTramiteCP obtenerDefinicionTramite(String idTramiteCP,
            String idioma);

}
