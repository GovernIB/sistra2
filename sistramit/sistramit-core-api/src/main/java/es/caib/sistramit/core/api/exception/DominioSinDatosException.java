package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Error que indica que un dominio no ha retornado datos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DominioSinDatosException extends ServiceRollbackException {

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
    public DominioSinDatosException(final String pMessage) {
        super(pMessage);
    }

    public DominioSinDatosException(final String pmessageSRE,
            final Throwable pcauseSRE) {
        super(pmessageSRE, pcauseSRE);
    }

}
