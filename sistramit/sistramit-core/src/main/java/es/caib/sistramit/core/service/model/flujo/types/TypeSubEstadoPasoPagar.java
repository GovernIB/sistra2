package es.gva.dgm.tra.ctt.att.negocio.model.flujo.internal.types;

import es.caib.sistramit.core.service.model.flujo.types.TypeSubestadoPaso;

/**
 * Subestados para el paso Pagar.
 *
 * @author Indra
 *
 */
public enum TypeSubEstadoPasoPagar implements TypeSubestadoPaso {
    /**
     * Indica que hay algún pago iniciado (a mitad de realizarse). No se
     * permitira cambiar de paso.
     */
    PAGO_INICIADO,

    /**
     * Indica que hay algún pago completado. No se permitira modificar
     * formularios anteriores.
     */
    PAGO_COMPLETADO;
}
