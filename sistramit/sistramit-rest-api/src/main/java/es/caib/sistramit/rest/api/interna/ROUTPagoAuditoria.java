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
@ApiModel(value = "ROUTPagoAuditoria", description = "Salida de datos de pago y paginacion")
public class ROUTPagoAuditoria {

	@ApiModelProperty(value = "Lista Pagos")
	private List<RPagoAuditoria> listaPagos;

	@ApiModelProperty(value = "Num. elementos")
	private Long numElementos;

	public List<RPagoAuditoria> getListaPagos() {
		return listaPagos;
	}

	public void setListaPagos(final List<RPagoAuditoria> listaEventos) {
		this.listaPagos = listaEventos;
	}

	public Long getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(final Long numElementos) {
		this.numElementos = numElementos;
	}

}
