package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;

/**
 *
 * Respuesta a la accion de ir a un paso. Indica cual es el paso actual y si al
 * ir a ese paso se ha finalizado el tramite.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResultadoIrAPaso implements Serializable {
    /**
     * Id sesion tramitacion.
     */
    private String idSesionTramitacion;

    /**
     * Id paso actual.
     */
    private String idPasoActual;

    /**
     * Indica si el tramite se ha finalizado como consecuencia de ir a ese paso.
     */
    private boolean finalizadoTrasIrAPaso;

    /**
     * Método de acceso a idPaso.
     *
     * @return idPaso
     */
    public String getIdPasoActual() {
        return idPasoActual;
    }

    /**
     * Método para establecer idPaso.
     *
     * @param pIdPaso
     *            idPaso a establecer
     */
    public void setIdPasoActual(final String pIdPaso) {
        idPasoActual = pIdPaso;
    }

    /**
     * Método de acceso a tramiteFinalizado.
     *
     * @return tramiteFinalizado
     */
    public boolean isFinalizadoTrasIrAPaso() {
        return finalizadoTrasIrAPaso;
    }

    /**
     * Método para establecer tramiteFinalizado.
     *
     * @param pTramiteFinalizado
     *            tramiteFinalizado a establecer
     */
    public void setFinalizadoTrasIrAPaso(final boolean pTramiteFinalizado) {
        finalizadoTrasIrAPaso = pTramiteFinalizado;
    }

    /**
     * Método de acceso a idSesionTramitacion.
     *
     * @return idSesionTramitacion
     */
    public String getIdSesionTramitacion() {
        return idSesionTramitacion;
    }

    /**
     * Método para establecer idSesionTramitacion.
     *
     * @param pIdSesionTramitacion
     *            idSesionTramitacion a establecer
     */
    public void setIdSesionTramitacion(final String pIdSesionTramitacion) {
        idSesionTramitacion = pIdSesionTramitacion;
    }
}
