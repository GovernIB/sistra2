package es.caib.sistra2.commons.plugins.autenticacion.api;

/**
 * Tipos de autenticación.
 *
 * @author Indra
 *
 */
public enum TipoAutenticacion {
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
    private TipoAutenticacion(final String value) {
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
     * @return el type es.caib.sistra2.commons.plugins.autenticacion
     */
    public static TipoAutenticacion fromString(final String text) {
        TipoAutenticacion respuesta = null;
        if (text != null) {
            for (final TipoAutenticacion b : TipoAutenticacion.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }

        }
        return respuesta;
    }

}
