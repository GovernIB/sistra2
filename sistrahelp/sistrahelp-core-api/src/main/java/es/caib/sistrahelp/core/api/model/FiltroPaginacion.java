package es.caib.sistrahelp.core.api.model;

/**
 * Filtros para la paginacion
 *
 * @author Indra
 *
 */
public class FiltroPaginacion extends ModelApi {

	private static final long serialVersionUID = 1L;

	private Integer first;

	private Integer pageSize;

	public FiltroPaginacion() {
		super();
	}

	public FiltroPaginacion(final Integer first, final Integer pageSize) {
		super();
		this.first = first;
		this.pageSize = pageSize;
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
