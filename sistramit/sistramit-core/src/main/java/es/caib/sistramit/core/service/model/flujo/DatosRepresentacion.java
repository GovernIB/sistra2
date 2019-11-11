package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.DatosInteresado;

/**
 * Datos representacion.
 */
@SuppressWarnings("serial")
public final class DatosRepresentacion implements Serializable {

	/** Indica si existe representante. */
	private DatosInteresado representante;

	/** Indica si existe representado. */
	private DatosInteresado representado;

	/**
	 * Método de acceso a representado.
	 *
	 * @return representado
	 */
	public DatosInteresado getRepresentado() {
		return representado;
	}

	/**
	 * Método para establecer representado.
	 *
	 * @param representado
	 *                         representado a establecer
	 */
	public void setRepresentado(final DatosInteresado representado) {
		this.representado = representado;
	}

	/**
	 * Método de acceso a representante.
	 *
	 * @return representante
	 */
	public DatosInteresado getRepresentante() {
		return representante;
	}

	/**
	 * Método para establecer representante.
	 *
	 * @param representante
	 *                          representante a establecer
	 */
	public void setRepresentante(final DatosInteresado representante) {
		this.representante = representante;
	}

}
