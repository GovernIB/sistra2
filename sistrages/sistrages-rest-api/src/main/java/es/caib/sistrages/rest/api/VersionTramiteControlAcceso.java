package es.caib.sistrages.rest.api;

/**
 * Control acceso trámite.
 *
 * @author Indra
 *
 */
public class VersionTramiteControlAcceso {

    /** Indica si está activo. */
    private boolean activo;

    /** Indica si se habilita debug. */
    private boolean debug;

    /** Indica si se limita tramitación. */
    private boolean limitarTramitacion;

    /** Límite tramitación: número de inicio de tramites. */
    private int limiteTramitacionInicios;

    /** Límite tramitación: intervalo minutos. */
    private int limiteTramitacionIntervalo;

    /**
     * Método de acceso a activo.
     * 
     * @return activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Método para establecer activo.
     * 
     * @param activo
     *            activo a establecer
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
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

    /**
     * Método de acceso a limitarTramitacion.
     * 
     * @return limitarTramitacion
     */
    public boolean isLimitarTramitacion() {
        return limitarTramitacion;
    }

    /**
     * Método para establecer limitarTramitacion.
     * 
     * @param limitarTramitacion
     *            limitarTramitacion a establecer
     */
    public void setLimitarTramitacion(boolean limitarTramitacion) {
        this.limitarTramitacion = limitarTramitacion;
    }

    /**
     * Método de acceso a limiteTramitacionInicios.
     * 
     * @return limiteTramitacionInicios
     */
    public int getLimiteTramitacionInicios() {
        return limiteTramitacionInicios;
    }

    /**
     * Método para establecer limiteTramitacionInicios.
     * 
     * @param limiteTramitacionInicios
     *            limiteTramitacionInicios a establecer
     */
    public void setLimiteTramitacionInicios(int limiteTramitacionInicios) {
        this.limiteTramitacionInicios = limiteTramitacionInicios;
    }

    /**
     * Método de acceso a limiteTramitacionIntervalo.
     * 
     * @return limiteTramitacionIntervalo
     */
    public int getLimiteTramitacionIntervalo() {
        return limiteTramitacionIntervalo;
    }

    /**
     * Método para establecer limiteTramitacionIntervalo.
     * 
     * @param limiteTramitacionIntervalo
     *            limiteTramitacionIntervalo a establecer
     */
    public void setLimiteTramitacionIntervalo(int limiteTramitacionIntervalo) {
        this.limiteTramitacionIntervalo = limiteTramitacionIntervalo;
    }

}
