package es.caib.sistrages.rest.api;

/**
 * Propiedades campo formulario.
 *
 * @author Indra
 *
 */
public class PropiedadesCampo {

    /** Obligatorio. */
    private boolean obligatorio;

    /** Solo lectura. */
    private boolean soloLectura;

    /** No modificable (no permite modificar su valor inicial). */
    private boolean noModificable;

    /** Script autorrellable. */
    private Script scriptAutorrellenable;

    /** Script estado. */
    private Script scriptEstado;

    /** Script validacion. */
    private Script scriptValidacion;

    /**
     * Método de acceso a obligatorio.
     * 
     * @return obligatorio
     */
    public boolean isObligatorio() {
        return obligatorio;
    }

    /**
     * Método para establecer obligatorio.
     * 
     * @param obligatorio
     *            obligatorio a establecer
     */
    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    /**
     * Método de acceso a soloLectura.
     * 
     * @return soloLectura
     */
    public boolean isSoloLectura() {
        return soloLectura;
    }

    /**
     * Método para establecer soloLectura.
     * 
     * @param soloLectura
     *            soloLectura a establecer
     */
    public void setSoloLectura(boolean soloLectura) {
        this.soloLectura = soloLectura;
    }

    /**
     * Método de acceso a noModificable.
     * 
     * @return noModificable
     */
    public boolean isNoModificable() {
        return noModificable;
    }

    /**
     * Método para establecer noModificable.
     * 
     * @param noModificable
     *            noModificable a establecer
     */
    public void setNoModificable(boolean noModificable) {
        this.noModificable = noModificable;
    }

    /**
     * Método de acceso a scriptAutorrellenable.
     * 
     * @return scriptAutorrellenable
     */
    public Script getScriptAutorrellenable() {
        return scriptAutorrellenable;
    }

    /**
     * Método para establecer scriptAutorrellenable.
     * 
     * @param scriptAutorrellenable
     *            scriptAutorrellenable a establecer
     */
    public void setScriptAutorrellenable(Script scriptAutorrellenable) {
        this.scriptAutorrellenable = scriptAutorrellenable;
    }

    /**
     * Método de acceso a scriptEstado.
     * 
     * @return scriptEstado
     */
    public Script getScriptEstado() {
        return scriptEstado;
    }

    /**
     * Método para establecer scriptEstado.
     * 
     * @param scriptEstado
     *            scriptEstado a establecer
     */
    public void setScriptEstado(Script scriptEstado) {
        this.scriptEstado = scriptEstado;
    }

    /**
     * Método de acceso a scriptValidacion.
     * 
     * @return scriptValidacion
     */
    public Script getScriptValidacion() {
        return scriptValidacion;
    }

    /**
     * Método para establecer scriptValidacion.
     * 
     * @param scriptValidacion
     *            scriptValidacion a establecer
     */
    public void setScriptValidacion(Script scriptValidacion) {
        this.scriptValidacion = scriptValidacion;
    }

}
