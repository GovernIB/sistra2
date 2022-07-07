package es.caib.sistra2.commons.plugins.dominio.rest;

import java.io.Serializable;
import java.util.Date;

/**
 * Datos del resultado de un apunte registral
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ResultadoRegistro implements Serializable {

    /** Número de registro */
    private String numeroRegistro;

    /** Fecha del apunte registral */
    private Date fechaRegistro;
    
    

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
     *            numeroRegistro a establecer
     */
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	/**
     * Método de acceso a fechaRegistro.
     * 
     * @return fechaRegistro
     */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
     * Método para establecer fechaRegistro.
     * 
     * @param fechaRegistro
     *            fechaRegistro a establecer
     */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}
