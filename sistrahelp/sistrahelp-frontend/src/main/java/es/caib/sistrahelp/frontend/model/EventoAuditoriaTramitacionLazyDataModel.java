package es.caib.sistrahelp.frontend.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
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

	@Override
	public List<EventoAuditoriaTramitacion> load(final int first, final int pageSize, final String sortField,
			final SortOrder sortOrder, final Map<String, Object> filters) {
		filtros.setSortField(sortField);
		filtros.setSortOrder(sortOrder.name());
		setLista(helpDeskService.obtenerAuditoriaEvento(filtros, new FiltroPaginacion(first, pageSize)));

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
	public Object getRowKey(final EventoAuditoriaTramitacion evento) {
		return evento.getId();
	}

	public List<EventoAuditoriaTramitacion> getLista() {
		return lista;
	}

	public void setLista(final List<EventoAuditoriaTramitacion> lista) {
		this.lista = lista;
	}
}
