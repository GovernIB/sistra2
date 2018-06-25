package es.caib.sistrages.rest.api.interna;

/**
 * Pago trámite.
 *
 * @author Indra
 *
 */
public class RPagoTramite {

    /** Identificador. */
    private String identificador;

    /** Descripción. */
    private String descripcion;

    /** Obligatoriedad: Si (S) / No (N) / Depende (D). */
    private String obligatoriedad;

    /** Script dependencia. */
    private RScript scriptDependencia;

    /** Script pago. */
    private RScript scriptPago;

    /** Simular pago. */
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
