package es.caib.sistrahelp.frontend.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.caib.sistrahelp.core.api.model.FiltroAuditoriaPago;
import es.caib.sistrahelp.core.api.model.FiltroPaginacion;
import es.caib.sistrahelp.core.api.model.PagoAuditoria;
import es.caib.sistrahelp.core.api.service.HelpDeskService;

public class PagoAuditoriaLazyDataModel extends LazyDataModel<PagoAuditoria> {

	public PagoAuditoriaLazyDataModel(final HelpDeskService helpDeskService, final Long pRowCount,
			final FiltroAuditoriaPago pFiltros) {
		super();
		this.helpDeskService = helpDeskService;
		this.setRowCount(pRowCount.intValue());
		this.filtros = pFiltros;
	}

	private static final long serialVersionUID = 1L;

	private final HelpDeskService helpDeskService;

	private final FiltroAuditoriaPago filtros;

	private List<PagoAuditoria> lista;

	@Override
	public List<PagoAuditoria> load(final int first, final int pageSize, final String sortField,
			final SortOrder sortOrder, final Map<String, Object> filters) {
		filtros.setSortField(sortField);
		filtros.setSortOrder(sortOrder.name());
		setLista(helpDeskService.obtenerAuditoriaPago(filtros, new FiltroPaginacion(first, pageSize)));

		return getLista();

	}

	@Override
	public PagoAuditoria getRowData(final String rowKey) {
		for (final PagoAuditoria pago : getLista()) {
			if (pago.getCodigoPago().equals(Long.valueOf(rowKey)))
				return pago;
		}

		return null;
	}

	@Override
	public Object getRowKey(final PagoAuditoria evento) {
		return evento.getCodigoPago();
	}

	public List<PagoAuditoria> getLista() {
		return lista;
	}

	public void setLista(final List<PagoAuditoria> lista) {
		this.lista = lista;
	}
}
