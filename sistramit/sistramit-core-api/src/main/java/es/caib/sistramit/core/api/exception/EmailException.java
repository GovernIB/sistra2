package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Error envio email.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class EmailException extends ServiceRollbackException {

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
    public EmailException(final String pMessage) {
        super(pMessage);
    }

    public EmailException(String pmessageSRE, Throwable pcauseSRE) {
        super(pmessageSRE, pcauseSRE);
    }

}
