package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada cuando se intenta realizar una acción no permitida sobre un
 * paso.
 *
 */
@SuppressWarnings("serial")
public final class AccionPasoNoPermitidaException
        extends ServiceRollbackException {

    /**
     * Constructor AccionPasoNoPermitidaException.
     *
     * @param message
     *            Mensaje
     * @param cause
     *            Causa
     */
    public AccionPasoNoPermitidaException(final String message,
            final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor AccionPasoNoPermitidaException.
     *
     * @param message
     *            Mensaje
     */
    public AccionPasoNoPermitidaException(final String message) {
        super(message);
    }

    /*
     * AccionPasoNoPermitidaException FATAL.
     */
    @Override
    public TypeNivelExcepcion getNivel() {
        return TypeNivelExcepcion.FATAL;
    }

}
