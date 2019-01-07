package es.caib.sistramit.core.service.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 *
 * Datos internos paso Guardar.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DatosInternosPasoGuardar extends DatosInternosPasoReferencia {

	// TODO VER SI ES NECESARIO JUSTIFICANTE (para electronico no deberia, para
	// presencial si al final se permite)

	/**
	 * Constructor.
	 *
	 * @param idSesionTramitacion
	 *            Parámetro id sesion tramitacion
	 * @param idPaso
	 *            Parámetro id paso
	 */
	public DatosInternosPasoGuardar(final String idSesionTramitacion, final String idPaso) {
		this.setTipo(TypePaso.GUARDAR);
		this.setIdSesionTramitacion(idSesionTramitacion);
		this.setIdPaso(idPaso);
	}

}
