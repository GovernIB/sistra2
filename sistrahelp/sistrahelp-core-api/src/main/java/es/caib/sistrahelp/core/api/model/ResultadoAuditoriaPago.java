package es.caib.sistrahelp.core.api.model;

import java.util.List;

/**
 * La clase ResultadoAuditoriaPago.
 */
public final class ResultadoAuditoriaPago extends ModelApi {

	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de ResultadoAuditoriaPago.
	 */
	public ResultadoAuditoriaPago() {
		super();
	}

	private Long numElementos;

	private List<PagoAuditoria> listaPagos;

	public List<PagoAuditoria> getListaPagos() {
		return listaPagos;
	}

	public void setListaPagos(final List<PagoAuditoria> listaPagos) {
		this.listaPagos = listaPagos;
	}

	public Long getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(final Long numElementos) {
		this.numElementos = numElementos;
	}

}
