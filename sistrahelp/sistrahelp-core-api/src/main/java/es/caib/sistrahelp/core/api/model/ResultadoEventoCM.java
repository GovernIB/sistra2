package es.caib.sistrahelp.core.api.model;

import java.util.List;

/**
 * La clase ResultadoAuditoriaPago.
 */
public final class ResultadoEventoCM extends ModelApi {

	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de ResultadoAuditoriaPago.
	 */
	public ResultadoEventoCM() {
		super();
	}

	private Long numElementos;

	private List<EventoCM> listaEventosCM;

	public List<EventoCM> getListaEventosCM() {
		return listaEventosCM;
	}

	public void setListaEventosCM(final List<EventoCM> listaEventosCM) {
		this.listaEventosCM = listaEventosCM;
	}

	public Long getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(final Long numElementos) {
		this.numElementos = numElementos;
	}

}
