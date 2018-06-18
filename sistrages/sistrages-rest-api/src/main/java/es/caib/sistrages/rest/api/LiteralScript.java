package es.caib.sistrages.rest.api;

/**
 * Mensajes de literales usados en script.
 *
 * @author Indra
 *
 */
public class LiteralScript {

    /** Identificador. */
    private String identificador;

    /** Literal. */
    private String literal;

    /**
     * Método de acceso a identificador.
     * 
     * @return identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Método para establecer identificador.
     * 
     * @param identificador
     *            identificador a establecer
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Método de acceso a literal.
     * 
     * @return literal
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Método para establecer literal.
     * 
     * @param literal
     *            literal a establecer
     */
    public void setLiteral(String literal) {
        this.literal = literal;
    }

}
