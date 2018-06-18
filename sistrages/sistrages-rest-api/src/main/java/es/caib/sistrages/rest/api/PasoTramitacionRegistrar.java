package es.caib.sistrages.rest.api;

/**
 * Paso registrar.
 *
 * @author Indra
 *
 */
public class PasoTramitacionRegistrar extends PasoTramitacion {

    /** Destino registro. */
    private DestinoRegistro destino;

    /** Script destino registro. */
    private Script scriptDestino;

    /** Instrucciones presentación. */
    private String instruccionesPresentacionHtml;

    /** Instrucciones tramitación. */
    private String instruccionesTramitacionHtml;

    /** Script presentador. */
    private Script scriptPresentador;

    /** Indica si admite representación. */
    private boolean admiteRepresentacion;

    /** Indica si valida representación. */
    private boolean validaRepresentacion;

    /** Script representante. */
    private Script scriptRepresentante;

    /** Script validar registro. */
    private Script scriptValidar;

    /**
     * Método de acceso a destino.
     * 
     * @return destino
     */
    public DestinoRegistro getDestino() {
        return destino;
    }

    /**
     * Método para establecer destino.
     * 
     * @param destino
     *            destino a establecer
     */
    public void setDestino(DestinoRegistro destino) {
        this.destino = destino;
    }

    /**
     * Método de acceso a scriptDestino.
     * 
     * @return scriptDestino
     */
    public Script getScriptDestino() {
        return scriptDestino;
    }

    /**
     * Método para establecer scriptDestino.
     * 
     * @param scriptDestino
     *            scriptDestino a establecer
     */
    public void setScriptDestino(Script scriptDestino) {
        this.scriptDestino = scriptDestino;
    }

    /**
     * Método de acceso a instruccionesPresentacionHtml.
     * 
     * @return instruccionesPresentacionHtml
     */
    public String getInstruccionesPresentacionHtml() {
        return instruccionesPresentacionHtml;
    }

    /**
     * Método para establecer instruccionesPresentacionHtml.
     * 
     * @param instruccionesPresentacionHtml
     *            instruccionesPresentacionHtml a establecer
     */
    public void setInstruccionesPresentacionHtml(
            String instruccionesPresentacionHtml) {
        this.instruccionesPresentacionHtml = instruccionesPresentacionHtml;
    }

    /**
     * Método de acceso a instruccionesTramitacionHtml.
     * 
     * @return instruccionesTramitacionHtml
     */
    public String getInstruccionesTramitacionHtml() {
        return instruccionesTramitacionHtml;
    }

    /**
     * Método para establecer instruccionesTramitacionHtml.
     * 
     * @param instruccionesTramitacionHtml
     *            instruccionesTramitacionHtml a establecer
     */
    public void setInstruccionesTramitacionHtml(
            String instruccionesTramitacionHtml) {
        this.instruccionesTramitacionHtml = instruccionesTramitacionHtml;
    }

    /**
     * Método de acceso a scriptPresentador.
     * 
     * @return scriptPresentador
     */
    public Script getScriptPresentador() {
        return scriptPresentador;
    }

    /**
     * Método para establecer scriptPresentador.
     * 
     * @param scriptPresentador
     *            scriptPresentador a establecer
     */
    public void setScriptPresentador(Script scriptPresentador) {
        this.scriptPresentador = scriptPresentador;
    }

    /**
     * Método de acceso a admiteRepresentacion.
     * 
     * @return admiteRepresentacion
     */
    public boolean isAdmiteRepresentacion() {
        return admiteRepresentacion;
    }

    /**
     * Método para establecer admiteRepresentacion.
     * 
     * @param admiteRepresentacion
     *            admiteRepresentacion a establecer
     */
    public void setAdmiteRepresentacion(boolean admiteRepresentacion) {
        this.admiteRepresentacion = admiteRepresentacion;
    }

    /**
     * Método de acceso a validaRepresentacion.
     * 
     * @return validaRepresentacion
     */
    public boolean isValidaRepresentacion() {
        return validaRepresentacion;
    }

    /**
     * Método para establecer validaRepresentacion.
     * 
     * @param validaRepresentacion
     *            validaRepresentacion a establecer
     */
    public void setValidaRepresentacion(boolean validaRepresentacion) {
        this.validaRepresentacion = validaRepresentacion;
    }

    /**
     * Método de acceso a scriptRepresentante.
     * 
     * @return scriptRepresentante
     */
    public Script getScriptRepresentante() {
        return scriptRepresentante;
    }

    /**
     * Método para establecer scriptRepresentante.
     * 
     * @param scriptRepresentante
     *            scriptRepresentante a establecer
     */
    public void setScriptRepresentante(Script scriptRepresentante) {
        this.scriptRepresentante = scriptRepresentante;
    }

    /**
     * Método de acceso a scriptValidar.
     * 
     * @return scriptValidar
     */
    public Script getScriptValidar() {
        return scriptValidar;
    }

    /**
     * Método para establecer scriptValidar.
     * 
     * @param scriptValidar
     *            scriptValidar a establecer
     */
    public void setScriptValidar(Script scriptValidar) {
        this.scriptValidar = scriptValidar;
    }

}
