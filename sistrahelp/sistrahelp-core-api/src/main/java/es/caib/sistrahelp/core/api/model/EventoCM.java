package es.caib.sistrahelp.core.api.model;

/**
 * La clase EventoCM.
 */

public final class EventoCM extends ModelApi {

	private static final long serialVersionUID = 1L;

	/**
	 * id tramite.
	 */
	private String tipoEvento;
	/**
	 * concurrencias
	 */
	private Long concurrencias;

	/**
	 * porcentage
	 */
	private Double porc;

	/**
	 * @return the idTramite
	 */
	public final String getTipoEvento() {
		return tipoEvento;
	}

	/**
	 * @param idTramite the idTramite to set
	 */
	public final void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
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

	/**
	 * @return the porc
	 */
	public final Double getPorc() {
		return porc;
	}

	/**
	 * @param porc the porc to set
	 */
	public final void setPorc(Double porc) {
		this.porc = porc;
	}

}
