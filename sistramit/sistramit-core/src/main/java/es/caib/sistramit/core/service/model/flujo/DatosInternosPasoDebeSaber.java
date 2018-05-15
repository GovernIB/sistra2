package es.caib.sistramit.core.service.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 *
 * Datos internos paso DebeSaber.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosInternosPasoDebeSaber extends DatosInternosPasoReferencia {

	/**
	 * Constructor.
	 *
	 * @param idSesionTramitacion
	 *            Parámetro id sesion tramitacion
	 * @param idPaso
	 *            Parámetro id paso
	 */
	public DatosInternosPasoDebeSaber(final String idSesionTramitacion, final String idPaso) {
		this.setTipo(TypePaso.DEBESABER);
		this.setIdSesionTramitacion(idSesionTramitacion);
		this.setIdPaso(idPaso);
	}

	// TODO PENDIENTE

}
