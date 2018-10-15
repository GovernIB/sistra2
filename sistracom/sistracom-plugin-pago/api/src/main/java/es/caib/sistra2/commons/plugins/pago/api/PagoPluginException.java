package es.caib.sistra2.commons.plugins.pago.api;

/**
 * Excepción en componente externo pago.
 *
 * @author Indra
 *
 */
public class PagoPluginException extends Exception {

    private static final long serialVersionUID = 1L;

    public PagoPluginException() {
        super();
    }

    public PagoPluginException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public PagoPluginException(String arg0) {
        super(arg0);
    }

    public PagoPluginException(Throwable arg0) {
        super(arg0);
    }

}
