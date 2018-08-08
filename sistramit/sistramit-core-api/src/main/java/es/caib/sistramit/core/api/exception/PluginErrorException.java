package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepciónque indica que no existe plugin indicado.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PluginErrorException extends ServiceRollbackException {

    @Override
    public TypeNivelExcepcion getNivel() {
        // ErrorScriptException FATAL.
        return TypeNivelExcepcion.FATAL;
    }

    /**
     * Constructor PluginNoExisteException
     *
     * @param message
     *            message
     */
    public PluginErrorException(final String message) {
        super(message);
    }

    /**
     * Constructor PluginNoExisteException
     *
     * @param message
     *            message
     * @param ex
     *            excepción
     */
    public PluginErrorException(final String message, Exception ex) {
        super(message, ex);
    }
}
