package es.caib.sistramit.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Entrada de datos de filtro y paginacion
 *
 * @author Indra
 *
 */
@ApiModel(value = "RINPagoAuditoria", description = "Entrada de datos de filtro y paginacion")
public class RINPagoAuditoria {

	public RINPagoAuditoria() {
		super();
	}

	@ApiModelProperty(value = "Filtro pago")
	private RFiltroPagoAuditoria filtro;

	@ApiModelProperty(value = "Paginacion")
	private RFiltroPaginacion paginacion;

	public RFiltroPagoAuditoria getFiltro() {
		return filtro;
	}

	public void setFiltro(final RFiltroPagoAuditoria filtro) {
		this.filtro = filtro;
	}

	public RFiltroPaginacion getPaginacion() {
		return paginacion;
	}

	public void setPaginacion(final RFiltroPaginacion paginacion) {
		this.paginacion = paginacion;
	}

}
