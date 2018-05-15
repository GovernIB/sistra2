package es.caib.sistramit.core.service.model.flujo;

import es.caib.sistramit.core.api.model.flujo.DetallePaso;

/**
 * Datos internos de un paso de tramitación basado en un controlador de
 * referencia que mantiene el detalle del paso en memoria.
 *
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public abstract class DatosInternosPasoReferencia extends DatosInternosPaso {
	/**
	 * Datos necesarios para el front. Se cachean los datos para tenerlos
	 * preparados.
	 */
	private DetallePaso detallePaso;

	/**
	 * Método de acceso a detallePaso.
	 *
	 * @return detallePaso
	 */
	public final DetallePaso getDetallePaso() {
		return detallePaso;
	}

	/**
	 * Método para establecer detallePaso.
	 *
	 * @param pDetallePaso
	 *            detallePaso a establecer
	 */
	public final void setDetallePaso(final DetallePaso pDetallePaso) {
		detallePaso = pDetallePaso;
	}

}
