package es.caib.sistrages.rest.api.interna;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Plugin.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RPlugin", description = "Descripcion de RPlugin")
public class RPlugin {

    /** Tipo. */
	@ApiModelProperty(value = "Tipo")
    private String tipo;

    /** Classname. */
	@ApiModelProperty(value = "Classname")
    private String classname;

    /** Propiedades. */
	@ApiModelProperty(value = "Propiedades")
    private RListaParametros parametros;

    /**
     * Método de acceso a tipo.
     *
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     *
     * @param tipo
     *            tipo a establecer
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Método de acceso a classname.
     *
     * @return classname
     */
    public String getClassname() {
        return classname;
    }

    /**
     * Método para establecer classname.
     *
     * @param classname
     *            classname a establecer
     */
    public void setClassname(String classname) {
        this.classname = classname;
    }

    /**
     * Método de acceso a parametros.
     *
     * @return parametros
     */
    public RListaParametros getParametros() {
        return parametros;
    }

    /**
     * Método para establecer parametros.
     *
     * @param parametros
     *            parametros a establecer
     */
    public void setParametros(RListaParametros parametros) {
        this.parametros = parametros;
    }

}
