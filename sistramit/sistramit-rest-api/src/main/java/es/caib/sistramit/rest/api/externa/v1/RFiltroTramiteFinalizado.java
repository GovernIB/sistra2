package es.caib.sistramit.rest.api.externa.v1;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Filtros para la recuperacion de tramites finalizados
 *
 * @author Indra
 *
 */
@ApiModel(value = "RFiltroTramiteFinalizado", description = "Filtros para la recuperacion de tramites finalizados")
public class RFiltroTramiteFinalizado {

	@ApiModelProperty(value = "Nif", required = true)
	private String nif;

	@ApiModelProperty(value = "Id sesión tramitación", required = true)
	private String idSesionTramitacion;

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

	/**
	 * Método de acceso a idSesionTramitacion.
	 * 
	 * @return idSesionTramitacion
	 */
	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	/**
	 * Método para establecer idSesionTramitacion.
	 * 
	 * @param idSesionTramitacion
	 *                                idSesionTramitacion a establecer
	 */
	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

}
