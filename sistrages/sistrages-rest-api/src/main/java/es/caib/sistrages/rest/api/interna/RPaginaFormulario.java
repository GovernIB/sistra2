package es.caib.sistrages.rest.api.interna;

import java.util.List;

public class RPaginaFormulario {

    /** HTML página (B64). */
    private String htmlB64;

    /** Indica si es página final. */
    private boolean paginaFinal;

    /** Script validación página. */
    private RScript scriptValidacion;

    /** Líneas de componentes. */
    private List<RLineaComponentes> lineas;

    /**
     * Método de acceso a htmlB64.
     *
     * @return htmlB64
     */
    public String getHtmlB64() {
        return htmlB64;
    }

    /**
     * Método para establecer htmlB64.
     *
     * @param htmlB64
     *            htmlB64 a establecer
     */
    public void setHtmlB64(String htmlB64) {
        this.htmlB64 = htmlB64;
    }

    /**
     * Método de acceso a paginaFinal.
     *
     * @return paginaFinal
     */
    public boolean isPaginaFinal() {
        return paginaFinal;
    }

    /**
     * Método para establecer paginaFinal.
     *
     * @param paginaFinal
     *            paginaFinal a establecer
     */
    public void setPaginaFinal(boolean paginaFinal) {
        this.paginaFinal = paginaFinal;
    }

    /**
     * Método de acceso a scriptValidacion.
     *
     * @return scriptValidacion
     */
    public RScript getScriptValidacion() {
        return scriptValidacion;
    }

    /**
     * Método para establecer scriptValidacion.
     *
     * @param scriptValidacion
     *            scriptValidacion a establecer
     */
    public void setScriptValidacion(RScript scriptValidacion) {
        this.scriptValidacion = scriptValidacion;
    }

    /**
     * Método de acceso a lineas.
     *
     * @return lineas
     */
    public List<RLineaComponentes> getLineas() {
        return lineas;
    }

    /**
     * Método para establecer lineas.
     *
     * @param lineas
     *            lineas a establecer
     */
    public void setLineas(List<RLineaComponentes> lineas) {
        this.lineas = lineas;
    }

}
