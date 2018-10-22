package es.caib.sistra2.commons.plugins.registro.api;

/**
 * Tipo de registro (Entrada/Salida).
 *
 * @author Indra
 *
 */
public enum TypeRegistro {
    /** Registro de entrada **/
    REGISTRO_ENTRADA("E"),
    /** Registro de salida. **/
    REGISTRO_SALIDA("S");

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
    private TypeRegistro(final String pValor) {
        valor = pValor;
    }

    /**
     * Obtiene enum desde string.
     *
     * @param text
     *            string
     * @return TypeRegistro
     */
    public static TypeRegistro fromString(final String text) {
        TypeRegistro respuesta = null;
        if (text != null) {
            for (final TypeRegistro b : TypeRegistro
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
