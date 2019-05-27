package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

/**
 * Opciones de configuración de un campo del formulario de tipo texto email.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class OpcionesCampoTextoEmail implements Serializable {

	/**
	 * Tamaño máximo (carácteres).
	 */
	private int tamanyo;

	/**
	 * Método de acceso a tamanyo.
	 * 
	 * @return tamanyo
	 */
	public int getTamanyo() {
		return tamanyo;
	}

	/**
	 * Método para establecer tamanyo.
	 * 
	 * @param tamanyo
	 *            tamanyo a establecer
	 */
	public void setTamanyo(final int tamanyo) {
		this.tamanyo = tamanyo;
	}

}
