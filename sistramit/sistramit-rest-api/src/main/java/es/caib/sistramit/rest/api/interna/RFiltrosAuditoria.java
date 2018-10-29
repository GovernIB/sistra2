package es.caib.sistramit.rest.api.interna;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Filtros para la auditoria de tramites
 *
 * @author Indra
 *
 */
@ApiModel(value = "RFiltrosAuditoria", description = "Filtros para la auditoria de tramites")
public class RFiltrosAuditoria {

	@ApiModelProperty(value = "")
	private List<String> listaAreas;

	@ApiModelProperty(value = "")
	private String idSesionTramitacion;

	@ApiModelProperty(value = "")
	private String nif;

	@ApiModelProperty(value = "")
	private Date fechaDesde;

	@ApiModelProperty(value = "")
	private Date fechaHasta;

	@ApiModelProperty(value = "")
	private String evento;

	public List<String> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(final List<String> listaAreas) {
		this.listaAreas = listaAreas;
	}

	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

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

	public String getEvento() {
		return evento;
	}

	public void setEvento(final String evento) {
		this.evento = evento;
	}

}
