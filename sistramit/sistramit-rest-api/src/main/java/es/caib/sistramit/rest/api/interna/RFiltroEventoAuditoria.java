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
@ApiModel(value = "RFiltroEventoAuditoria", description = "Filtros para la auditoria de tramites")
public class RFiltroEventoAuditoria {

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

	@ApiModelProperty(value = "Tipo Evento")
	private String evento;

	@ApiModelProperty(value = "Id. Tramite")
	private String idTramite;

	@ApiModelProperty(value = "Version tramite")
	private Integer versionTramite;

	@ApiModelProperty(value = "Cod. Procedimiento")
	private String idProcedimientoCP;

	@ApiModelProperty(value = "Cod. Procedimiento SIA")
	private String idProcedimientoSIA;

	@ApiModelProperty(value = "Error plataforma")
	private boolean errorPlataforma;

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

	public String getEvento() {
		return evento;
	}

	public void setEvento(final String evento) {
		this.evento = evento;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	public Integer getVersionTramite() {
		return versionTramite;
	}

	public void setVersionTramite(final Integer versionTramite) {
		this.versionTramite = versionTramite;
	}

	public String getIdProcedimientoCP() {
		return idProcedimientoCP;
	}

	public void setIdProcedimientoCP(final String idProcedimientoCP) {
		this.idProcedimientoCP = idProcedimientoCP;
	}

	public String getIdProcedimientoSIA() {
		return idProcedimientoSIA;
	}

	public void setIdProcedimientoSIA(final String idProcedimientoSIA) {
		this.idProcedimientoSIA = idProcedimientoSIA;
	}

	public boolean isErrorPlataforma() {
		return errorPlataforma;
	}

	public void setErrorPlataforma(final boolean errorPlataforma) {
		this.errorPlataforma = errorPlataforma;
	}

	public boolean isSoloContar() {
		return soloContar;
	}

	public void setSoloContar(final boolean soloContar) {
		this.soloContar = soloContar;
	}

}
