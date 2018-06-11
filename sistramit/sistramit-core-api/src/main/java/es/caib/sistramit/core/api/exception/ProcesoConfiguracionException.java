package es.caib.sistramit.core.api.exception;

import es.caib.sistramit.core.api.model.comun.ListaPropiedades;
import es.caib.sistramit.core.api.model.comun.types.TypeNivelExcepcion;

/**
 * Excepcion que indica que no esta bien configurado la parte de procesos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ProcesoConfiguracionException extends ServiceRollbackException {

    @Override
    public TypeNivelExcepcion getNivel() {
        // TypeNivelExcepcion FATAL
        return TypeNivelExcepcion.FATAL;
    }

    public ProcesoConfiguracionException(final String messageSNRE,
            final ListaPropiedades detallesSNRE) {
        super(messageSNRE, detallesSNRE);
    }

    public ProcesoConfiguracionException(final String messageSNRE,
            final Throwable causeSNRE, final ListaPropiedades detallesSNRE) {
        super(messageSNRE, causeSNRE, detallesSNRE);
    }

    public ProcesoConfiguracionException(final String messageSNRE,
            final Throwable causeSNRE) {
        super(messageSNRE, causeSNRE);
    }

    public ProcesoConfiguracionException(final String messageSNRE) {
        super(messageSNRE);
    }

}
