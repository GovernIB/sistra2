package es.caib.sistramit.core.service.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 *
 * Datos internos paso Rellenar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosInternosPasoRellenar extends DatosInternosPasoReferencia {

	/**
	 * Constructor.
	 *
	 * @param idSesionTramitacion
	 *            Parámetro id sesion tramitacion
	 * @param idPaso
	 *            Parámetro id paso
	 */
	public DatosInternosPasoRellenar(final String idSesionTramitacion, final String idPaso) {
		this.setTipo(TypePaso.RELLENAR);
		this.setIdSesionTramitacion(idSesionTramitacion);
		this.setIdPaso(idPaso);
	}

	// TODO PENDIENTE

}
