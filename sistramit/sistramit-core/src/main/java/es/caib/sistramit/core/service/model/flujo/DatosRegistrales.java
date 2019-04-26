package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

/**
 * Datos registrales.
 */
@SuppressWarnings("serial")
public final class DatosRegistrales implements Serializable {

	/** Oficina. */
	private String oficina;

	/** Libro. */
	private String libro;

	/** Tipo asunto. */
	private String tipoAsunto;

	/** Expediente (para tramites continuacion). */
	private String numeroExpediente;

	/** texto Expone (para solicitudes genericas). */
	private String textoExpone;

	/** texto Solicita (para solicitudes genericas). */
	private String textoSolicita;

	/** codigo de la unidad sobre la que se realizara el asiento */
	private String codigoOrganoDestino;

	/**
	 * Método de acceso a libro.
	 *
	 * @return libro
	 */
	public String getLibro() {
		return libro;
	}

	/**
	 * Método para establecer libro.
	 *
	 * @param libro
	 *            libro a establecer
	 */
	public void setLibro(String libro) {
		this.libro = libro;
	}

	/**
	 * Método de acceso a tipoAsunto.
	 *
	 * @return tipoAsunto
	 */
	public String getTipoAsunto() {
		return tipoAsunto;
	}

	/**
	 * Método para establecer tipoAsunto.
	 *
	 * @param tipoAsunto
	 *            tipoAsunto a establecer
	 */
	public void setTipoAsunto(String tipoAsunto) {
		this.tipoAsunto = tipoAsunto;
	}

	/**
	 * Método de acceso a numeroExpediente.
	 *
	 * @return numeroExpediente
	 */
	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	/**
	 * Método para establecer numeroExpediente.
	 *
	 * @param numeroExpediente
	 *            numeroExpediente a establecer
	 */
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	/**
	 * Método de acceso a oficina.
	 *
	 * @return oficina
	 */
	public String getOficina() {
		return oficina;
	}

	/**
	 * Método para establecer oficina.
	 *
	 * @param oficina
	 *            oficina a establecer
	 */
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}


	/**
	 * Método de acceso a textoExpone.
	 *
	 * @return textoExpone
	 */
	public String getTextoExpone() {
		return textoExpone;
	}

	/**
	 * Método para establecer textoExpone.
	 *
	 * @param textoExpone
	 *            textoExpone a establecer
	 */
	public void setTextoExpone(String textoExpone) {
		this.textoExpone = textoExpone;
	}

	/**
	 * Método de acceso a textoSolicita.
	 *
	 * @return textoSolicita
	 */
	public String getTextoSolicita() {
		return textoSolicita;
	}

	/**
	 * Método para establecer textoSolicita.
	 *
	 * @param textoSolicita
	 *            textoSolicita a establecer
	 */
	public void setTextoSolicita(String textoSolicita) {
		this.textoSolicita = textoSolicita;
	}

	/**
	 * Método de acceso a codigoOrganoDestino.
	 * @return codigoOrganoDestino
	 */
	public String getCodigoOrganoDestino() {
		return codigoOrganoDestino;
	}

	/**
	 * Método para establecer codigoOrganoDestino.
	 * @param codigoOrganoDestino
	 * 			  codigoOrganoDestino a establecer
	 */
	public void setCodigoOrganoDestino(String codigoOrganoDestino) {
		this.codigoOrganoDestino = codigoOrganoDestino;
	}


}
