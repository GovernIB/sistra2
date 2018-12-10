package es.caib.sistramit.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Entrada de datos de filtro y paginacion
 *
 * @author Indra
 *
 */
@ApiModel(value = "RINTramiteAuditoria", description = "Entrada de datos de filtro y paginacion")
public class RINTramiteAuditoria {

	public RINTramiteAuditoria() {
		super();
	}

	@ApiModelProperty(value = "Filtro pago")
	private RFiltroPersistenciaAuditoria filtro;

	@ApiModelProperty(value = "Paginacion")
	private RFiltroPaginacion paginacion;

	public RFiltroPersistenciaAuditoria getFiltro() {
		return filtro;
	}

	public void setFiltro(final RFiltroPersistenciaAuditoria filtro) {
		this.filtro = filtro;
	}

	public RFiltroPaginacion getPaginacion() {
		return paginacion;
	}

	public void setPaginacion(final RFiltroPaginacion paginacion) {
		this.paginacion = paginacion;
	}

}
