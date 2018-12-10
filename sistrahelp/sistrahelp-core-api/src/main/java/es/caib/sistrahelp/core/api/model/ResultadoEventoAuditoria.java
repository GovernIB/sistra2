package es.caib.sistrahelp.core.api.model;

import java.util.List;

/**
 * La clase ResultadoEventoAuditoria.
 */
public final class ResultadoEventoAuditoria extends ModelApi {

	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de EventoAuditoriaTramitacion.
	 */
	public ResultadoEventoAuditoria() {
		super();
	}

	/**
	 * id sesion tramitacion.
	 */
	private Long numElementos;

	private List<EventoAuditoriaTramitacion> listaEventos;

	public Long getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(final Long numElementos) {
		this.numElementos = numElementos;
	}

	public List<EventoAuditoriaTramitacion> getListaEventos() {
		return listaEventos;
	}

	public void setListaEventos(final List<EventoAuditoriaTramitacion> listaEventos) {
		this.listaEventos = listaEventos;
	}

}
