package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Distintas acciones del paso de tramitación Debe saber.
 *
 * @author Indra
 *
 */
public enum TypeAccionPasoDebeSaber implements TypeAccionPaso {

    /**
     * No existen acciones para paso Debe Saber. Mantenemos tipo por si surge
     * alguna acción.
     */
    NO_EXISTE;

    @Override
    public boolean isModificaPaso() {
        return false;
    }

}
