package es.caib.sistramit.core.api.model.system;

/**
 * Filtros para la paginacion
 *
 * @author Indra
 *
 */
public class FiltroPaginacion {

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
