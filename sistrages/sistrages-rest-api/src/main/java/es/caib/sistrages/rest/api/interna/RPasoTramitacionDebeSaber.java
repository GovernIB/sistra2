package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RPasoTramitacionDebeSaber", description = "Descripcion de RPasoTramitacionDebeSaber")
public class RPasoTramitacionDebeSaber extends RPasoTramitacion {

    /** Instrucciones inicio. */
	@ApiModelProperty(value = "Instrucciones inicio")
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
