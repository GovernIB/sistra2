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
@ApiModel(value = "ROUTEventoCM", description = "Salida de eventos totales cuadro de mandos")
public class ROUTEventoCM {

	@ApiModelProperty(value = "Lista Pagos")
	private List<REventoCM> listaEventos;

	@ApiModelProperty(value = "Num. elementos")
	private Long numElementos;

	public final List<REventoCM> getListaEventos() {
		return listaEventos;
	}

	public final void setListaEventos(List<REventoCM> listaEventos) {
		this.listaEventos = listaEventos;
	}

	public Long getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(final Long numElementos) {
		this.numElementos = numElementos;
	}

}
