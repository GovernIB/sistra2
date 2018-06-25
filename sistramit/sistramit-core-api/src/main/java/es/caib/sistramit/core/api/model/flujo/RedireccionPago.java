package es.caib.sistramit.core.api.model.flujo;

/**
 *
 * Respuesta a la acción de abrir un pago a través de la pasarela de pagos.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class RedireccionPago implements ModelApi {

    /** Url inicio pago. */
    private String url;

    /**
     * Método de acceso a url.
     * 
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Método para establecer url.
     * 
     * @param url
     *            url a establecer
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
