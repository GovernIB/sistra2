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

}
