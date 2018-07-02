package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 *
 * Excepcion lanzada cuando se intenta cargar un trámite de persistencia pero se
 * ha detectado que se ha modificado la configuración del trámite.
 *
 */
@SuppressWarnings("serial")
public final class ConfiguracionModificadaException
        extends ServiceRollbackException {

    /*
     * ConfiguracionModificadaException FATAL.
     */
    @Override
    public TypeNivelExcepcion getNivel() {
        return TypeNivelExcepcion.FATAL;
    }

    /**
     * Constructor ConfiguracionModificadaException.
     *
     * @param pMessage
     *            Mensaje excepción.
     */
    public ConfiguracionModificadaException(final String pMessage) {
        super(pMessage);
    }

}
