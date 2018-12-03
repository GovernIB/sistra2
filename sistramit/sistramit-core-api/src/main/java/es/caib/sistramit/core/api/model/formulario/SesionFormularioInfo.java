package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

/**
 * Informacion de la sesion de formulario.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class SesionFormularioInfo implements Serializable {

    /** Id sesión tramitación en la que se abre la sesión formulario. */
    private String idSesionTramitacion;

    /** Id sesión formulario. */
    private String idSesionFormulario;

    /**
     * Debug habilitado.
     */
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
     * @param pIdSesionTramitacion
     *            idSesionTramitacion a establecer
     */
    public void setIdSesionTramitacion(final String pIdSesionTramitacion) {
        idSesionTramitacion = pIdSesionTramitacion;
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
     * @param pDebugEnabled
     *            debugEnabled a establecer
     */
    public void setDebugEnabled(final boolean pDebugEnabled) {
        debugEnabled = pDebugEnabled;
    }

    /**
     * Método de acceso a idSesionFormulario.
     * 
     * @return idSesionFormulario
     */
    public String getIdSesionFormulario() {
        return idSesionFormulario;
    }

    /**
     * Método para establecer idSesionFormulario.
     * 
     * @param idSesionFormulario
     *            idSesionFormulario a establecer
     */
    public void setIdSesionFormulario(String idSesionFormulario) {
        this.idSesionFormulario = idSesionFormulario;
    }

}
