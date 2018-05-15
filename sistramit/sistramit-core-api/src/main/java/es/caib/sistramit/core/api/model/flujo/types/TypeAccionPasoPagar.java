package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones del paso de tramitación Pagar.
 *
 * @author Indra
 *
 */
public enum TypeAccionPasoPagar implements TypeAccionPaso {

	// TODO Pendiente definir accciones

	/**
	 * Pendiente.
	 */
	INICIAR_PAGO;

	/**
	 * Indica si la acción modifica datos del paso.
	 */
	private boolean modificaPasoPagar = true;

	/**
	 * Constructor.
	 *
	 * @param pmodificaPaso
	 *            Indica si modifica el paso.
	 */
	private TypeAccionPasoPagar(final boolean pmodificaPaso) {
		modificaPasoPagar = pmodificaPaso;
	}

	/**
	 * Constructor.
	 */
	private TypeAccionPasoPagar() {
	}

	@Override
	public boolean isModificaPaso() {
		return modificaPasoPagar;
	}
}
