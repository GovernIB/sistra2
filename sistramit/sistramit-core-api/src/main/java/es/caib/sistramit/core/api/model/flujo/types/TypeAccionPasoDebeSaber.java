package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones del paso de tramitación Debe saber.
 *
 * @author Indra
 *
 */
public enum TypeAccionPasoDebeSaber implements TypeAccionPaso {

	// TODO Pendiente definir accciones

	/**
	 * Descargar plantilla. Parámetros entrada: idAnexo. Parámetros salida: nombre /
	 * contenido (byte[])
	 */
	DESCARGAR_PLANTILLA;

	@Override
	public boolean isModificaPaso() {
		return false;
	}

}
