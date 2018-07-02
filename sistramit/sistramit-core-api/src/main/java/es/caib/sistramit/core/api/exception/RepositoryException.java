package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada si se produce un error en las clases de la capa de acceso a
 * bbdd. Los errores generados por hibernate lanzaran la excepcion
 * correspondiente de spring.
 *
 */
@SuppressWarnings("serial")
public final class RepositoryException extends ServiceRollbackException {

    @Override
    public TypeNivelExcepcion getNivel() {
        return TypeNivelExcepcion.FATAL;
    }

    /**
     * Constructor RepositoryException.
     *
     * @param message
     *            Mensaje
     * @param cause
     *            Causa
     */
    public RepositoryException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor RepositoryException.
     *
     * @param message
     *            Mensaje
     */
    public RepositoryException(final String message) {
        super(message);
    }

}
