package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

/**
 * Ids paso anterior y siguiente.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class NavegacionPaso implements Serializable {

    /**
     * Id paso anterior.
     */
    private String idPasoAnterior;

    /**
     * Id paso siguiente.
     */
    private String idPasoSiguiente;

    /**
     * Método de acceso a idPasoAnterior.
     *
     * @return idPasoAnterior
     */
    public String getIdPasoAnterior() {
        return idPasoAnterior;
    }

    /**
     * Método para establecer idPasoAnterior.
     *
     * @param pIdPasoAnterior
     *            idPasoAnterior a establecer
     */
    public void setIdPasoAnterior(final String pIdPasoAnterior) {
        idPasoAnterior = pIdPasoAnterior;
    }

    /**
     * Método de acceso a idPasoSiguiente.
     *
     * @return idPasoSiguiente
     */
    public String getIdPasoSiguiente() {
        return idPasoSiguiente;
    }

    /**
     * Método para establecer idPasoSiguiente.
     *
     * @param pIdPasoSiguiente
     *            idPasoSiguiente a establecer
     */
    public void setIdPasoSiguiente(final String pIdPasoSiguiente) {
        idPasoSiguiente = pIdPasoSiguiente;
    }

}
