package es.caib.sistrages.rest.api.interna;

/**
 * Ayuda anexo trámite.
 *
 * @author Indra
 *
 */
public class RAnexoTramiteAyuda {

    /** Ayuda fichero (path). */
    private String fichero;

    /** Ayuda url . */
    private String url;


    /** Ayuda mensaje (html) . */
    private String mensajeHtml;

    /**
     * Método de acceso a fichero.
     *
     * @return fichero
     */
    public String getFichero() {
        return fichero;
    }

    /**
     * Método para establecer fichero.
     *
     * @param fichero
     *            fichero a establecer
     */
    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

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
     * Método de acceso a mensajeHtml.
     *
     * @return mensajeHtml
     */
    public String getMensajeHtml() {
        return mensajeHtml;
    }

    /**
     * Método para establecer mensajeHtml.
     *
     * @param mensajeHtml
     *            mensajeHtml a establecer
     */
    public void setMensajeHtml(String mensajeHtml) {
        this.mensajeHtml = mensajeHtml;
    }

}
