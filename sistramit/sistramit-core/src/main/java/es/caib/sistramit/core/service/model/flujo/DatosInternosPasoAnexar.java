package es.caib.sistramit.core.service.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 *
 * Datos internos paso Anexar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosInternosPasoAnexar extends DatosInternosPasoReferencia {

	/**
	 * Constructor.
	 *
	 * @param idSesionTramitacion
	 *            Parámetro id sesion tramitacion
	 * @param idPaso
	 *            Parámetro id paso
	 */
	public DatosInternosPasoAnexar(final String idSesionTramitacion, final String idPaso) {
		this.setTipo(TypePaso.ANEXAR);
		this.setIdSesionTramitacion(idSesionTramitacion);
		this.setIdPaso(idPaso);
	}

	// TODO PENDIENTE

}
