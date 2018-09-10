package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que la extensión del anexo no es valida.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AnexoVacioException extends ServiceRollbackException {

    @Override
    public TypeNivelExcepcion getNivel() {
        // AnexoVacioException WARNING.
        return TypeNivelExcepcion.WARNING;
    }

    /**
     * Constructor AnexoVacioException.
     *
     * @param pMessage
     *            Mensaje de error.
     */
    public AnexoVacioException(final String pMessage) {
        super(pMessage);
    }

}
