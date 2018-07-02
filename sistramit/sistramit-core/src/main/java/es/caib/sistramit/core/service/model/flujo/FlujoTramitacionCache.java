package es.caib.sistramit.core.service.model.flujo;

import java.util.Date;

import es.caib.sistramit.core.service.component.flujo.FlujoTramitacionComponent;

/**
 * Cacheo de flujo de tramitación.
 *
 * @author Indra
 *
 */
public class FlujoTramitacionCache {

    /** Flujo de tramitacion. */
    private FlujoTramitacionComponent flujoTramitacion;

    /** Ultimo acceso. */
    private Date fcUltimoAcceso;

    /**
     * Método de acceso a flujoTramitacion.
     * 
     * @return flujoTramitacion
     */
    public FlujoTramitacionComponent getFlujoTramitacion() {
        return flujoTramitacion;
    }

    /**
     * Método para establecer flujoTramitacion.
     * 
     * @param flujoTramitacion
     *            flujoTramitacion a establecer
     */
    public void setFlujoTramitacion(
            FlujoTramitacionComponent flujoTramitacion) {
        this.flujoTramitacion = flujoTramitacion;
    }

    /**
     * Método de acceso a fcUltimoAcceso.
     * 
     * @return fcUltimoAcceso
     */
    public Date getFcUltimoAcceso() {
        return fcUltimoAcceso;
    }

    /**
     * Método para establecer fcUltimoAcceso.
     * 
     * @param fcUltimoAcceso
     *            fcUltimoAcceso a establecer
     */
    public void setFcUltimoAcceso(Date fcUltimoAcceso) {
        this.fcUltimoAcceso = fcUltimoAcceso;
    }

}
