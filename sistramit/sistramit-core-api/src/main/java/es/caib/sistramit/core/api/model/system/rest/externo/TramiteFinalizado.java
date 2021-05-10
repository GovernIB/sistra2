package es.caib.sistramit.core.api.model.system.rest.externo;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistramit.core.api.model.security.types.TypeAutenticacion;
import es.caib.sistramit.core.api.model.security.types.TypeMetodoAutenticacion;

/**
 * Tramite finalizado. (RestApiExternaService)
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class TramiteFinalizado implements Serializable {

	/** Id sesión tramitación. */
	private String idSesionTramitacion;
	/** Idioma. */
	private String idioma;
	/** Id trámite. */
	private String idTramite;
	/** Version Tramite. */
	private int versionTramite;
	/** Descripcion Tramite */
	private String descripcionTramite;
	/** Id procedimiento SIA */
	private String idProcedimientoSIA;
	/** Fecha finalización. */
	private Date fechaFin;
	/** Autenticación. */
	private TypeAutenticacion autenticacion;
	/** Método autenticación. */
	private TypeMetodoAutenticacion metodoAutenticacion;
	/** Nif. */
	private String nif;
	/** Nombre y apellidos. */
	private String nombreApellidos;
	/** Número registro. */
	private String numeroRegistro;

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

	/**
	 * Método de acceso a idioma.
	 * 
	 * @return idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Método para establecer idioma.
	 * 
	 * @param idioma
	 *                   idioma a establecer
	 */
	public void setIdioma(final String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Método de acceso a idTramite.
	 * 
	 * @return idTramite
	 */
	public String getIdTramite() {
		return idTramite;
	}

	/**
	 * Método para establecer idTramite.
	 * 
	 * @param idTramite
	 *                      idTramite a establecer
	 */
	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	/**
	 * Método de acceso a versionTramite.
	 * 
	 * @return versionTramite
	 */
	public int getVersionTramite() {
		return versionTramite;
	}

	/**
	 * Método para establecer versionTramite.
	 * 
	 * @param versionTramite
	 *                           versionTramite a establecer
	 */
	public void setVersionTramite(final int versionTramite) {
		this.versionTramite = versionTramite;
	}

	/**
	 * Método de acceso a descripcionTramite.
	 * 
	 * @return descripcionTramite
	 */
	public String getDescripcionTramite() {
		return descripcionTramite;
	}

	/**
	 * Método para establecer descripcionTramite.
	 * 
	 * @param descripcionTramite
	 *                               descripcionTramite a establecer
	 */
	public void setDescripcionTramite(final String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
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
	public TypeAutenticacion getAutenticacion() {
		return autenticacion;
	}

	/**
	 * Método para establecer autenticacion.
	 * 
	 * @param autenticacion
	 *                          autenticacion a establecer
	 */
	public void setAutenticacion(final TypeAutenticacion autenticacion) {
		this.autenticacion = autenticacion;
	}

	/**
	 * Método de acceso a metodoAutenticacion.
	 * 
	 * @return metodoAutenticacion
	 */
	public TypeMetodoAutenticacion getMetodoAutenticacion() {
		return metodoAutenticacion;
	}

	/**
	 * Método para establecer metodoAutenticacion.
	 * 
	 * @param metodoAutenticacion
	 *                                metodoAutenticacion a establecer
	 */
	public void setMetodoAutenticacion(final TypeMetodoAutenticacion metodoAutenticacion) {
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
