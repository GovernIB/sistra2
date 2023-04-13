package es.caib.sistramit.core.api.model.system.rest.interno;

import java.io.Serializable;

/**
 * La clase ErroresPorTramiteCM.
 */
@SuppressWarnings("serial")
public final class ErroresPorTramiteCMRe implements Serializable {

	private String idExcepcion;

	private Long concurrencias;

	public ErroresPorTramiteCMRe(String idExcepcion, Long concurrencias) {
		super();
		this.idExcepcion = idExcepcion;
		this.concurrencias = concurrencias;
	}

	public ErroresPorTramiteCMRe() {
		super();
	}

	/**
	 * @return the idExcepcion
	 */
	public final String getIdExcepcion() {
		return idExcepcion;
	}

	/**
	 * @param idExcepcion the idExcepcion to set
	 */
	public final void setIdExcepcion(String idExcepcion) {
		this.idExcepcion = idExcepcion;
	}

	/**
	 * @return the concurrencias
	 */
	public final Long getConcurrencias() {
		return concurrencias;
	}

	/**
	 * @param concurrencias the concurrencias to set
	 */
	public final void setConcurrencias(Long concurrencias) {
		this.concurrencias = concurrencias;
	}
}
