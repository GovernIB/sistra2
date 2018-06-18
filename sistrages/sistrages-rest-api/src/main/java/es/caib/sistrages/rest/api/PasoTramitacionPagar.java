package es.caib.sistrages.rest.api;

import java.util.List;

/**
 * Paso pagar.
 *
 * @author Indra
 *
 */
public class PasoTramitacionPagar extends PasoTramitacion {

    /** Pagos. */
    private List<PagoTramite> pagos;

    /**
     * Método de acceso a pagos.
     * 
     * @return pagos
     */
    public List<PagoTramite> getPagos() {
        return pagos;
    }

    /**
     * Método para establecer pagos.
     * 
     * @param pagos
     *            pagos a establecer
     */
    public void setPagos(List<PagoTramite> pagos) {
        this.pagos = pagos;
    }

}
