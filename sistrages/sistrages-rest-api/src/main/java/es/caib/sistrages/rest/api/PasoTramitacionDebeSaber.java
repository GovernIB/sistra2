package es.caib.sistrages.rest.api;

public class PasoTramitacionDebeSaber extends PasoTramitacion {

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
