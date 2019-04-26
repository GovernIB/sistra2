package es.caib.sistra2.commons.plugins.dominio.rest.api.v1;

/**
 * Excepción en plugin dominio.
 *
 * @author Indra
 *
 */
public class RDominioPluginException extends Exception {

    private static final long serialVersionUID = 1L;

    public RDominioPluginException() {
        super();
    }

    public RDominioPluginException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public RDominioPluginException(String arg0) {
        super(arg0);
    }

    public RDominioPluginException(Throwable arg0) {
        super(arg0);
    }

}
