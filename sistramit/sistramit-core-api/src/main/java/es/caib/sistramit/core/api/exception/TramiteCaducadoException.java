package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepción que indica que el trámite ha caducado y no se puede acceder.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class TramiteCaducadoException extends ServiceRollbackException {

    @Override
    public TypeNivelExcepcion getNivel() {
        // TramiteCaducadoException FATAL.
        return TypeNivelExcepcion.FATAL;
    }

    /**
     * Constructor TramiteCaducadoException.
     *
     * @param pIdSesionTramite
     *            Id sesion tramite.
     */
    public TramiteCaducadoException(final String pIdSesionTramite) {
        super("Tramite " + pIdSesionTramite + " finalizado");
    }

}
