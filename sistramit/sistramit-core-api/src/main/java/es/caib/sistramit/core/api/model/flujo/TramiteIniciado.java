package es.caib.sistramit.core.api.model.flujo;

import java.util.Date;

/**
 * Trámite iniciado.
 *
 * @author Indra
 */
@SuppressWarnings("serial")
public final class TramiteIniciado implements ModelApi {

	/** Id Sesión. */
	private String idSesion;

	/** Idioma. */
	private String idioma;

	/** Fecha inicio. */
	private Date fechaInicio;

	/** Fecha último acceso. */
	private Date fechaUltimoAcceso;

	/** Fecha caducidad. */
	private Date fechaCaducidad;

	public String getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaUltimoAcceso() {
		return fechaUltimoAcceso;
	}

	public void setFechaUltimoAcceso(Date fechaUltimoAcceso) {
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
}
