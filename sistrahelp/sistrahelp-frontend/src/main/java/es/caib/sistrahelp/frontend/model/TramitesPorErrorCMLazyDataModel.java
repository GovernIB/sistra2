package es.caib.sistrahelp.frontend.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.caib.sistrahelp.core.api.model.EventoCM;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.service.HelpDeskService;

public class TramitesPorErrorCMLazyDataModel extends LazyDataModel<EventoCM> {

	public TramitesPorErrorCMLazyDataModel(final HelpDeskService helpDeskService, final Long pRowCount,
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

	@Override
	public List<EventoCM> load(final int first, final int pageSize, final String sortField, final SortOrder sortOrder,
			final Map<String, Object> filters) {
		if (filters.get("tipoEvento") != null && !filters.get("tipoEvento").toString().isEmpty()) {
			filtros.setErrorTipo(filters.get("tipoEvento").toString());
		} else {
			filtros.setErrorTipo(null);
		}
		filtros.setSortField(sortField);
		filtros.setSortOrder(sortOrder.name());
		setLista(helpDeskService.obtenerTramitesPorErrorCM(filtros, new FiltroPaginacion(first, pageSize))
				.getListaEventosCM());

		return getLista();

	}

	@Override
	public EventoCM getRowData(final String rowKey) {
		if (getLista() != null) {
			for (final EventoCM evento : getLista()) {
				if ((evento.getTipoEvento()).equals(rowKey))
					return evento;
			}
		}

		return null;
	}

	@Override
	public Object getRowKey(final EventoCM evento) {
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
