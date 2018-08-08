package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Literal.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RLiteral", description = "Descripcion de RLiteral")
public class RLiteral {

    /** Idiomas. */
	@ApiModelProperty(value = "Idiomas")
    private List<RLiteralIdioma> literales;

    /**
     * Método de acceso a literales.
     *
     * @return literales
     */
    public List<RLiteralIdioma> getLiterales() {
        return literales;
    }

    /**
     * Método para establecer literales.
     *
     * @param literales
     *            literales a establecer
     */
    public void setLiterales(List<RLiteralIdioma> literales) {
        this.literales = literales;
    }

}
