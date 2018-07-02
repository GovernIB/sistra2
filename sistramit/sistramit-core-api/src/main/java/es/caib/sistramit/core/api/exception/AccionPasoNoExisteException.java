package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada cuando se intenta realizar una acción sobre un paso de un
 * flujo de tramitación y no existe dicho paso en el controlador del paso.
 *
 */
@SuppressWarnings("serial")
public final class AccionPasoNoExisteException
        extends ServiceRollbackException {

    /**
     * Constructor AccionPasoNoExisteException.
     *
     * @param message
     *            Mensaje
     */
    public AccionPasoNoExisteException(final String message) {
        super(message);
    }

    /**
     * Constructor AccionPasoNoExisteException.
     *
     * @param message
     *            Mensaje
     * @param cause
     *            Causa
     */
    public AccionPasoNoExisteException(final String message,
            final Throwable cause) {
        super(message, cause);
    }

    /*
     * AccionPasoNoExisteException FATAL.
     */
    @Override
    public TypeNivelExcepcion getNivel() {
        return TypeNivelExcepcion.FATAL;
    }

}
