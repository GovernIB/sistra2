package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Error provocado al anexar un anexo que sobrepasa el tamaño máximo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class TamanyoMaximoAnexoException
        extends ServiceRollbackException {

    /*
     * TamanyoMaximoAnexoException WARNING.
     */
    @Override
    public TypeNivelExcepcion getNivel() {
        return TypeNivelExcepcion.WARNING;
    }

    /**
     * ConstructorTamanyoMaximoAnexoException .
     *
     * @param pMessage
     *            Parámetro message
     */
    public TamanyoMaximoAnexoException(final String pMessage) {
        super(pMessage);
    }

    /**
     * ConstructorTamanyoMaximoAnexoException .
     *
     * @param pMessage
     *            Parámetro message
     * @param pEx
     *            Excepcion origen
     */
    public TamanyoMaximoAnexoException(final String pMessage,
            final Throwable pEx) {
        super(pMessage, pEx);
    }

}
