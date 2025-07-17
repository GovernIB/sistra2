package es.caib.sistrahelp.frontend.model;

import java.util.List;
import java.util.Map;

import es.caib.sistrahelp.core.api.model.*;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import es.caib.sistrahelp.core.api.service.HelpDeskService;

public class SoporteLazyDataModel extends LazyDataModel<Soporte> {

	public SoporteLazyDataModel(final HelpDeskService helpDeskService, final Long pRowCount,
			final FiltroAuditoriaTramitacion pFiltros) {
		super();
		this.helpDeskService = helpDeskService;
		this.setRowCount(pRowCount.intValue());
		this.filtros = pFiltros;
	}

	private static final long serialVersionUID = 1L;

	private final HelpDeskService helpDeskService;

	private final FiltroAuditoriaTramitacion filtros;

	private List<Soporte> lista;

	public int count(Map<String, FilterMeta> filterBy) {

		filtros.setSoloContar(true);
		ResultadoSoporte result = helpDeskService.obtenerFormularioSoporte(filtros, null);
		filtros.setSoloContar(false);
		return result.getNumElementos().intValue();
	}

	@Override
	public List<Soporte> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		if (sortBy != null && !sortBy.isEmpty()) {
			SortMeta sortMeta = sortBy.values().iterator().next();
			SortOrder sortOrder = sortMeta.getOrder();
			if (sortOrder != null) {
				filtros.setSortOrder(sortOrder.name());
			}
			filtros.setSortField(sortMeta.getField());
		}
		setLista(helpDeskService.obtenerFormularioSoporte(filtros, new FiltroPaginacion(first, pageSize))
				.getListaFormularios());

		return getLista();

	}

	@Override
	public Soporte getRowData(final String rowKey) {
		for (final Soporte evento : getLista()) {
			if (evento.getCodigo() == Long.parseLong(rowKey)) {
				return evento;
			}
		}

		return null;
	}

	@Override
	public String getRowKey(final Soporte evento) {
		return evento.getCodigo().toString();
	}

	public List<Soporte> getLista() {
		return lista;
	}

	public void setLista(final List<Soporte> list) {
		this.lista = list;
	}
}
