package es.caib.sistrahelp.frontend.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.caib.sistrahelp.core.api.model.ErroresPorTramiteCM;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.service.HelpDeskService;

public class TramitesPorErrorCMExpansionLazyDataModel extends LazyDataModel<ErroresPorTramiteCM> {

	public TramitesPorErrorCMExpansionLazyDataModel(final HelpDeskService helpDeskService, final Long pRowCount,
			final FiltroAuditoriaTramitacion pFiltros) {
		super();
		this.helpDeskService = helpDeskService;
		this.setRowCount(pRowCount.intValue());
		this.filtros = pFiltros;
	}

	private static final long serialVersionUID = 1L;

	private final HelpDeskService helpDeskService;

	private final FiltroAuditoriaTramitacion filtros;

	private List<ErroresPorTramiteCM> lista;

	@Override
	public List<ErroresPorTramiteCM> load(final int first, final int pageSize, final String sortField,
			final SortOrder sortOrder, final Map<String, Object> filters) {
		if (filters.get("idTramite") != null && !filters.get("idTramite").toString().isEmpty()) {
			filtros.setIdTramite(filters.get("idTramite").toString());
		} else {
			filtros.setIdTramite(null);
		}
		filtros.setSortField(sortField);
		filtros.setSortOrder(sortOrder.name());
		setLista(helpDeskService.obtenerTramitesPorErrorCMExpansion(filtros, new FiltroPaginacion(first, pageSize))
				.getListaErroresCM());

		return getLista();

	}

	@Override
	public ErroresPorTramiteCM getRowData(final String rowKey) {
		for (final ErroresPorTramiteCM evento : getLista()) {
			if ((evento.getIdTramite() + evento.getVersion().toString()).equals(rowKey))
				return evento;
		}

		return null;
	}

	@Override
	public Object getRowKey(final ErroresPorTramiteCM evento) {
		return evento.getIdTramite() + evento.getVersion().toString();
	}

	public List<ErroresPorTramiteCM> getLista() {
		return lista;
	}

	public void setLista(final List<ErroresPorTramiteCM> list) {
		this.lista = list;
	}
}
