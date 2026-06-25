package es.caib.sistramit.core.ejb;

import javax.ejb.ApplicationException;

@ApplicationException
public class AppEJBException extends RuntimeException {

    public AppEJBException(String message, Throwable cause) {
        super(message, cause);
    }
}