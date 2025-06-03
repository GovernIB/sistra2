package es.caib.sistrahelp.frontend.model;

import java.util.List;
import java.util.Map;

import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.model.FiltroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.PersistenciaAuditoria;
import es.caib.sistrahelp.core.api.service.HelpDeskService;

public class PersistenciaLazyDataModel extends LazyDataModel<PersistenciaAuditoria> {

	public PersistenciaLazyDataModel(final HelpDeskService helpDeskService, final Long pRowCount,
			final FiltroPersistenciaAuditoria pFiltros) {
		super();
		this.helpDeskService = helpDeskService;
		this.setRowCount(pRowCount.intValue());
		this.filtros = pFiltros;
	}

	private static final long serialVersionUID = 1L;

	private final HelpDeskService helpDeskService;

	private final FiltroPersistenciaAuditoria filtros;

	private List<PersistenciaAuditoria> lista;

	public int count(Map<String, FilterMeta> filterBy) {
		return helpDeskService.countAuditoriaPersistencia(filtros).intValue();
	}

	@Override
	public List<PersistenciaAuditoria> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

		if (sortBy != null && !sortBy.isEmpty()) {
			SortMeta sortMeta = sortBy.values().iterator().next();
			SortOrder sortOrder = sortMeta.getOrder();
			if (sortOrder != null) {
				filtros.setSortOrder(sortOrder.name());
			}
			filtros.setSortField(sortMeta.getField());
		}
		setLista(helpDeskService.obtenerAuditoriaPersistencia(filtros, new FiltroPaginacion(first, pageSize)));

		return getLista();

	}

	@Override
	public PersistenciaAuditoria getRowData(final String rowKey) {
		for (final PersistenciaAuditoria persistencia : getLista()) {
			if (persistencia.getId().equals(Long.valueOf(rowKey)))
				return persistencia;
		}

		return null;
	}

	@Override
	public String getRowKey(final PersistenciaAuditoria evento) {
		return evento.getId().toString();
	}

	public List<PersistenciaAuditoria> getLista() {
		return lista;
	}

	public void setLista(final List<PersistenciaAuditoria> lista) {
		this.lista = lista;
	}
}
