package es.caib.sistramit.rest.api.interna;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Filtros para la perdida de clave
 *
 * @author Indra
 *
 */
@ApiModel(value = "RFiltroPerdidaClave", description = "Filtros para la perdida de clave de tramitacion")
public class RFiltroPerdidaClave {

	@ApiModelProperty(value = "Lista de areas")
	private List<String> listaAreas;

	@ApiModelProperty(value = "Dato Formulario")
	private String datoFormulario;

	@ApiModelProperty(value = "Fecha desde")
	private Date fechaDesde;

	@ApiModelProperty(value = "Fecha Hasta")
	private Date fechaHasta;

	@ApiModelProperty(value = "Id. Tramite")
	private String idTramite;

	@ApiModelProperty(value = "Version tramite")
	private Integer versionTramite;

	@ApiModelProperty(value = "Cod. Procedimiento")
	private String idProcedimientoCP;

	public List<String> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(final List<String> listaAreas) {
		this.listaAreas = listaAreas;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public String getDatoFormulario() {
		return datoFormulario;
	}

	public void setDatoFormulario(final String datoFormulario) {
		this.datoFormulario = datoFormulario;
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

}
