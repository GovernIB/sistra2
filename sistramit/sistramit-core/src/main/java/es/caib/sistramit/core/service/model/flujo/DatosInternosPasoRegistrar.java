package es.caib.sistramit.core.service.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 *
 * Datos internos paso Registrar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosInternosPasoRegistrar extends DatosInternosPasoReferencia {

	/**
	 * Constructor.
	 *
	 * @param idSesionTramitacion
	 *            Parámetro id sesion tramitacion
	 * @param idPaso
	 *            Parámetro id paso
	 */
	public DatosInternosPasoRegistrar(final String idSesionTramitacion, final String idPaso) {
		this.setTipo(TypePaso.REGISTRAR);
		this.setIdSesionTramitacion(idSesionTramitacion);
		this.setIdPaso(idPaso);
	}

	// TODO PENDIENTE
}
