package es.caib.sistrages.rest.api;

import java.util.List;

public class PaginaFormulario {

    /** HTML página (B64). */
    private String htmlB64;

    /** Indica si es página final. */
    private boolean paginaFinal;

    /** Script validación página. */
    private Script scriptValidacion;

    /** Líneas de componentes. */
    private List<LineaComponentes> lineas;

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
    public Script getScriptValidacion() {
        return scriptValidacion;
    }

    /**
     * Método para establecer scriptValidacion.
     * 
     * @param scriptValidacion
     *            scriptValidacion a establecer
     */
    public void setScriptValidacion(Script scriptValidacion) {
        this.scriptValidacion = scriptValidacion;
    }

    /**
     * Método de acceso a lineas.
     * 
     * @return lineas
     */
    public List<LineaComponentes> getLineas() {
        return lineas;
    }

    /**
     * Método para establecer lineas.
     * 
     * @param lineas
     *            lineas a establecer
     */
    public void setLineas(List<LineaComponentes> lineas) {
        this.lineas = lineas;
    }

}
