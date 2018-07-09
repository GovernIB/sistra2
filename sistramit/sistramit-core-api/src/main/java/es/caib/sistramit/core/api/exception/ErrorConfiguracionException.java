package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que la configuración del trámite no es correcta.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ErrorConfiguracionException
        extends ServiceRollbackException {

    @Override
    public TypeNivelExcepcion getNivel() {
        // ErrorConfiguracionException FATAL.
        return TypeNivelExcepcion.FATAL;
    }

    /**
     * Constructor ErrorConfiguracionException.
     *
     * @param pMessage
     *            Mensaje de error.
     */
    public ErrorConfiguracionException(final String pMessage) {
        super(pMessage);
    }

}
