package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;
import java.util.Date;

/**
 * Indica el resultado del registro.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ResultadoRegistro implements Serializable {

	/**
	 * Indica si es un registro (false) o preregistro (true).
	 */
	private boolean preregistro;

	/**
	 * Número de registro.
	 */
	private String numeroRegistro;

	/**
	 * Fecha de registro.
	 */
	private Date fechaRegistro;

	/**
	 * Asunto.
	 */
	private String asunto;

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
	 * @param pNumeroRegistro
	 *            numeroRegistro a establecer
	 */
	public void setNumeroRegistro(final String pNumeroRegistro) {
		numeroRegistro = pNumeroRegistro;
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
	 * @param pFechaRegistro
	 *            fechaRegistro a establecer
	 */
	public void setFechaRegistro(final Date pFechaRegistro) {
		fechaRegistro = pFechaRegistro;
	}

	/**
	 * Método de acceso a preregistro.
	 *
	 * @return preregistro
	 */
	public boolean isPreregistro() {
		return preregistro;
	}

	/**
	 * Método para establecer preregistro.
	 *
	 * @param pPreregistro
	 *            preregistro a establecer
	 */
	public void setPreregistro(final boolean pPreregistro) {
		preregistro = pPreregistro;
	}

	/**
	 * Método de acceso a asunto.
	 *
	 * @return asunto
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * Método para establecer asunto.
	 *
	 * @param pAsunto
	 *            asunto a establecer
	 */
	public void setAsunto(final String pAsunto) {
		asunto = pAsunto;
	}

}
