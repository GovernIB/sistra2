package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Error provocado si un parámetro de entrada de una operación no es correcto.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ParametrosEntradaIncorrectosException
        extends ServiceRollbackException {

    /*
     * ParametrosEntradaIncorrectosException FATAL.
     */
    @Override
    public TypeNivelExcepcion getNivel() {
        return TypeNivelExcepcion.FATAL;
    }

    /**
     * Constructor ParametrosEntradaIncorrectosException.
     *
     * @param pMessage
     *            Parámetro message
     * @param pDetalles
     *            Parámetro detalles
     */
    public ParametrosEntradaIncorrectosException(final String pMessage,
            final ListaPropiedades pDetalles) {
        super(pMessage, pDetalles);
    }

    /**
     * Constructor ParametrosEntradaIncorrectosException.
     *
     * @param pMessage
     *            Parámetro message
     * @param pCause
     *            Parámetro cause
     * @param pDetalles
     *            Parámetro detalles
     */
    public ParametrosEntradaIncorrectosException(final String pMessage,
            final Throwable pCause, final ListaPropiedades pDetalles) {
        super(pMessage, pCause, pDetalles);
    }

    /**
     * Constructor ParametrosEntradaIncorrectosException.
     *
     * @param pMessage
     *            Parámetro message
     * @param pCause
     *            Parámetro cause
     */
    public ParametrosEntradaIncorrectosException(final String pMessage,
            final Throwable pCause) {
        super(pMessage, pCause);
    }

    /**
     * Constructor ParametrosEntradaIncorrectosException.
     *
     * @param pMessage
     *            Parámetro message
     */
    public ParametrosEntradaIncorrectosException(final String pMessage) {
        super(pMessage);
    }

}
