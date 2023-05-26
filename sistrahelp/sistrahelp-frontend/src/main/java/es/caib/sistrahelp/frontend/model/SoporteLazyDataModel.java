package es.caib.sistrahelp.frontend.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.caib.sistrahelp.core.api.model.ErroresPorTramiteCM;
import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.model.ResultadoErroresPorTramiteCM;
import es.caib.sistrahelp.core.api.model.Soporte;
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

	@Override
	public List<Soporte> load(final int first, final int pageSize, final String sortField, final SortOrder sortOrder,
			final Map<String, Object> filters) {

		filtros.setSortField(sortField);
		filtros.setSortOrder(sortOrder.name());
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
	public Object getRowKey(final Soporte evento) {
		return evento.getCodigo();
	}

	public List<Soporte> getLista() {
		return lista;
	}

	public void setLista(final List<Soporte> list) {
		this.lista = list;
	}
}
