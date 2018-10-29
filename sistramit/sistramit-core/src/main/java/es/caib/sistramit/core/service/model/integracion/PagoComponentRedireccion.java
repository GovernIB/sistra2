package es.caib.sistramit.core.service.model.integracion;

import java.io.Serializable;

/**
 * Datos de redireccion a gestor de pagos externo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PagoComponentRedireccion implements Serializable {

    /** Url inicio pago. */
    private String url;

    /** Identificador pago. */
    private String identificador;

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

}
