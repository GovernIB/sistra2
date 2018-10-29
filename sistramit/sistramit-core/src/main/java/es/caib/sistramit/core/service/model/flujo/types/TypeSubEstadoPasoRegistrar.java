package es.caib.sistramit.core.service.model.flujo.types;

/**
 * Subestados para el paso Registrar.
 *
 * @author Indra
 *
 */
public enum TypeSubEstadoPasoRegistrar implements TypeSubestadoPaso {

    /**
     * Indica que se ha iniciado proceso de registro y ha ocurrido un error
     * inesperado por lo que se debe reintentar el registro.
     */
    REINTENTAR_REGISTRO;

}
