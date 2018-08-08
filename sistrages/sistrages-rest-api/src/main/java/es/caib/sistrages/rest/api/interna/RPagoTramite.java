package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Pago trámite.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPagoTramite", description = "Descripcion de RPagoTramite")
public class RPagoTramite {

    /** Identificador. */
	@ApiModelProperty(value = "Identificador")
    private String identificador;

    /** Descripción. */
	@ApiModelProperty(value = "Descripción")
    private String descripcion;

    /** Obligatoriedad: Si (S) / No (N) / Depende (D). */
	@ApiModelProperty(value = "Obligatoriedad: Si (S) / No (N) / Depende (D)")
    private String obligatoriedad;

    /** Script dependencia. */
	@ApiModelProperty(value = "Script dependencia")
    private RScript scriptDependencia;

    /** Script pago. */
	@ApiModelProperty(value = "Script pago")
    private RScript scriptPago;

    /** Simular pago. */
	@ApiModelProperty(value = "Simular pago")
    private boolean simularPago;

    /**
     * Método de acceso a identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Método para establecer identificador.
     *
     * @param identificador
     *            identificador a establecer
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Método de acceso a descripcion.
     *
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método para establecer descripcion.
     *
     * @param descripcion
     *            descripcion a establecer
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Método de acceso a obligatoriedad.
     *
     * @return obligatoriedad
     */
    public String getObligatoriedad() {
        return obligatoriedad;
    }

    /**
     * Método para establecer obligatoriedad.
     *
     * @param obligatoriedad
     *            obligatoriedad a establecer
     */
    public void setObligatoriedad(String obligatoriedad) {
        this.obligatoriedad = obligatoriedad;
    }

    /**
     * Método de acceso a scriptDependencia.
     *
     * @return scriptDependencia
     */
    public RScript getScriptDependencia() {
        return scriptDependencia;
    }

    /**
     * Método para establecer scriptDependencia.
     *
     * @param scriptDependencia
     *            scriptDependencia a establecer
     */
    public void setScriptDependencia(RScript scriptDependencia) {
        this.scriptDependencia = scriptDependencia;
    }

    /**
     * Método de acceso a scriptPago.
     *
     * @return scriptPago
     */
    public RScript getScriptPago() {
        return scriptPago;
    }

    /**
     * Método para establecer scriptPago.
     *
     * @param scriptPago
     *            scriptPago a establecer
     */
    public void setScriptPago(RScript scriptPago) {
        this.scriptPago = scriptPago;
    }

    /**
     * Método de acceso a simularPago.
     *
     * @return simularPago
     */
    public boolean isSimularPago() {
        return simularPago;
    }

    /**
     * Método para establecer simularPago.
     *
     * @param simularPago
     *            simularPago a establecer
     */
    public void setSimularPago(boolean simularPago) {
        this.simularPago = simularPago;
    }

}
