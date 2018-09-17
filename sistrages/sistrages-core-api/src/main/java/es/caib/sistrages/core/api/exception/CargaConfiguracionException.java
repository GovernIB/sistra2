package es.caib.sistrages.core.api.exception;

/**
 * Excepcion que indica que ha habido un problema a la hora de cargar la
 * configuración.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class CargaConfiguracionException extends ServiceRollbackException {

    public CargaConfiguracionException(final String messageSNRE,
            final Throwable causeSNRE) {
        super(messageSNRE, causeSNRE);
    }

    public CargaConfiguracionException(final String messageSNRE) {
        super(messageSNRE);
    }

}
