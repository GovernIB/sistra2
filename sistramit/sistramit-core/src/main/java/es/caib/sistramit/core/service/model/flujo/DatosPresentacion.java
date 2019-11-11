package es.caib.sistramit.core.service.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.DatosInteresado;

/**
 * Datos presentacion.
 */
@SuppressWarnings("serial")
public final class DatosPresentacion implements Serializable {

	/** Indica quien debe presentar el registro. */
	private DatosInteresado presentador;

	/**
	 * Método de acceso a presentador.
	 *
	 * @return presentador
	 */
	public DatosInteresado getPresentador() {
		return presentador;
	}

	/**
	 * Método para establecer presentador.
	 *
	 * @param pPresentador
	 *                         presentador a establecer
	 */
	public void setPresentador(final DatosInteresado pPresentador) {
		presentador = pPresentador;
	}

}
