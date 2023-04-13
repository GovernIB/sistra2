package es.caib.sistrahelp.core.api.model;

/**
 * La clase EventoCM.
 */

public final class ErroresPorTramiteCMRe extends ModelApi {

	private static final long serialVersionUID = 1L;

	private String idExcepcion;

	private Long concurrencias;

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
