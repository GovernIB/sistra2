package es.caib.sistra2.commons.plugins.pago.api;

/**
 * Redirección pago.
 *
 * @author Indra
 *
 */
public class RedireccionPago {

    /** Identificador pago. */
    private String identificador;

    /** Url pago. */
    private String urlPago;

    /**
     * Método de acceso a identificador.
     *
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Método para establecer identificador.
     *
     * @param identificador
     *            identificador a establecer
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Método de acceso a urlPago.
     *
     * @return urlPago
     */
    public String getUrlPago() {
        return urlPago;
    }

    /**
     * Método para establecer urlPago.
     *
     * @param urlPago
     *            urlPago a establecer
     */
    public void setUrlPago(String urlPago) {
        this.urlPago = urlPago;
    }

}
