package es.caib.sistrages.core.api.exception;

/**
 * Excepcion que indica que ha habido un problema de calcular el valor identificador (GLOBAL.ID o ID_ENTIDAD.ID o ID_ENTIDAD.ID_AREA.ID)
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class ValorIdentificadorIncorrectoException extends ServiceRollbackException {

    public ValorIdentificadorIncorrectoException(final String messageSNRE,
            final Throwable causeSNRE) {
        super(messageSNRE, causeSNRE);
    }

    public ValorIdentificadorIncorrectoException(final String messageSNRE) {
        super(messageSNRE);
    }


}
