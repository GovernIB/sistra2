package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones del paso de tramitación Guardar (tras registrar).
 *
 * @author Indra
 *
 */
public enum TypeAccionPasoGuardar implements TypeAccionPaso {

	/**
	 * Descargar documento. Parámetros entrada: idDocumentoRegistro, instancia.
	 * Parámetros salida: nombreFichero, datosFichero (byte[])
	 */
	DESCARGAR_DOCUMENTO,
	/**
	 * Descargar justificante. Parámetros entrada: no tiene. Parámetros salida:
	 * nombreFichero, datosFichero (byte[])
	 */
	DESCARGAR_JUSTIFICANTE;

	@Override
	public boolean isModificaPaso() {
		return false;
	}

}
