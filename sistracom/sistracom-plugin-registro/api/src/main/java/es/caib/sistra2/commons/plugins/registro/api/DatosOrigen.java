package es.caib.sistra2.commons.plugins.registro.api;

import java.io.Serializable;
import java.util.Date;

import es.caib.sistra2.commons.plugins.registro.api.types.TypeRegistro;

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

	/** Libro de la oficina de registro sobre la que realiza el asiento */
	private String libroOficinaRegistro;

	/** Numero de registro del apunte */
	private String numeroRegistro;

	/** Fecha y hora del apunte registra */
	private Date fechaEntradaRegistro;

	/** Tipo de registro (E/S) */
	private TypeRegistro tipoRegistro;

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


	public String getLibroOficinaRegistro() {
		return libroOficinaRegistro;
	}

	public void setLibroOficinaRegistro(String libroOficinaRegistro) {
		this.libroOficinaRegistro = libroOficinaRegistro;
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

	public TypeRegistro getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(TypeRegistro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

}
