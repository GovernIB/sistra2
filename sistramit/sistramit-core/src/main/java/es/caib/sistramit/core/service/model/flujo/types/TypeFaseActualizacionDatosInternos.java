package es.caib.sistramit.core.service.model.flujo.types;

/**
 * Indica cuando se procede a actualizar los datos internos del paso.
 *
 * @author Indra
 *
 */
public enum TypeFaseActualizacionDatosInternos {

    /**
     * Al inicializar paso.
     */
    INICIALIZAR_PASO,
    /**
     * Al cargar paso.
     */
    CARGAR_PASO,
    /**
     * Al revisar paso.
     */
    REVISAR_PASO,
    /**
     * Despues de ejecutar una acción.
     */
    EJECUCION_ACCION_PASO;

}
