package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones del paso de tramitación Rellenar.
 *
 * @author Indra
 *
 */
public enum TypeAccionPasoRellenar implements TypeAccionPaso {

	// TODO Pendiente definir accciones

	ABRIR_FORMULARIO, GUARDAR_FORMULARIO, DESCARGAR_FORMULARIO(false), DESCARGAR_XML(false);

	/**
	 * Indica si la acción modifica datos del paso.
	 */
	private boolean modificaPasoRelenar = true;

	/**
	 * Constructor.
	 *
	 * @param pmodificaPaso
	 *            Indica si modifica el paso.
	 */
	private TypeAccionPasoRellenar(final boolean pmodificaPaso) {
		modificaPasoRelenar = pmodificaPaso;
	}

	/**
	 * Constructor.
	 */
	private TypeAccionPasoRellenar() {
	}

	@Override
	public boolean isModificaPaso() {
		return modificaPasoRelenar;
	}
}
