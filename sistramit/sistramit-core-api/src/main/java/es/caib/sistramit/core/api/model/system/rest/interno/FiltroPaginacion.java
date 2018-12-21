package es.caib.sistramit.core.api.model.system.rest.interno;

import java.io.Serializable;

/**
 * Filtros para la paginacion (RestApiInternaService)
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class FiltroPaginacion implements Serializable {

	private Integer first;

	private Integer pageSize;

	public FiltroPaginacion() {
		super();
	}

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
