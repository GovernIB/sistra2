package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones de los pasos de tramitación.
 *
 * @author Indra
 *
 */
public interface TypeAccionPaso {

	/**
	 * Nombre de la acción.
	 *
	 * @return Nombre de la acción
	 */
	String name();

	/**
	 * Indica si modifica el paso.
	 *
	 * @return readonly
	 */
	boolean isModificaPaso();
}
