package es.caib.sistrages.rest.api.interna;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Configuración para valores de un dominio.
 *
 * @author Indra
 *
 */
@ApiModel(value = "RListaDominio", description = "Descripcion de RListaDominio")
public class RListaDominio {

    /** Identificador dominio. */
	@ApiModelProperty(value = "Identificador dominio")
    private String dominio;

    /** Campo dominio para mapear código. */
	@ApiModelProperty(value = "Campo dominio para mapear código")
    private String campoCodigo;

    /** Campo dominio para mapear descripción. */
	@ApiModelProperty(value = "Campo dominio para mapear descripción")
    private String campoDescripcion;

    /** Lista parámetros. */
	@ApiModelProperty(value = "Lista parámetros")
    private List<RParametroDominio> parametros;

    /**
     * Método de acceso a dominio.
     *
     * @return dominio
     */
    public String getDominio() {
        return dominio;
    }

    /**
     * Método para establecer dominio.
     *
     * @param dominio
     *            dominio a establecer
     */
    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    /**
     * Método de acceso a campoCodigo.
     *
     * @return campoCodigo
     */
    public String getCampoCodigo() {
        return campoCodigo;
    }

    /**
     * Método para establecer campoCodigo.
     *
     * @param campoCodigo
     *            campoCodigo a establecer
     */
    public void setCampoCodigo(String campoCodigo) {
        this.campoCodigo = campoCodigo;
    }

    /**
     * Método de acceso a campoDescripción.
     *
     * @return campoDescripción
     */
    public String getCampoDescripcion() {
        return campoDescripcion;
    }

    /**
     * Método para establecer campoDescripción.
     *
     * @param campoDescripción
     *            campoDescripción a establecer
     */
    public void setCampoDescripcion(String campoDescripción) {
        this.campoDescripcion = campoDescripción;
    }

    /**
     * Método de acceso a parametros.
     *
     * @return parametros
     */
    public List<RParametroDominio> getParametros() {
        return parametros;
    }

    /**
     * Método para establecer parametros.
     *
     * @param parametros
     *            parametros a establecer
     */
    public void setParametros(List<RParametroDominio> parametros) {
        this.parametros = parametros;
    }

}
