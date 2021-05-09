package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Configuracion global.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RConfiguracionGlobal", description = "Descripcion de RConfiguracionGlobal")
public class RConfiguracionGlobal {

    /** Timestamp recuperacion. */
	@ApiModelProperty(value = "Timestamp recuperacion")
    private String timestamp;

    /** Propiedades. */
	@ApiModelProperty(value = "Propiedades")
    private RListaParametros propiedades;

    /** Plugins. */
	@ApiModelProperty(value = "Plugins")
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

    /**
     * Método de acceso a timestamp.
     *
     * @return timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Método para establecer timestamp.
     *
     * @param timestamp
     *            timestamp a establecer
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
