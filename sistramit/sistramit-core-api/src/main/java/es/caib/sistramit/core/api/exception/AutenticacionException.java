package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Error en autenticación.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class AutenticacionException extends ServiceRollbackException {

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
    public AutenticacionException(final String pMessage) {
        super(pMessage);
    }

    public AutenticacionException(String pmessageSRE, Throwable pcauseSRE) {
        super(pmessageSRE, pcauseSRE);
    }

}
