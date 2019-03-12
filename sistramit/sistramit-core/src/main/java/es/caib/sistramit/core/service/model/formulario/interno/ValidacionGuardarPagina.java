package es.caib.sistramit.core.service.model.formulario.interno;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.formulario.MensajeValidacion;

/**
 * Validación guardar página.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ValidacionGuardarPagina implements Serializable {

	/**
	 * Mensaje validación.
	 */
	private MensajeValidacion mensajeValidacion;

	/**
	 * Id campo asociado al mensaje de validación.
	 */
	private String idCampo;

	/**
	 * Método de acceso a mensajeValidacion.
	 * 
	 * @return mensajeValidacion
	 */
	public MensajeValidacion getMensajeValidacion() {
		return mensajeValidacion;
	}

	/**
	 * Método para establecer mensajeValidacion.
	 * 
	 * @param mensajeValidacion
	 *            mensajeValidacion a establecer
	 */
	public void setMensajeValidacion(MensajeValidacion mensajeValidacion) {
		this.mensajeValidacion = mensajeValidacion;
	}

	/**
	 * Método de acceso a idCampo.
	 * 
	 * @return idCampo
	 */
	public String getIdCampo() {
		return idCampo;
	}

	/**
	 * Método para establecer idCampo.
	 * 
	 * @param idCampo
	 *            idCampo a establecer
	 */
	public void setIdCampo(String idCampo) {
		this.idCampo = idCampo;
	}

}
