package es.caib.sistra2.commons.plugins.registro.api.types;

/**
 * Tipo de actuacion del interesado
 *
 * @author Indra
 *
 */
public enum TypeInteresado {
    /** Representante **/
    REPRESENTANTE("RPT"),
    /** Representado. **/
    REPRESENTADO("RPD");

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
    private TypeInteresado(final String pValor) {
        valor = pValor;
    }

    /**
     * Obtiene enum desde string.
     *
     * @param text
     *            string
     * @return TypeRegistro
     */
    public static TypeInteresado fromString(final String text) {
        TypeInteresado respuesta = null;
        if (text != null) {
            for (final TypeInteresado b : TypeInteresado
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
