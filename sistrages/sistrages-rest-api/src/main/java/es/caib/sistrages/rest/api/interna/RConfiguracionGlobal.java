package es.caib.sistrages.rest.api.interna;

import java.util.List;

/**
 * Configuracion global.
 *
 * @author Indra
 *
 */
public class RConfiguracionGlobal {

    /** Propiedades. */
    private RListaParametros propiedades;

    /** Plugins. */
    private List<RPlugin> plugins;

    /**
     * Método de acceso a propiedades.
     *
     * @return propiedades
     */
    public RListaParametros getPropiedades() {
        return propiedades;
    }

    /**
     * Método para establecer propiedades.
     *
     * @param propiedades
     *            propiedades a establecer
     */
    public void setPropiedades(RListaParametros propiedades) {
        this.propiedades = propiedades;
    }

    /**
     * Método de acceso a plugins.
     *
     * @return plugins
     */
    public List<RPlugin> getPlugins() {
        return plugins;
    }

    /**
     * Método para establecer plugins.
     *
     * @param plugins
     *            plugins a establecer
     */
    public void setPlugins(List<RPlugin> plugins) {
        this.plugins = plugins;
    }

}
