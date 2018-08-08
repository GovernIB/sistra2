package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Paso pagar.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPasoTramitacionPagar", description = "Descripcion de RPasoTramitacionPagar",parent = RPasoTramitacion.class)
public class RPasoTramitacionPagar extends RPasoTramitacion {

    /** Pagos. */
	@ApiModelProperty(value = "Pagos")
    private List<RPagoTramite> pagos;

    /**
     * Método de acceso a pagos.
     *
     * @return pagos
     */
    public List<RPagoTramite> getPagos() {
        return pagos;
    }

    /**
     * Método para establecer pagos.
     *
     * @param pagos
     *            pagos a establecer
     */
    public void setPagos(List<RPagoTramite> pagos) {
        this.pagos = pagos;
    }

}
