package es.caib.sistrahelp.frontend.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
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

	@Override
	public List<PersistenciaAuditoria> load(final int first, final int pageSize, final String sortField,
			final SortOrder sortOrder, final Map<String, Object> filters) {

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
	public Object getRowKey(final PersistenciaAuditoria evento) {
		return evento.getId();
	}

	public List<PersistenciaAuditoria> getLista() {
		return lista;
	}

	public void setLista(final List<PersistenciaAuditoria> lista) {
		this.lista = lista;
	}
}
