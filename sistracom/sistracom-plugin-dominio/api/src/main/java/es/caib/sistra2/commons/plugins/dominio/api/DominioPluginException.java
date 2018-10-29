package es.caib.sistra2.commons.plugins.dominio.api;

/**
 * Excepción en plugin dominio.
 *
 * @author Indra
 * 
 */
public class DominioPluginException extends Exception {

    private static final long serialVersionUID = 1L;

    public DominioPluginException() {
        super();
    }

    public DominioPluginException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public DominioPluginException(String arg0) {
        super(arg0);
    }

    public DominioPluginException(Throwable arg0) {
        super(arg0);
    }

}
