package es.caib.sistramit.core.service.model.formulario.interno;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Resultado validacion.
 *
 * @author Indra
 *
 */
public final class ResultadoValidacion {

	/**
	 * Error de validación.
	 */
	private boolean error;
	/**
	 * Mensaje de error.
	 */
	private String mensajeError;
	/**
	 * Mensajes de aviso.
	 */
	private List<MensajeAviso> avisos = new ArrayList<>();

	/**
	 * Método de acceso a error.
	 *
	 * @return error
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * Método para establecer error.
	 *
	 * @param pError
	 *            error a establecer
	 */
	public void setError(final boolean pError) {
		error = pError;
	}

	/**
	 * Método de acceso a mensajeError.
	 *
	 * @return mensajeError
	 */
	public String getMensajeError() {
		return mensajeError;
	}

	/**
	 * Método para establecer mensajeError.
	 *
	 * @param pMensajeError
	 *            mensajeError a establecer
	 */
	public void setMensajeError(final String pMensajeError) {
		mensajeError = pMensajeError;
	}

	/**
	 * Método de acceso a avisos.
	 *
	 * @return avisos
	 */
	public List<MensajeAviso> getAvisos() {
		return avisos;
	}

	/**
	 * Método para establecer avisos.
	 *
	 * @param pAvisos
	 *            avisos a establecer
	 */
	public void setAvisos(final List<MensajeAviso> pAvisos) {
		avisos = pAvisos;
	}

}
