package es.caib.sistra2.commons.plugins.catalogoprocedimientos.api;

import java.text.ParseException;
import java.util.List;

import org.fundaciobit.pluginsib.core.IPlugin;

/**
 * Interface catálogo procedimientos.
 *
 * @author Indra
 *
 */
public interface ICatalogoProcedimientosPlugin extends IPlugin {

    /** Prefix. */
    public static final String CATALOGO_PROCEDIMIENTOS_BASE_PROPERTY = IPLUGINSIB_BASE_PROPERTIES
            + "catalogoprocedimientos.";

    /**
     * Recupera configuración trámite.
     *
     * @param idTramiteCP
     *            id trámite
     * @param idioma
     *            idioma
     * @throws ParseException
     */
    DefinicionTramiteCP obtenerDefinicionTramite(String idTramiteCP,
            String idioma) throws CatalogoPluginException;

    /**
     * Recupera procedimientos en los que se usa un trámite de Sistra.
     *
     * @param idTramite
     *            id trámite sistra
     * @param idioma
     *            idioma
     * @throws CatalogoPluginException
     */
    List<DefinicionProcedimientoCP> obtenerProcedimientosTramiteSistra(
            String idTramite, String idioma) throws CatalogoPluginException;

}
