package es.caib.sistramit.rest.api.externa.v1;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Filtros para la recuperacion de tramites en persistencia
 *
 * @author Indra
 *
 */
@ApiModel(value = "RFiltroTramitePersistencia", description = "Filtros para la recuperacion de tramites en persistencia")
public class RFiltroTramitePersistencia {

	@ApiModelProperty(value = "Nif")
	private String nif;

	@ApiModelProperty(value = "Fecha desde")
	private Date fechaDesde;

	@ApiModelProperty(value = "Fecha Hasta")
	private Date fechaHasta;

	public String getNif() {
		return nif;
	}

	public void setNif(final String nif) {
		this.nif = nif;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(final Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(final Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

}
