package es.caib.sistrages.rest.api;

import java.util.List;

/**
 * Script.
 *
 * @author Indra
 *
 */
public class Script {

    /** Script. */
    private String script;

    /** Literales. */
    private List<LiteralScript> literales;

    /**
     * Método de acceso a script.
     * 
     * @return script
     */
    public String getScript() {
        return script;
    }

    /**
     * Método para establecer script.
     * 
     * @param script
     *            script a establecer
     */
    public void setScript(String script) {
        this.script = script;
    }

    /**
     * Método de acceso a literales.
     * 
     * @return literales
     */
    public List<LiteralScript> getLiterales() {
        return literales;
    }

    /**
     * Método para establecer literales.
     * 
     * @param literales
     *            literales a establecer
     */
    public void setLiterales(List<LiteralScript> literales) {
        this.literales = literales;
    }

}
