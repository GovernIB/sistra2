package es.caib.sistrages.rest.api;

import java.util.List;

/**
 * Literal.
 *
 * @author Indra
 *
 */
public class Literal {

    /** Idiomas. */
    private List<LiteralIdioma> literales;

    /**
     * Método de acceso a literales.
     * 
     * @return literales
     */
    public List<LiteralIdioma> getLiterales() {
        return literales;
    }

    /**
     * Método para establecer literales.
     * 
     * @param literales
     *            literales a establecer
     */
    public void setLiterales(List<LiteralIdioma> literales) {
        this.literales = literales;
    }

}
