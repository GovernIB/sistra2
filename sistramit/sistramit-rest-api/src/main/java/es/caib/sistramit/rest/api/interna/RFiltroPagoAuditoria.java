package es.caib.sistramit.rest.api.interna;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Filtros para la auditoria de pagos
 *
 * @author Indra
 *
 */
@ApiModel(value = "RFiltroPagoAuditoria", description = "Filtros para la auditoria de pagos")
public class RFiltroPagoAuditoria {

	@ApiModelProperty(value = "Lista de areas")
	private List<String> listaAreas;

	@ApiModelProperty(value = "id.Sesion Tramitacion")
	private String idSesionTramitacion;

	@ApiModelProperty(value = "Nif")
	private String nif;

	@ApiModelProperty(value = "Fecha desde")
	private Date fechaDesde;

	@ApiModelProperty(value = "Fecha Hasta")
	private Date fechaHasta;

	@ApiModelProperty(value = "Tipo Acceso")
	private String acceso;

	@ApiModelProperty(value = "Solo contar numero de elementos")
	private boolean soloContar;

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

	public boolean isSoloContar() {
		return soloContar;
	}

	public void setSoloContar(final boolean soloContar) {
		this.soloContar = soloContar;
	}

	public String getAcceso() {
		return acceso;
	}

	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}

}
