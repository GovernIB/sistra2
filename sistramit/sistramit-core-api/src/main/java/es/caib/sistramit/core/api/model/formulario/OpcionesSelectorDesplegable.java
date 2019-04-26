package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.comun.types.TypeSiNo;

/**
 * Opciones particularizadas de configuración de un campo del formulario de tipo
 * selector desplegable.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class OpcionesSelectorDesplegable implements Serializable {

	/**
	 * Indice alfabético.
	 */
	private TypeSiNo indice = TypeSiNo.NO;

	/**
	 * Método de acceso a indice.
	 * 
	 * @return indice
	 */
	public TypeSiNo getIndice() {
		return indice;
	}

	/**
	 * Método para establecer indice.
	 * 
	 * @param indice
	 *            indice a establecer
	 */
	public void setIndice(final TypeSiNo indice) {
		this.indice = indice;
	}

}
