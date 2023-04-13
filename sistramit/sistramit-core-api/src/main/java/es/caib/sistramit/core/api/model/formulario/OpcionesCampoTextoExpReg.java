package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Opciones particularizadas de configuración de un campo del formulario de tipo
 * expresión regular.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class OpcionesCampoTextoExpReg implements Serializable {

	/**
	 * Expresión regular.
	 */
	private String regexp;

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
	 * Método de acceso a regexp.
	 *
	 * @return regexp
	 */
	public String getRegexp() {
		return regexp;
	}

	/**
	 * Método para establecer regexp.
	 *
	 * @param pRegexp
	 *                    regexp a establecer
	 */
	public void setRegexp(final String pRegexp) {
		regexp = pRegexp;
	}

}
