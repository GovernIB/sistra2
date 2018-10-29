package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que habido un error en el proceso de pago.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PagoException extends ServiceRollbackException {

    @Override
    public TypeNivelExcepcion getNivel() {
        // PagoException FATAL.
        return TypeNivelExcepcion.FATAL;
    }

    /**
     * Constructor PagoException.
     *
     * @param pMessage
     *            Mensaje de error.
     */
    public PagoException(final String pMessage) {
        super(pMessage);
    }

    /**
     * Constructor PagoException.
     *
     * @param pMessage
     *            Mensaje de error.
     * @param pExc
     *            Excepcion original.
     */
    public PagoException(final String pMessage, final Throwable pExc) {
        super(pMessage, pExc);
    }

    /**
     * Genera excepción ServiceRollbackException estableciendo un mensaje, la
     * causa y los detalles.
     *
     * @param pMessage
     *            Mensaje
     * @param pCause
     *            Causa
     * @param pDetalles
     *            Detalles de la excepción
     */
    public PagoException(final String pMessage, final Throwable pCause,
            final ListaPropiedades pDetalles) {
        super(pMessage, pCause, pDetalles);
    }

}
