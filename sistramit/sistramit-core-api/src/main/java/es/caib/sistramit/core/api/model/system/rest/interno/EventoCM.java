package es.caib.sistramit.core.api.model.system.rest.interno;

import java.io.Serializable;

/**
 * La clase EventoCM.
 */
@SuppressWarnings("serial")
public final class EventoCM implements Serializable {
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
	 * Crea una nueva instancia de EventoCM
	 */
	public EventoCM(final String tipoEvento, final Long concurrencias) {
		super();
		this.tipoEvento = tipoEvento;
		this.concurrencias = concurrencias;
		this.porc = null;
	}

	/**
	 * Crea una nueva instancia de EventoCM
	 */
	public EventoCM(final String tipoEvento, final Long concurrencias, final Double porc) {
		super();
		this.tipoEvento = tipoEvento;
		this.concurrencias = concurrencias;
		this.porc = porc;
	}

	/**
	 * Crea una nueva instancia de EventoCM
	 */
	public EventoCM() {
		super();
	}

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
