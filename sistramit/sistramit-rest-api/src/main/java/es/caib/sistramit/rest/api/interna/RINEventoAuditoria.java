package es.caib.sistramit.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Entrada de datos de filtro y paginacion
 *
 * @author Indra
 *
 */
@ApiModel(value = "RINEventoAuditoria", description = "Entrada de datos de filtro y paginacion")
public class RINEventoAuditoria {

	@ApiModelProperty(value = "Filtro auditoria")
	private RFiltroEventoAuditoria filtro;

	@ApiModelProperty(value = "Paginacion")
	private RFiltroPaginacion paginacion;

	public RFiltroEventoAuditoria getFiltro() {
		return filtro;
	}

	public void setFiltro(final RFiltroEventoAuditoria filtro) {
		this.filtro = filtro;
	}

	public RFiltroPaginacion getPaginacion() {
		return paginacion;
	}

	public void setPaginacion(final RFiltroPaginacion paginacion) {
		this.paginacion = paginacion;
	}

}
