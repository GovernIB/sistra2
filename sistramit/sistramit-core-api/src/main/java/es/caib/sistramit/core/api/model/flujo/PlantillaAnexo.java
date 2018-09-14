package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypePlantillaAnexo;

/**
 * Plantilla anexo.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class PlantillaAnexo implements ModelApi {

    /**
     * Tipo de plantilla.
     */
    private TypePlantillaAnexo tipo;

    /**
     * Url plantilla (para externa será url externa, para interna será url
     * interna).
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

    /** Crea instancia (para bucles). */
    public static PlantillaAnexo createNewPlantillaAnexo(
            TypePlantillaAnexo pTipo, String pUrl) {
        final PlantillaAnexo p = new PlantillaAnexo();
        p.setTipo(pTipo);
        p.setUrl(pUrl);
        return p;
    }

}
