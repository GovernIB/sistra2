package es.caib.sistramit.rest.api.externa.v1;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Tramite en persistencia
 *
 * @author Indra
 *
 */
@ApiModel(value = "RTramitePersistencia", description = "Tramite en persistencia")
public final class RTramitePersistencia {

	@ApiModelProperty(value = "Id. Sesion Tramitacion")
	private String idSesionTramitacion;
	@ApiModelProperty(value = "Idioma")
	private String idioma;
	@ApiModelProperty(value = "Id. Tramite")
	private String idTramite;
	@ApiModelProperty(value = "Version Tramite")
	private int versionTramite;
	@ApiModelProperty(value = "Descripcion Tramite")
	private String descripcionTramite;
	@ApiModelProperty(value = "Fecha Inicio")
	private Date fechaInicio;
	@ApiModelProperty(value = "Fecha Ultimo Acceso")
	private Date fechaUltimoAcceso;

	public String getIdSesionTramitacion() {
		return idSesionTramitacion;
	}

	public void setIdSesionTramitacion(final String idSesionTramitacion) {
		this.idSesionTramitacion = idSesionTramitacion;
	}

	public String getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	public int getVersionTramite() {
		return versionTramite;
	}

	public void setVersionTramite(final int versionTramite) {
		this.versionTramite = versionTramite;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(final Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getDescripcionTramite() {
		return descripcionTramite;
	}

	public void setDescripcionTramite(final String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}

	public Date getFechaUltimoAcceso() {
		return fechaUltimoAcceso;
	}

	public void setFechaUltimoAcceso(final Date fechaUltimoAcceso) {
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

}
