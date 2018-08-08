package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Script.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RScript", description = "Descripcion de RScript")
public class RScript {

    /** Script. */
	@ApiModelProperty(value = "Script")
    private String script;

    /** Literales. */
	@ApiModelProperty(value = "Literales")
    private List<RLiteralScript> literales;

    /**
     * Método de acceso a script.
     *
     * @return script
     */
    public String getScript() {
        return script;
    }

    /**
     * Método para establecer script.
     *
     * @param script
     *            script a establecer
     */
    public void setScript(String script) {
        this.script = script;
    }

    /**
     * Método de acceso a literales.
     *
     * @return literales
     */
    public List<RLiteralScript> getLiterales() {
        return literales;
    }

    /**
     * Método para establecer literales.
     *
     * @param literales
     *            literales a establecer
     */
    public void setLiterales(List<RLiteralScript> literales) {
        this.literales = literales;
    }

}
