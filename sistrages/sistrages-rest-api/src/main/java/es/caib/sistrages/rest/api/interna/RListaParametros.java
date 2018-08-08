package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Lista parámetros.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RListaParametros", description = "Descripcion de RListaParametros")
public class RListaParametros {

    /** Lista parametros. */
	@ApiModelProperty(value = "Lista parametros")
    private List<RValorParametro> parametros;

    /**
     * Método de acceso a parametros.
     *
     * @return parametros
     */
    public List<RValorParametro> getParametros() {
        return parametros;
    }

    /**
     * Método para establecer parametros.
     *
     * @param parametros
     *            parametros a establecer
     */
    public void setParametros(List<RValorParametro> parametros) {
        this.parametros = parametros;
    }

}
