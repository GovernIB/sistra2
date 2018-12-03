package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Error que indica que un dominio no existe.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DominioNoExisteException extends ServiceRollbackException {

    @Override
    public TypeNivelExcepcion getNivel() {
        return TypeNivelExcepcion.FATAL;
    }

    /**
     * Constructor.
     *
     * @param pMessage
     *            Mensaje de error.
     */
    public DominioNoExisteException(final String pMessage) {
        super(pMessage);
    }

    public DominioNoExisteException(final String pmessageSRE,
            final Throwable pcauseSRE) {
        super(pmessageSRE, pcauseSRE);
    }

}
