package es.caib.sistra2.commons.plugins.registro.api;

import java.io.Serializable;
import java.util.Date;

/**
 * Datos de oficina provinientes del registro
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class DatosOrigen implements Serializable {

    /** codigo DIR3 de entidad */
	private String codigoEntidad;

    /** Codigo de oficina sobre la que se realiza el asiento */
	private String codigoOficinaRegistro;

	/** Numero de registro del apunte */
	private String numeroRegistro;

	/** Fecha y hora del apunte registra */
	private Date fechaEntradaRegistro;

	/** Tipo de registro (E/S) */
	private Character tipoRegistro;

	public String getCodigoEntidad() {
		return codigoEntidad;
	}

	public void setCodigoEntidad(String codigoEntidad) {
		this.codigoEntidad = codigoEntidad;
	}

	public String getCodigoOficinaRegistro() {
		return codigoOficinaRegistro;
	}

	public void setCodigoOficinaRegistro(String codigoOficinaRegistro) {
		this.codigoOficinaRegistro = codigoOficinaRegistro;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public Date getFechaEntradaRegistro() {
		return fechaEntradaRegistro;
	}

	public void setFechaEntradaRegistro(Date fechaEntradaRegistro) {
		this.fechaEntradaRegistro = fechaEntradaRegistro;
	}

	public Character getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(Character tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

}
