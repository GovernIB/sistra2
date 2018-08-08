package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Paso registrar.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPasoTramitacionRegistrar", description = "Descripcion de RPasoTramitacionRegistrar", parent = RPasoTramitacion.class)
public class RPasoTramitacionRegistrar extends RPasoTramitacion {

    /** Destino registro. */
	@ApiModelProperty(value = "Destino registro")
    private RDestinoRegistro destino;

    /** Script destino registro. */
	@ApiModelProperty(value = "Script destino registro")
    private RScript scriptDestino;

    /** Instrucciones presentación. */
	@ApiModelProperty(value = "Instrucciones presentación")
    private String instruccionesPresentacionHtml;

    /** Instrucciones tramitación. */
	@ApiModelProperty(value = "Instrucciones tramitación")
    private String instruccionesTramitacionHtml;

    /** Script presentador. */
	@ApiModelProperty(value = "Script presentador")
    private RScript scriptPresentador;

    /** Indica si admite representación. */
	@ApiModelProperty(value = "Indica si admite representación")
    private boolean admiteRepresentacion;

    /** Indica si valida representación. */
	@ApiModelProperty(value = "Indica si valida representación")
    private boolean validaRepresentacion;

    /** Script representante. */
	@ApiModelProperty(value = "Script representante")
    private RScript scriptRepresentante;

    /** Script validar registro. */
	@ApiModelProperty(value = "Script validar registro")
    private RScript scriptValidar;

    /**
     * Método de acceso a destino.
     *
     * @return destino
     */
    public RDestinoRegistro getDestino() {
        return destino;
    }

    /**
     * Método para establecer destino.
     *
     * @param destino
     *            destino a establecer
     */
    public void setDestino(RDestinoRegistro destino) {
        this.destino = destino;
    }

    /**
     * Método de acceso a scriptDestino.
     *
     * @return scriptDestino
     */
    public RScript getScriptDestino() {
        return scriptDestino;
    }

    /**
     * Método para establecer scriptDestino.
     *
     * @param scriptDestino
     *            scriptDestino a establecer
     */
    public void setScriptDestino(RScript scriptDestino) {
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
    public RScript getScriptPresentador() {
        return scriptPresentador;
    }

    /**
     * Método para establecer scriptPresentador.
     *
     * @param scriptPresentador
     *            scriptPresentador a establecer
     */
    public void setScriptPresentador(RScript scriptPresentador) {
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
    public RScript getScriptRepresentante() {
        return scriptRepresentante;
    }

    /**
     * Método para establecer scriptRepresentante.
     *
     * @param scriptRepresentante
     *            scriptRepresentante a establecer
     */
    public void setScriptRepresentante(RScript scriptRepresentante) {
        this.scriptRepresentante = scriptRepresentante;
    }

    /**
     * Método de acceso a scriptValidar.
     *
     * @return scriptValidar
     */
    public RScript getScriptValidar() {
        return scriptValidar;
    }

    /**
     * Método para establecer scriptValidar.
     *
     * @param scriptValidar
     *            scriptValidar a establecer
     */
    public void setScriptValidar(RScript scriptValidar) {
        this.scriptValidar = scriptValidar;
    }

}
