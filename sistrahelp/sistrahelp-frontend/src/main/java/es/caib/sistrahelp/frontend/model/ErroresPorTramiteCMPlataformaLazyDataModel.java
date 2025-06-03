package es.caib.sistrahelp.frontend.model;

import java.util.List;
import java.util.Map;

import es.caib.sistrahelp.core.api.model.*;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import es.caib.sistrahelp.core.api.service.HelpDeskService;

public class ErroresPorTramiteCMPlataformaLazyDataModel extends LazyDataModel<EventoCM> {

	public ErroresPorTramiteCMPlataformaLazyDataModel(final HelpDeskService helpDeskService, final Long pRowCount,
			final FiltroAuditoriaTramitacion pFiltros) {
		super();
		this.helpDeskService = helpDeskService;
		this.setRowCount(pRowCount.intValue());
		this.filtros = pFiltros;
	}

	private static final long serialVersionUID = 1L;

	private final HelpDeskService helpDeskService;

	private final FiltroAuditoriaTramitacion filtros;

	private List<EventoCM> lista;

	public int count(Map<String, FilterMeta> filterBy) {
		//TODO Implementar
		//return 20;
		//return helpDeskService.countSoporte(filtros).intValue();
		filtros.setSoloContar(true);
		Long numElementos = helpDeskService.obtenerErroresPlataformaCM(filtros, null).getNumElementos();
		return numElementos == null ? 0 : numElementos.intValue();
	}

	@Override
	public List<EventoCM> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		filtros.setSoloContar(false);

		if (sortBy != null && !sortBy.isEmpty()) {
			SortMeta sortMeta = sortBy.values().iterator().next();
			SortOrder sortOrder = sortMeta.getOrder();
			if (sortOrder != null) {
				filtros.setSortOrder(sortOrder.name());
			}
			filtros.setSortField(sortMeta.getField());
		}
		setLista(helpDeskService.obtenerErroresPlataformaCM(filtros, new FiltroPaginacion(first, pageSize))
				.getListaEventosCM());

		return getLista();

	}

	@Override
	public EventoCM getRowData(final String rowKey) {
		for (final EventoCM evento : getLista()) {
			if ((evento.getTipoEvento()).equals(rowKey))
				return evento;
		}

		return null;
	}

	@Override
	public String getRowKey(final EventoCM evento) {
		return evento.getTipoEvento();
	}

	public final List<EventoCM> getLista() {
		return lista;
	}

	public final void setLista(List<EventoCM> lista) {
		this.lista = lista;
	}

	public final FiltroAuditoriaTramitacion getFiltros() {
		return filtros;
	}

}
