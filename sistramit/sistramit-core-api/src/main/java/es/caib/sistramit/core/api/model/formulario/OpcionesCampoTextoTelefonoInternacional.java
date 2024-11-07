package es.caib.sistramit.core.api.model.formulario;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

import java.io.Serializable;

/**
 * Configuración de un campo del formulario de tipo texto teléfono internacional.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class OpcionesCampoTextoTelefonoInternacional implements Serializable {

	/**
	 * Indica si realiza validación precisa.
	 */
	private TypeSiNo validacionPrecisa = TypeSiNo.SI;


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
	 * Método de acceso a validacionPrecisa.
	 *
	 * @return validacionPrecisa
	 */
	public TypeSiNo getValidacionPrecisa() {
		return validacionPrecisa;
	}

	/**
	 * Método para establecer validacionPrecisa.
	 *
	 * @param validacionPrecisa
	 *            validacionPrecisa a establecer
	 */
	public void setValidacionPrecisa(TypeSiNo validacionPrecisa) {
		this.validacionPrecisa = validacionPrecisa;
	}
}

