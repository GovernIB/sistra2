package es.caib.sistramit.core.api.model.flujo;

import es.caib.sistramit.core.api.model.flujo.types.TypePaso;

/**
 * Establece descripción del paso para mostrarlo en el Debe Saber.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public final class DescripcionPaso implements ModelApi {

    /**
     * Id del paso.
     */
    private String id;

    /**
     * Tipo de paso.
     */
    private TypePaso tipo;

    /**
     * Crea nueva instancia DescripcionPaso.
     *
     * @return DescripcionPaso
     */
    public static DescripcionPaso createNewDescripcionPaso() {
        return new DescripcionPaso();
    }

    /**
     * Método de acceso a id.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Indica id paso.
     *
     * @param pId
     *            id a establecer
     */
    public void setId(final String pId) {
        id = pId;
    }

    /**
     * Indica tipo de paso.
     *
     * @return tipo
     */
    public TypePaso getTipo() {
        return tipo;
    }

    /**
     * Método para establecer tipo.
     *
     * @param pTipo
     *            tipo a establecer
     */
    public void setTipo(final TypePaso pTipo) {
        tipo = pTipo;
    }

}
