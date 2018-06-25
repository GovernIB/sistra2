package es.caib.sistrages.rest.api.interna;

import java.util.List;

/**
 * Literal.
 *
 * @author Indra
 *
 */
public class RLiteral {

    /** Idiomas. */
    private List<RLiteralIdioma> literales;

    /**
     * Método de acceso a literales.
     *
     * @return literales
     */
    public List<RLiteralIdioma> getLiterales() {
        return literales;
    }

    /**
     * Método para establecer literales.
     *
     * @param literales
     *            literales a establecer
     */
    public void setLiterales(List<RLiteralIdioma> literales) {
        this.literales = literales;
    }

}
