package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones del paso de tramitación Capturar.
 *
 * @author Indra
 *
 */
public enum TypeAccionPasoCapturar implements TypeAccionPaso {

    /**
     * Abrir formulario. Parámetros entrada: no tiene. Parámetros salida:
     * referencia (AbrirFormulario).
     */
    ABRIR_FORMULARIO,
    /**
     * Guardar formulario. Parámetros entrada: ticket. Parámetros salida: no
     * tiene.
     */
    GUARDAR_FORMULARIO;

    @Override
    public boolean isModificaPaso() {
        return true;
    }
}
