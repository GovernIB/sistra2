package es.caib.sistrahelp.frontend.model;

import java.util.List;
import java.util.Map;

import es.caib.sistrahelp.core.api.model.EventoCM;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
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

	public int count(Map<String, FilterMeta> filterBy) {
		//TODO Implementar
		//return 20;
		//return helpDeskService.countSoporte(filtros).intValue();
		filtros.setSoloContar(true);
		Long numElementos = helpDeskService.obtenerTramitesPorErrorCMExpansion(filtros, null).getNumElementos();
		return numElementos == null ? 0 : numElementos.intValue();
	}

	@Override
	public List<ErroresPorTramiteCM> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		filtros.setSoloContar(false);

		if (sortBy != null && !sortBy.isEmpty()) {
			SortMeta sortMeta = sortBy.values().iterator().next();
			SortOrder sortOrder = sortMeta.getOrder();
			if (sortOrder != null) {
				filtros.setSortOrder(sortOrder.name());
			}
			filtros.setSortField(sortMeta.getField());
		}
		/** TODO Implementar
		if (filters.get("idTramite") != null && !filters.get("idTramite").toString().isEmpty()) {
			filtros.setIdTramite(filters.get("idTramite").toString());
		} else {
			filtros.setIdTramite(null);
		} */
		setLista(helpDeskService.obtenerTramitesPorErrorCMExpansion(filtros, new FiltroPaginacion(first, pageSize))
				.getListaErroresCM());

		return getLista();

	}

	@Override
	public ErroresPorTramiteCM getRowData(final String rowKey) {
		if (getLista() != null) {
			for (final ErroresPorTramiteCM evento : getLista()) {
				if ((evento.getIdTramite() + evento.getVersion().toString()).equals(rowKey))
					return evento;
			}
		}

		return null;
	}


	@Override
	public String getRowKey(final ErroresPorTramiteCM evento) {
		return evento.getIdTramite() + evento.getVersion().toString();
	}

	public List<ErroresPorTramiteCM> getLista() {
		return lista;
	}

	public void setLista(final List<ErroresPorTramiteCM> list) {
		this.lista = list;
	}
}
