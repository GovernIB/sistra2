package es.caib.sistramit.core.service.component.script.plugins.flujo;

import es.caib.sistramit.core.service.model.script.flujo.ResRegistroInt;

/**
 *
 * Datos que se pueden establecer dinámicamente al registrar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResRegistro implements ResRegistroInt {

	/** Oficina. */
	private String oficina;

	/** Libro. */
	private String libro;

	/** Tipo asunto. */
	private String tipoAsunto;

	/** Expediente (para tramites continuacion). */
	private String numeroExpediente;

	/** texto relativo al expone (solo para solicitud generica). */
	private String textoExpone;

	/** texto relativo al solicita (solo para solicitud generica). */
	private String textoSolicita;

	/** codigo del organo sobre el que se realiza el asiento . */
	private String codigoOrganoDestino;

	@Override
	public String getPluginId() {
		return ID;
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
	@Override
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}

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
	@Override
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
	@Override
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
	@Override
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	/**
	 * Método de acceso a textoExpone.
	 *
	 * @return textoExpone
	 */
	public String getExpone() {
		return textoExpone;
	}

	/**
	 * Método para establecer textoExpone.
	 *
	 * @param textoExpone
	 *            textoExpone a establecer
	 */
	@Override
	public void setExpone(String textoExpone) {
		this.textoExpone = textoExpone;

	}

	/**
	 * Método de acceso a textoSolicita.
	 *
	 * @return textoSolicita
	 */
	public String getSolicita() {
		return textoSolicita;
	}

	/**
	 * Método para establecer textoSolicita.
	 *
	 * @param textoSolicita
	 *            textoSolicita a establecer
	 */
	@Override
	public void setSolicita(String textoSolicita) {
		this.textoSolicita = textoSolicita;
	}

	/**
	 * Método de acceso a codigoOrganoDestino.
	 *
	 * @return codigoOrganoDestino
	 */
	public String getCodigoOrganoDestino() {
		return codigoOrganoDestino;
	}

	/**
	 * Método para establecer codigoOrganoDestino.
	 *
	 * @param codigoOrganoDestino
	 *            codigoOrganoDestino a establecer
	 */
	@Override
	public void setCodigoOrganoDestino(String codigoOrganoDestino) {
		this.codigoOrganoDestino = codigoOrganoDestino;
	}



}
