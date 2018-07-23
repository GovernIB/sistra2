package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que no se ha podido enviar el formulario de soporte.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class ErrorFormularioSoporteException
        extends ServiceRollbackException {

    @Override
    public TypeNivelExcepcion getNivel() {
        return TypeNivelExcepcion.WARNING;
    }

    /**
     * Constructor ErrorFormularioSoporteException.
     *
     */
    public ErrorFormularioSoporteException() {
        super("Error al enviar formulario de soporte");
    }

    /**
     * Constructor ErrorFormularioSoporteException.
     * 
     * @param message
     *            mensaje
     */
    public ErrorFormularioSoporteException(String message) {
        super("Error al enviar formulario de soporte: " + message);
    }

    /**
     * Constructor ErrorFormularioSoporteException.
     * 
     * @param message
     *            mensaje
     * @param error
     *            error
     */
    public ErrorFormularioSoporteException(String message, Exception error) {
        super("Error al enviar formulario de soporte: " + message, error);
    }
}
