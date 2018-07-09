package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que el trámite se está modificando en otra sesión.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class TimestampFlujoException extends ServiceRollbackException {

    /*
     * FlujoInvalidoException FATAL.
     */
    @Override
    public TypeNivelExcepcion getNivel() {
        return TypeNivelExcepcion.FATAL;
    }

    /**
     * Constructor TimestampFlujoException.
     *
     */
    public TimestampFlujoException() {
        super("El trámite se está modificando en otra sesión");
    }

}
