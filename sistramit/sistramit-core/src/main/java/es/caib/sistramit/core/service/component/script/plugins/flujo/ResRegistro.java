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

	/** Entidad sobre el que se realiza el asiento . */
	private String codigoEntidad;

	/** codigo del organo sobre el que se realiza el asiento . */
	private String codigoOrganoDestino;

	/** Oficina. */
	private String oficina;

	/** Libro. */
	private String libro;

	/** Expediente (para tramites continuacion). */
	private String numeroExpediente;

	/** texto relativo al expone (solo para solicitud generica). */
	private String textoExpone;

	/** texto relativo al solicita (solo para solicitud generica). */
	private String textoSolicita;

	/** extracto del asiento . */
	private String extracto;

	/** código SIA . */
	private String codigoSIA;

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
	 *                    oficina a establecer
	 */
	@Override
	public void setOficina(final String oficina) {
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
	 *                  libro a establecer
	 */
	@Override
	public void setLibro(final String libro) {
		this.libro = libro;
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
	 *                             numeroExpediente a establecer
	 */
	@Override
	public void setNumeroExpediente(final String numeroExpediente) {
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
	 *                        textoExpone a establecer
	 */
	@Override
	public void setExpone(final String textoExpone) {
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
	 *                          textoSolicita a establecer
	 */
	@Override
	public void setSolicita(final String textoSolicita) {
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
	 *                                codigoOrganoDestino a establecer
	 */
	@Override
	public void setCodigoOrganoDestino(final String codigoOrganoDestino) {
		this.codigoOrganoDestino = codigoOrganoDestino;
	}

	/**
	 * Método de acceso al extracto
	 *
	 * @return the extracto
	 */
	public String getExtracto() {
		return extracto;
	}

	/**
	 * Método para establecer el extracto.
	 *
	 * @param extracto
	 *                     extracto a establecer
	 */
	@Override
	public void setExtracto(final String extracto) {
		this.extracto = extracto;
	}

	/**
	 * Método de acceso a codigoEntidad.
	 *
	 * @return codigoEntidad
	 */
	public String getCodigoEntidad() {
		return codigoEntidad;
	}

	/**
	 * Método para establecer codigoEntidad.
	 *
	 * @param codigoEntidad
	 *                          codigoEntidad a establecer
	 */
	@Override
	public void setCodigoEntidad(final String codigoEntidad) {
		this.codigoEntidad = codigoEntidad;
	}

	/**
	 * Método de acceso a codigoSIA.
	 * 
	 * @return codigoSIA
	 */
	public String getCodigoSIA() {
		return codigoSIA;
	}

	/**
	 * Método para establecer codigoSIA.
	 * 
	 * @param codigoSIA
	 *                      codigoSIA a establecer
	 */
	public void setCodigoSIA(final String codigoSIA) {
		this.codigoSIA = codigoSIA;
	}

}
