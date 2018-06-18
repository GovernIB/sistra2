package es.caib.sistrages.rest.api;

import java.util.List;

/**
 * Diseño formulario interno.
 *
 * @author Indra
 *
 */
public class FormularioInterno {

    /** Indica si muestra título formulario. */
    private boolean mostrarTitulo;

    /** Título formulario. */
    private String titulo;

    /** Plantillas. */
    private List<PlantillaFormulario> plantillas;

    /** Script plantillas. */
    private Script scriptPlantillas;

    /** Páginas. */
    private List<PaginaFormulario> paginas;

    /**
     * Método de acceso a mostrarTitulo.
     * 
     * @return mostrarTitulo
     */
    public boolean isMostrarTitulo() {
        return mostrarTitulo;
    }

    /**
     * Método para establecer mostrarTitulo.
     * 
     * @param mostrarTitulo
     *            mostrarTitulo a establecer
     */
    public void setMostrarTitulo(boolean mostrarTitulo) {
        this.mostrarTitulo = mostrarTitulo;
    }

    /**
     * Método de acceso a titulo.
     * 
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Método para establecer titulo.
     * 
     * @param titulo
     *            titulo a establecer
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Método de acceso a plantillas.
     * 
     * @return plantillas
     */
    public List<PlantillaFormulario> getPlantillas() {
        return plantillas;
    }

    /**
     * Método para establecer plantillas.
     * 
     * @param plantillas
     *            plantillas a establecer
     */
    public void setPlantillas(List<PlantillaFormulario> plantillas) {
        this.plantillas = plantillas;
    }

    /**
     * Método de acceso a scriptPlantillas.
     * 
     * @return scriptPlantillas
     */
    public Script getScriptPlantillas() {
        return scriptPlantillas;
    }

    /**
     * Método para establecer scriptPlantillas.
     * 
     * @param scriptPlantillas
     *            scriptPlantillas a establecer
     */
    public void setScriptPlantillas(Script scriptPlantillas) {
        this.scriptPlantillas = scriptPlantillas;
    }

    /**
     * Método de acceso a paginas.
     * 
     * @return paginas
     */
    public List<PaginaFormulario> getPaginas() {
        return paginas;
    }

    /**
     * Método para establecer paginas.
     * 
     * @param paginas
     *            paginas a establecer
     */
    public void setPaginas(List<PaginaFormulario> paginas) {
        this.paginas = paginas;
    }

}
