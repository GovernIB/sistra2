package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RPasoTramitacionDebeSaber", description = "Descripcion de RPasoTramitacionDebeSaber")
public class RPasoTramitacionDebeSaber extends RPasoTramitacion {

	/** Instrucciones inicio. */
	@ApiModelProperty(value = "Instrucciones inicio")
	private String instruccionesInicio;

	/** Script instrucciones inicio. */
	@ApiModelProperty(value = "Instrucciones inicio (establecido por script)")
	private RScript scriptInstruccionesInicio;

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
	 *                                instruccionesInicio a establecer
	 */
	public void setInstruccionesInicio(final String instruccionesInicio) {
		this.instruccionesInicio = instruccionesInicio;
	}

	/**
	 * Método de acceso a scriptInstruccionesInicio.
	 * 
	 * @return scriptInstruccionesInicio
	 */
	public RScript getScriptInstruccionesInicio() {
		return scriptInstruccionesInicio;
	}

	/**
	 * Método para establecer scriptInstruccionesInicio.
	 * 
	 * @param scriptInstruccionesInicio
	 *                                      scriptInstruccionesInicio a establecer
	 */
	public void setScriptInstruccionesInicio(final RScript scriptInstruccionesInicio) {
		this.scriptInstruccionesInicio = scriptInstruccionesInicio;
	}

}
