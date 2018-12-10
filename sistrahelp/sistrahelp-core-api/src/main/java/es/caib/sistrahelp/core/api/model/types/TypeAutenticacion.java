package es.caib.sistrahelp.core.api.model.types;

/**
 * Tipos de autenticación.
 *
 * @author Indra
 *
 */
public enum TypeAutenticacion {
    /**
     * Autenticado (Código String: c).
     */
    AUTENTICADO("c"),
    /**
     * Autenticacion anónima (Código String: a).
     */
    ANONIMO("a");

    /**
     * Valor como string.
     */
    private final String stringValueAutenticacion;

    /**
     * Constructor.
     *
     * @param value
     *            Valor como string.
     */
    private TypeAutenticacion(final String value) {
        stringValueAutenticacion = value;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return stringValueAutenticacion;
    }

    /**
     * Método para From string de la clase TypeAutenticacion.
     *
     * @param text
     *            Parámetro text
     * @return el type autenticacion
     */
    public static TypeAutenticacion fromString(final String text) {
        TypeAutenticacion respuesta = null;
        if (text != null) {
            for (final TypeAutenticacion b : TypeAutenticacion.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }

        }
        return respuesta;
    }

}
