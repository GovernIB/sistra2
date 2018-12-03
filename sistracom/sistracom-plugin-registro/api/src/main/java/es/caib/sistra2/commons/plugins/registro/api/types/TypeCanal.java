package es.caib.sistra2.commons.plugins.registro.api.types;

/**
 * Canal de notificacion del interesado segun SICRES3
 *
 * @author Indra
 *
 */
public enum TypeCanal {
    /** Direccion postal **/
    POSTAL("01"),
    /** Direccion electronica habilitada **/
    DEH("02"),
    /** Comparecencia electronica **/
    COMPARECENCIA("03");

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
    private TypeCanal(final String pValor) {
        valor = pValor;
    }

    /**
     * Obtiene enum desde string.
     *
     * @param text
     *            string
     * @return TypeDocumento
     */
    public static TypeCanal fromString(final String text) {
        TypeCanal respuesta = null;
        if (text != null) {
            for (final TypeCanal b : TypeCanal
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
