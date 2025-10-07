package es.caib.sistrahelp.frontend.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.service.HelpDeskService;

public class EventoAuditoriaTramitacionLazyDataModel extends LazyDataModel<EventoAuditoriaTramitacion> {

	public EventoAuditoriaTramitacionLazyDataModel(final HelpDeskService helpDeskService, final Long pRowCount,
			final FiltroAuditoriaTramitacion pFiltros) {
		super();
		this.helpDeskService = helpDeskService;
		this.setRowCount(pRowCount.intValue());
		this.filtros = pFiltros;
	}

	private static final long serialVersionUID = 1L;

	private final HelpDeskService helpDeskService;

	private final FiltroAuditoriaTramitacion filtros;

	private List<EventoAuditoriaTramitacion> lista;

	private int first;

	public int count(Map<String, FilterMeta> filterBy) {
		return helpDeskService.countAuditoriaEvento(filtros).intValue();
	}

	@Override
	public List<EventoAuditoriaTramitacion> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

		if (sortBy != null && !sortBy.isEmpty()) {
			SortMeta sortMeta = sortBy.values().iterator().next();
			SortOrder sortOrder = sortMeta.getOrder();
			if (sortOrder != null) {
				filtros.setSortOrder(sortOrder.name());
			}
			filtros.setSortField(sortMeta.getField());
		}
		setLista(helpDeskService.obtenerAuditoriaEvento(filtros, new FiltroPaginacion(first, pageSize)));

		setFirst(first);

		return getLista();

	}

	@Override
	public EventoAuditoriaTramitacion getRowData(final String rowKey) {
		for (final EventoAuditoriaTramitacion evento : getLista()) {
			if (evento.getId().equals(Long.valueOf(rowKey)))
				return evento;
		}

		return null;
	}

	@Override
	public String getRowKey(final EventoAuditoriaTramitacion evento) {
		return evento.getId().toString();
	}

	public List<EventoAuditoriaTramitacion> getLista() {
		return lista;
	}

	public void setLista(final List<EventoAuditoriaTramitacion> lista) {
		this.lista = lista;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}
}
