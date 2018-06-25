package es.caib.sistrages.rest.api.interna;

import java.util.List;

/**
 * Configuración para valores de un dominio.
 *
 * @author Indra
 *
 */
public class RListaDominio {

    /** Identificador dominio. */
    private String dominio;

    /** Campo dominio para mapear código. */
    private String campoCodigo;

    /** Campo dominio para mapear descripción. */
    private String campoDescripción;

    /** Lista parámetros. */
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
    public String getCampoDescripción() {
        return campoDescripción;
    }

    /**
     * Método para establecer campoDescripción.
     *
     * @param campoDescripción
     *            campoDescripción a establecer
     */
    public void setCampoDescripción(String campoDescripción) {
        this.campoDescripción = campoDescripción;
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
