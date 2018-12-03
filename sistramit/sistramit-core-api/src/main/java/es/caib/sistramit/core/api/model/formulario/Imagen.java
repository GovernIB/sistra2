package es.caib.sistramit.core.api.model.formulario;

import java.io.Serializable;

/**
 * Imagen.
 */
@SuppressWarnings("serial")
public final class Imagen implements Serializable {

    /** Id imagen. */
    private String id;

    /** Url imagen. */
    private String src;

    /**
     * Método de acceso a id.
     *
     * @return el id
     */
    public String getId() {
        return id;
    }

    /**
     * Método para settear el campo id.
     *
     * @param pId
     *            el id a settear
     */
    public void setId(final String pId) {
        id = pId;
    }

    /**
     * Método de acceso a src.
     *
     * @return el src
     */
    public String getSrc() {
        return src;
    }

    /**
     * Método para settear el campo src.
     *
     * @param pSrc
     *            el src a settear
     */
    public void setSrc(final String pSrc) {
        src = pSrc;
    }

}
