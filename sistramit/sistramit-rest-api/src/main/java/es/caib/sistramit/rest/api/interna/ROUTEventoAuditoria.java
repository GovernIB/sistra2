package es.caib.sistramit.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Resultado de eventos
 *
 * @author Indra
 *
 */
@ApiModel(value = "ROUTEventoAuditoria", description = "salida de datos de eventos y paginacion")
public class ROUTEventoAuditoria {

	@ApiModelProperty(value = "Lista eventos")
	private List<REventoAuditoria> listaEventos;

	@ApiModelProperty(value = "Num. elementos")
	private Long numElementos;

	public List<REventoAuditoria> getListaEventos() {
		return listaEventos;
	}

	public void setListaEventos(final List<REventoAuditoria> listaEventos) {
		this.listaEventos = listaEventos;
	}

	public Long getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(final Long numElementos) {
		this.numElementos = numElementos;
	}

}
