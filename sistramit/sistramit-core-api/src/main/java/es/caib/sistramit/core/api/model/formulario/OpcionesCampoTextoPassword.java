package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Opciones de configuración de un campo del formulario de tipo texto password.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class OpcionesCampoTextoPassword implements Serializable {

	/**
	 * Tamaño máximo (carácteres).
	 */
	private int tamanyo;

	/**
	 * Prevenir pegar.
	 */
	private TypeSiNo pegar = TypeSiNo.SI;

	/**
	 * Método de acceso a prevenirPegar.
	 *
	 * @return prevenirPegar
	 */
	public TypeSiNo getPegar() {
		return pegar;
	}

	/**
	 * Método para establecer prevenirPegar.
	 *
	 * @param prevenirPegar
	 *                          prevenirPegar a establecer
	 */
	public void setPegar(final TypeSiNo prevenirPegar) {
		this.pegar = prevenirPegar;
	}

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
	 * @param pTamanyo
	 *                     tamanyo a establecer
	 */
	public void setTamanyo(final int pTamanyo) {
		tamanyo = pTamanyo;
	}

}
