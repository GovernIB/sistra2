package es.caib.sistrages.rest.api;

import java.util.List;

/**
 * Configuracion global.
 *
 * @author Indra
 *
 */
public class ConfiguracionGlobal {

    /** Propiedades. */
    private ListaParametros propiedades;

    /** Plugins. */
    private List<Plugin> plugins;

    /**
     * Método de acceso a propiedades.
     * 
     * @return propiedades
     */
    public ListaParametros getPropiedades() {
        return propiedades;
    }

    /**
     * Método para establecer propiedades.
     * 
     * @param propiedades
     *            propiedades a establecer
     */
    public void setPropiedades(ListaParametros propiedades) {
        this.propiedades = propiedades;
    }

    /**
     * Método de acceso a plugins.
     * 
     * @return plugins
     */
    public List<Plugin> getPlugins() {
        return plugins;
    }

    /**
     * Método para establecer plugins.
     * 
     * @param plugins
     *            plugins a establecer
     */
    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

}
