package es.caib.sistra2.commons.plugins.registro.api.types;

/**
 * Autenticidad del documento según SICRES3
 *
 * @author Indra
 *
 */
public enum TypeValidez {
    /** Copia **/
    COPIA("01"),
    /** Copia compulsada **/
    COPIA_COMPULSADA("02"),
    /** Copia original **/
    COPIA_ORIGINAL("03"),
    /** Original **/
    ORIGINAL("04");

    /**
     * Ambito nombre;
     */
    private String valor;

    /**
     * Constructor.
     *
     * @param pValor
     *            Role name
     */
    private TypeValidez(final String pValor) {
        valor = pValor;
    }

    /**
     * Obtiene enum desde string.
     *
     * @param text
     *            string
     * @return TypeDocumento
     */
    public static TypeValidez fromString(final String text) {
        TypeValidez respuesta = null;
        if (text != null) {
            for (final TypeValidez b : TypeValidez
                    .values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }

        }
        return respuesta;
    }

    @Override
    public String toString() {
        return valor;
    }

}
