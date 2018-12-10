package es.caib.sistrahelp.core.api.model;

import java.util.List;

/**
 * La clase ResultadoAuditoriaPago.
 */
public final class ResultadoAuditoriaPersistencia extends ModelApi {

	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de ResultadoAuditoriaPago.
	 */
	public ResultadoAuditoriaPersistencia() {
		super();
	}

	private Long numElementos;

	private List<PersistenciaAuditoria> listaPersistencia;

	public List<PersistenciaAuditoria> getListaPersistencia() {
		return listaPersistencia;
	}

	public void setListaPersistencia(final List<PersistenciaAuditoria> listaPersistencia) {
		this.listaPersistencia = listaPersistencia;
	}

	public Long getNumElementos() {
		return numElementos;
	}

	public void setNumElementos(final Long numElementos) {
		this.numElementos = numElementos;
	}

}
