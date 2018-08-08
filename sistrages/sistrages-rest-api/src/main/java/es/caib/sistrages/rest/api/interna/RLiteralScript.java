package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Mensajes de literales usados en script.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RLiteralScript", description = "Descripcion de RLiteralScript")
public class RLiteralScript {

    /** Identificador. */
	@ApiModelProperty(value = "Identificador")
    private String identificador;

    /** Literal. */
	@ApiModelProperty(value = "Literal")
    private String literal;

    /**
     * Método de acceso a identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Método para establecer identificador.
     *
     * @param identificador
     *            identificador a establecer
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Método de acceso a literal.
     *
     * @return literal
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Método para establecer literal.
     *
     * @param literal
     *            literal a establecer
     */
    public void setLiteral(String literal) {
        this.literal = literal;
    }

}
