package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Error provocado al transformar a pdf.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class TransformacionPdfException extends ServiceRollbackException {

    /*
     * TransformacionPdfException WARNING.
     */
    @Override
    public TypeNivelExcepcion getNivel() {
        return TypeNivelExcepcion.WARNING;
    }

    /**
     * ConstructorTransformacionPdfException .
     *
     * @param pMessage
     *            Parámetro message
     */
    public TransformacionPdfException(final String pMessage) {
        super(pMessage);
    }

    /**
     * ConstructorTransformacionPdfException .
     *
     * @param pMessage
     *            Parámetro message
     * @param pEx
     *            Excepcion origen
     */
    public TransformacionPdfException(final String pMessage,
            final Throwable pEx) {
        super(pMessage, pEx);
    }

}
