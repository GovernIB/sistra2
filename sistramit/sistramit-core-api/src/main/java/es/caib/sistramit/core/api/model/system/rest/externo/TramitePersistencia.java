package es.caib.sistramit.core.api.model.system.rest.externo;

import java.io.Serializable;
import java.util.Date;

/**
 * Tramite Persistencia. (RestApiExternaService)
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class TramitePersistencia implements Serializable {

	public TramitePersistencia(final String idSesionTramitacion, final String idioma, final String descripcionTramite,
			final String idTramite, final int versionTramite, final Date fechaInicio, final Date fechaUltimoAcceso) {
		super();
		this.idSesionTramitacion = idSesionTramitacion;
		this.idioma = idioma;
		this.descripcionTramite = descripcionTramite;
		this.idTramite = idTramite;
		this.versionTramite = versionTramite;
		this.fechaInicio = fechaInicio;
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

	public TramitePersistencia() {
		super();
	}

	private String idSesionTramitacion;
	private String idioma;
	private String descripcionTramite;
	private String idTramite;
	private int versionTramite;
	private Date fechaInicio;
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
