package es.caib.sistramit.rest.api.externa.v1;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Tramite en persistencia
 *
 * @author Indra
 *
 */
@ApiModel(value = "RTramiteFinalizado", description = "Tramite finalizado")
public final class RTramiteFinalizado {

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
	@ApiModelProperty(value = "Id procedimiento SIA")
	private String idProcedimientoSIA;
	@ApiModelProperty(value = "Fecha finalización")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private Date fechaFin;
	@ApiModelProperty(value = "Tipo autenticación: Autenticado (c) / Anónimo (a).")
	private String autenticacion;
	@ApiModelProperty(value = "Metodo Autenticacion (ANONIMO / CLAVE_CERTIFICADO / CLAVE_PIN / CLAVE_PERMANENTE / CLAVE_MOVIL)")
	private String metodoAutenticacion;
	@ApiModelProperty(value = "Nif")
	private String nif;
	@ApiModelProperty(value = "Nombre y apellidos")
	private String nombreApellidos;
	@ApiModelProperty(value = "Número registro")
	private String numeroRegistro;

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

	public String getDescripcionTramite() {
		return descripcionTramite;
	}

	public void setDescripcionTramite(final String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método de acceso a idProcedimientoSIA.
	 *
	 * @return idProcedimientoSIA
	 */
	public String getIdProcedimientoSIA() {
		return idProcedimientoSIA;
	}

	/**
	 * Método para establecer idProcedimientoSIA.
	 *
	 * @param idProcedimientoSIA
	 *                               idProcedimientoSIA a establecer
	 */
	public void setIdProcedimientoSIA(final String idProcedimientoSIA) {
		this.idProcedimientoSIA = idProcedimientoSIA;
	}

	/**
	 * Método de acceso a fechaFin.
	 *
	 * @return fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * Método para establecer fechaFin.
	 *
	 * @param fechaFin
	 *                     fechaFin a establecer
	 */
	public void setFechaFin(final Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Método de acceso a autenticacion.
	 *
	 * @return autenticacion
	 */
	public String getAutenticacion() {
		return autenticacion;
	}

	/**
	 * Método para establecer autenticacion.
	 *
	 * @param autenticacion
	 *                          autenticacion a establecer
	 */
	public void setAutenticacion(final String autenticacion) {
		this.autenticacion = autenticacion;
	}

	/**
	 * Método de acceso a metodoAutenticacion.
	 *
	 * @return metodoAutenticacion
	 */
	public String getMetodoAutenticacion() {
		return metodoAutenticacion;
	}

	/**
	 * Método para establecer metodoAutenticacion.
	 *
	 * @param metodoAutenticacion
	 *                                metodoAutenticacion a establecer
	 */
	public void setMetodoAutenticacion(final String metodoAutenticacion) {
		this.metodoAutenticacion = metodoAutenticacion;
	}

	/**
	 * Método de acceso a nif.
	 *
	 * @return nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * Método para establecer nif.
	 *
	 * @param nif
	 *                nif a establecer
	 */
	public void setNif(final String nif) {
		this.nif = nif;
	}

	/**
	 * Método de acceso a nombreApellidos.
	 *
	 * @return nombreApellidos
	 */
	public String getNombreApellidos() {
		return nombreApellidos;
	}

	/**
	 * Método para establecer nombreApellidos.
	 *
	 * @param nombreApellidos
	 *                            nombreApellidos a establecer
	 */
	public void setNombreApellidos(final String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}

	/**
	 * Método de acceso a numeroRegistro.
	 *
	 * @return numeroRegistro
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	/**
	 * Método para establecer numeroRegistro.
	 *
	 * @param numeroRegistro
	 *                           numeroRegistro a establecer
	 */
	public void setNumeroRegistro(final String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

}
