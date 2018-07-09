package es.caib.sistramit.core.api.model.flujo;

/**
 * Flujo tramitacion info.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class FlujoTramitacionInfo implements ModelApi {

    /**
     * Id sesión tramitación.
     */
    private String idSesionTramitacion;

    /**
     * Debug.
     */
    private boolean debug;

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
     * @param idSesionTramitacion
     *            idSesionTramitacion a establecer
     */
    public void setIdSesionTramitacion(String idSesionTramitacion) {
        this.idSesionTramitacion = idSesionTramitacion;
    }

    /**
     * Método de acceso a debug.
     * 
     * @return debug
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * Método para establecer debug.
     * 
     * @param debug
     *            debug a establecer
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
