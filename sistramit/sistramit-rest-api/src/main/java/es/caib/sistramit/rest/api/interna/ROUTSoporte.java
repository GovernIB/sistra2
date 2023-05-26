package es.caib.sistramit.rest.api.interna;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Resultado de pagos
 *
 * @author Indra
 *
 */
@ApiModel(value = "ROUTSoporte", description = "Salida de formularios Soporte")
public class ROUTSoporte {

	/**
	 * Evento Auditoria.
	 *
	 * @author Indra
	 *
	 */

	@ApiModelProperty(value = "Num. elementos")
	private Long numElementos;

	@ApiModelProperty(value = "Lista errores")
	private List<RSoporte> listaFromularios;

	public Long getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(Long numElementos) {
		this.numElementos = numElementos;
	}

	public List<RSoporte> getListaFormularios() {
		return listaFromularios;
	}

	public void setListaFormularios(List<RSoporte> listaFromularios) {
		this.listaFromularios = listaFromularios;
	}

}
