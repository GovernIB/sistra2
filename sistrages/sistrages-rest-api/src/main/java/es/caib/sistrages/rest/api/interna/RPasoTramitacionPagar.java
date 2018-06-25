package es.caib.sistrages.rest.api.interna;

import java.util.List;

/**
 * Paso pagar.
 *
 * @author Indra
 *
 */
public class RPasoTramitacionPagar extends RPasoTramitacion {

    /** Pagos. */
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
