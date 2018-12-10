package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.DatosUsuario;

/**
 * Datos representacion.
 */
@SuppressWarnings("serial")
public final class DatosRepresentacion implements Serializable {

	/** Indica si existe representado. */
	private DatosUsuario representado;

	/**
	 * Método de acceso a representado.
	 * 
	 * @return representado
	 */
	public DatosUsuario getRepresentado() {
		return representado;
	}

	/**
	 * Método para establecer representado.
	 * 
	 * @param representado
	 *            representado a establecer
	 */
	public void setRepresentado(DatosUsuario representado) {
		this.representado = representado;
	}

}
