package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Diseño formulario interno.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RFormularioInterno", description = "Descripcion de RFormularioInterno")
public class RFormularioInterno {

    /** Indica si muestra título formulario. */
	@ApiModelProperty(value = "Indica si muestra título formulario")
    private boolean mostrarTitulo;

    /** Título formulario. */
	@ApiModelProperty(value = "Título formulario")
    private String titulo;

    /** Plantillas. */
	@ApiModelProperty(value = "Plantillas")
    private List<RPlantillaFormulario> plantillas;

    /** Script plantillas. */
	@ApiModelProperty(value = "Script plantillas")
    private RScript scriptPlantillas;

    /** Páginas. */
	@ApiModelProperty(value = "Páginas")
    private List<RPaginaFormulario> paginas;

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
    public List<RPlantillaFormulario> getPlantillas() {
        return plantillas;
    }

    /**
     * Método para establecer plantillas.
     *
     * @param plantillas
     *            plantillas a establecer
     */
    public void setPlantillas(List<RPlantillaFormulario> plantillas) {
        this.plantillas = plantillas;
    }

    /**
     * Método de acceso a scriptPlantillas.
     *
     * @return scriptPlantillas
     */
    public RScript getScriptPlantillas() {
        return scriptPlantillas;
    }

    /**
     * Método para establecer scriptPlantillas.
     *
     * @param scriptPlantillas
     *            scriptPlantillas a establecer
     */
    public void setScriptPlantillas(RScript scriptPlantillas) {
        this.scriptPlantillas = scriptPlantillas;
    }

    /**
     * Método de acceso a paginas.
     *
     * @return paginas
     */
    public List<RPaginaFormulario> getPaginas() {
        return paginas;
    }

    /**
     * Método para establecer paginas.
     *
     * @param paginas
     *            paginas a establecer
     */
    public void setPaginas(List<RPaginaFormulario> paginas) {
        this.paginas = paginas;
    }

}
