package es.caib.sistramit.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Resultado de pagos
 *
 * @author Indra
 *
 */
@ApiModel(value = "ROUTErroresPorTramiteCM", description = "Salida de errores por tramite cuadro de mandos")
public class ROUTErroresPorTramiteCM {

	@ApiModelProperty(value = "Lista errores")
	private List<RErroresPorTramiteCM> listaErrores;

	@ApiModelProperty(value = "Num. elementos")
	private Long numElementos;

	public final List<RErroresPorTramiteCM> getListaErrores() {
		return listaErrores;
	}

	public final void setListaErrores(List<RErroresPorTramiteCM> listaErrores) {
		this.listaErrores = listaErrores;
	}

	public Long getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(final Long numElementos) {
		this.numElementos = numElementos;
	}

}
