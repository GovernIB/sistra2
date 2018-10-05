package es.caib.sistra2.commons.plugins.autenticacion.api;

/**
 * Excepción en proceso de autenticación.
 *
 * @author Indra
 *
 */
public class AutenticacionPluginException extends Exception {

    private static final long serialVersionUID = 1L;

    public AutenticacionPluginException() {
        super();
    }

    public AutenticacionPluginException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public AutenticacionPluginException(String arg0) {
        super(arg0);
    }

    public AutenticacionPluginException(Throwable arg0) {
        super(arg0);
    }

}
