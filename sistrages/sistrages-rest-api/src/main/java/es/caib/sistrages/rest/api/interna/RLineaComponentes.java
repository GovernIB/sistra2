package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Línea de componentes.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RLineaComponentes", description = "Descripcion de RLineaComponentes")
public class RLineaComponentes {

    /** Componentes. */
	@ApiModelProperty(value = "Componentes")
    private List<RComponente> componentes;

    /**
     * Método de acceso a componentes.
     *
     * @return componentes
     */
    public List<RComponente> getComponentes() {
        return componentes;
    }

    /**
     * Método para establecer componentes.
     *
     * @param componentes
     *            componentes a establecer
     */
    public void setComponentes(List<RComponente> componentes) {
        this.componentes = componentes;
    }

}
