package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Opciones particularizadas de configuración de un campo del formulario de tipo
 * hora.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class OpcionesCampoTextoHora implements Serializable {

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

}
