package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones del paso de tramitación Accion.
 *
 * @author Indra
 *
 */
public enum TypeAccionPasoAccion implements TypeAccionPaso {
    /**
     * Ejecuta la accion asociada al paso. Parámetros entrada: no tiene.
     * Parámetros salida: no tiene.
     */
    EJECUTAR_ACCION;

    @Override
    public boolean isModificaPaso() {
        return false;
    }

}
