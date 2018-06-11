package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepcion que indica que ha habido un problema a la hora de cargar la
 * configuración.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class CargaConfiguracionException extends ServiceRollbackException {

    @Override
    public TypeNivelExcepcion getNivel() {
        // TypeNivelExcepcion FATAL
        return TypeNivelExcepcion.FATAL;
    }

    public CargaConfiguracionException(final String messageSNRE,
            final ListaPropiedades detallesSNRE) {
        super(messageSNRE, detallesSNRE);
    }

    public CargaConfiguracionException(final String messageSNRE,
            final Throwable causeSNRE, final ListaPropiedades detallesSNRE) {
        super(messageSNRE, causeSNRE, detallesSNRE);
    }

    public CargaConfiguracionException(final String messageSNRE,
            final Throwable causeSNRE) {
        super(messageSNRE, causeSNRE);
    }

    public CargaConfiguracionException(final String messageSNRE) {
        super(messageSNRE);
    }

}
