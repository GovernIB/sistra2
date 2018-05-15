package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones del paso de tramitación Guardar (tras registrar).
 *
 * @author Indra
 *
 */
public enum TypeAccionPasoGuardar implements TypeAccionPaso {

	// TODO Pendiente definir accciones

	/**
	 * Descargar documento. Parámetros entrada: idDocumentoRegistro Parámetros
	 * salida: nombreFichero, datosFichero (byte[])
	 */
	DESCARGAR_DOCUMENTO,
	/**
	 * Descargar justificante. Parámetros entrada: csv (TypeSiNo). Parámetros
	 * salida: nombreFichero, datosFichero (byte[])
	 */
	DESCARGAR_JUSTIFICANTE;

	@Override
	public boolean isModificaPaso() {
		return false;
	}

}
