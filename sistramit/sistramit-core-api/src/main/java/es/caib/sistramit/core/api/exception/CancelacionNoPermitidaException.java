package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada cuando se intenta cancelar un trámite y no se permite (p.e.
 * registro iniciado).
 *
 */
@SuppressWarnings("serial")
public final class CancelacionNoPermitidaException
        extends ServiceRollbackException {

    /*
     * CancelacionNoPermitidaException WARNING.
     */
    @Override
    public TypeNivelExcepcion getNivel() {
        return TypeNivelExcepcion.WARNING;
    }

    /**
     * Constructor CancelacionNoPermitidaException.
     *
     * @param message
     *            Mensaje
     * @param cause
     *            Causa
     */
    public CancelacionNoPermitidaException(final String message,
            final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor CancelacionNoPermitidaException.
     *
     * @param message
     *            Mensaje
     */
    public CancelacionNoPermitidaException(final String message) {
        super(message);
    }

}
