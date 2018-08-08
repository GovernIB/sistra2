package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Plantilla formulario.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPlantillaFormulario", description = "Descripcion de RPlantillaFormulario")
public class RPlantillaFormulario {

    /** Identificador plantilla. */
	@ApiModelProperty(value = "Identificador plantilla")
    private String identificador;

    /** Clase formateador (classname). */
	@ApiModelProperty(value = "Clase formateador (classname)")
    private String claseFormateador;

    /** Plantilla (path fichero). */
	@ApiModelProperty(value = "Plantilla (path fichero)")
    private String ficheroPlantilla;

    /** Plantilla defecto. */
	@ApiModelProperty(value = "Plantilla defecto")
    private boolean defecto;

    /**
     * Método de acceso a formateador.
     *
     * @return formateador
     */
    public String getClaseFormateador() {
        return claseFormateador;
    }

    /**
     * Método para establecer formateador.
     *
     * @param formateador
     *            formateador a establecer
     */
    public void setClaseFormateador(String formateador) {
        this.claseFormateador = formateador;
    }

    /**
     * Método de acceso a plantilla.
     *
     * @return plantilla
     */
    public String getFicheroPlantilla() {
        return ficheroPlantilla;
    }

    /**
     * Método para establecer plantilla.
     *
     * @param plantilla
     *            plantilla a establecer
     */
    public void setFicheroPlantilla(String plantilla) {
        this.ficheroPlantilla = plantilla;
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

    /**
     * Método de acceso a defecto.
     * 
     * @return defecto
     */
    public boolean isDefecto() {
        return defecto;
    }

    /**
     * Método para establecer defecto.
     * 
     * @param defecto
     *            defecto a establecer
     */
    public void setDefecto(boolean defecto) {
        this.defecto = defecto;
    }

}
