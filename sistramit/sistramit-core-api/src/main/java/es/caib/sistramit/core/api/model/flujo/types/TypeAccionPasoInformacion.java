package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones del paso de tramitación Información.
 *
 * @author Indra
 *
 */
public enum TypeAccionPasoInformacion implements TypeAccionPaso {

    /**
     * Descargar documento. Parámetros entrada: idFichero. Parámetros salida:
     * nombreFichero, datosFichero (byte[])
     */
    DESCARGAR_DOCUMENTO;

    @Override
    public boolean isModificaPaso() {
        return false;
    }

}
