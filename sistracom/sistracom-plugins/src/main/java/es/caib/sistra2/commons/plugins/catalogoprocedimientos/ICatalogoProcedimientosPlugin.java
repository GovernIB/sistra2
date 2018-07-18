package es.caib.sistra2.commons.plugins.catalogoprocedimientos;

import org.fundaciobit.plugins.IPlugin;

/**
 * Interface catálogo procedimientos.
 *
 * @author Indra
 *
 */
public interface ICatalogoProcedimientosPlugin extends IPlugin {

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
