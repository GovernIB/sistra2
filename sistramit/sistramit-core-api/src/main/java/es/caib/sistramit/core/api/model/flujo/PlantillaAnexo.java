package es.caib.sistramit.core.api.model.flujo;

import java.io.Serializable;

import es.caib.sistramit.core.api.model.flujo.types.TypePlantillaAnexo;

/**
 * Plantilla anexo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlantillaAnexo implements Serializable {

    /**
     * Tipo de plantilla.
     */
    private TypePlantillaAnexo tipo;

    /**
     * Url plantilla externa.
     */
    private String url;

    /**
     * Método de acceso a tipo.
     *
     * @return tipo
     */
    public final TypePlantillaAnexo getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     *
     * @param pTipo
     *            tipo a establecer
     */
    public final void setTipo(final TypePlantillaAnexo pTipo) {
        tipo = pTipo;
    }

    /**
     * Método para Imprime de la clase PlantillaAnexo.
     *
     * @return el string
     */
    public final String print() {
        return "[tipo:]" + getTipo();
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

}
