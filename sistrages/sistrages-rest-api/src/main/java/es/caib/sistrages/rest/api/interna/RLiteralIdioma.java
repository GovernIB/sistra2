package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Literal idioma.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RLiteralIdioma", description = "Descripcion de RLiteralIdioma")
public class RLiteralIdioma {

    /**
     * Idioma.
     */
	@ApiModelProperty(value = "Idioma")
    private String idioma;

    /**
     * Descripción.
     */
	@ApiModelProperty(value = "Descripción")
    private String descripcion;

    /**
     * Método de acceso a idioma.
     *
     * @return idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Método para establecer idioma.
     *
     * @param idioma
     *            idioma a establecer
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Método de acceso a descripcion.
     *
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método para establecer descripcion.
     *
     * @param descripcion
     *            descripcion a establecer
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
