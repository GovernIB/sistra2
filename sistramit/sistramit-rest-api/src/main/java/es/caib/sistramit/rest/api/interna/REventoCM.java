package es.caib.sistramit.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Eventos totales cuadro de mando
 *
 * @author Indra
 *
 */
@ApiModel(value = "REventoCM", description = "Eventos totales cuadro de mando")
public class REventoCM {
	/**
	 * Tipo evento
	 */
	@ApiModelProperty(value = "Tipo evento")
	private String tipoEvento;

	/**
	 * Concurrencias.
	 */
	@ApiModelProperty(value = "Concurrencias")
	private Long concurrencias;

	/**
	 * Concurrencias.
	 */
	@ApiModelProperty(value = "Porcentage")
	private Double porc;

	public final String getTipoEvento() {
		return tipoEvento;
	}

	public final void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public final Long getConcurrencias() {
		return concurrencias;
	}

	public final void setConcurrencias(Long concurrencias) {
		this.concurrencias = concurrencias;
	}

	public final Double getPorc() {
		return porc;
	}

	public final void setPorc(Double porc) {
		this.porc = porc;
	}

}
