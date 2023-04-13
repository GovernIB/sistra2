package es.caib.sistrahelp.core.api.model;

import java.util.List;

/**
 * La clase ResultadoAuditoriaPago.
 */
public final class ResultadoErroresPorTramiteCM extends ModelApi {

	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de ResultadoAuditoriaPago.
	 */
	public ResultadoErroresPorTramiteCM() {
		super();
	}

	private Long numElementos;

	private List<ErroresPorTramiteCM> listaErroresCM;

	/**
	 * @return the listaErroresCM
	 */
	public final List<ErroresPorTramiteCM> getListaErroresCM() {
		return listaErroresCM;
	}

	/**
	 * @param listaErroresCM the listaErroresCM to set
	 */
	public final void setListaErroresCM(List<ErroresPorTramiteCM> listaErroresCM) {
		this.listaErroresCM = listaErroresCM;
	}

	public Long getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(final Long numElementos) {
		this.numElementos = numElementos;
	}

}
