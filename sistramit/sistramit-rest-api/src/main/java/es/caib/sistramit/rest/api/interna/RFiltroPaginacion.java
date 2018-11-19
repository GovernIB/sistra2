package es.caib.sistramit.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Filtros para la paginacion
 *
 * @author Indra
 *
 */
@ApiModel(value = "RFiltroPaginacion", description = "Filtros para la paginacion")
public class RFiltroPaginacion {

	@ApiModelProperty(value = "Num. primer elemento")
	private Integer first;

	@ApiModelProperty(value = "Tamaño página")
	private Integer pageSize;

	public Integer getFirst() {
		return first;
	}

	public void setFirst(final Integer first) {
		this.first = first;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(final Integer pageSize) {
		this.pageSize = pageSize;
	}

}
