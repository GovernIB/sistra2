package es.caib.sistramit.core.service.model.system;

/**
 * Información acerca del flujo.
 *
 * @author Indra
 *
 */
public class EventoFlujoInfo {

    /** Id sesión tramitación. */
    private String idSesionTramitacion;

    /** Indica si se realiza debug. */
    private boolean debugEnabled;

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
     * Método de acceso a debugEnabled.
     *
     * @return debugEnabled
     */
    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    /**
     * Método para establecer debugEnabled.
     *
     * @param debugEnabled
     *            debugEnabled a establecer
     */
    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

}
