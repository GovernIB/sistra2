package es.caib.sistrages.rest.api.interna;

public class RPasoTramitacionDebeSaber extends RPasoTramitacion {

    /** Instrucciones inicio. */
    private String instruccionesInicio;

    /**
     * Método de acceso a instruccionesInicio.
     *
     * @return instruccionesInicio
     */
    public String getInstruccionesInicio() {
        return instruccionesInicio;
    }

    /**
     * Método para establecer instruccionesInicio.
     *
     * @param instruccionesInicio
     *            instruccionesInicio a establecer
     */
    public void setInstruccionesInicio(String instruccionesInicio) {
        this.instruccionesInicio = instruccionesInicio;
    }

}
