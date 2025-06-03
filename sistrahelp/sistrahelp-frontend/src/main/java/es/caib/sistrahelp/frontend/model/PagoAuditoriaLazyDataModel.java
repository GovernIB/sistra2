package es.caib.sistrahelp.frontend.model;

import java.util.List;
import java.util.Map;

import es.caib.sistrahelp.core.api.model.EventoCM;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
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

	public int count(Map<String, FilterMeta> filterBy) {
		return helpDeskService.countAuditoriaPago(filtros).intValue();
	}

	@Override
	public List<PagoAuditoria> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

		if (sortBy != null && !sortBy.isEmpty()) {
			SortMeta sortMeta = sortBy.values().iterator().next();
			SortOrder sortOrder = sortMeta.getOrder();
			if (sortOrder != null) {
				filtros.setSortOrder(sortOrder.name());
			}
			filtros.setSortField(sortMeta.getField());
		}
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
	public String getRowKey(final PagoAuditoria evento) {
		return evento.getCodigoPago().toString();
	}

	public List<PagoAuditoria> getLista() {
		return lista;
	}

	public void setLista(final List<PagoAuditoria> lista) {
		this.lista = lista;
	}
}
