package es.caib.sistra2.commons.plugins.autenticacion;

/**
 * Método de autenticación.
 *
 * @author Indra
 *
 */
public enum TipoMetodoAutenticacion {
    /**
     * ANONIMO.
     */
    ANONIMO("ANONIMO"),
    /**
     * CERTIFICADO_CLAVE.
     */
    CLAVE_CERTIFICADO("CLAVE_CERTIFICADO"),
    /**
     * CLAVE_PIN.
     */
    CLAVE_PIN("CLAVE_PIN"),
    /**
     * CLAVE_PERMANENTE.
     */
    CLAVE_PERMANENTE("CLAVE_PERMANENTE");

    /**
     * Valor como string.
     */
    private final String stringValueMetodoAuth;

    /**
     * Constructor.
     *
     * @param value
     *            Valor como string.
     */
    private TipoMetodoAutenticacion(final String value) {
        stringValueMetodoAuth = value;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return stringValueMetodoAuth;
    }

    /**
     * Convierte string a TypeValor.
     *
     * @param text
     *            string
     * @return TypeValor
     */
    public static TipoMetodoAutenticacion fromString(final String text) {
        TipoMetodoAutenticacion respuesta = null;
        if (text != null) {
            for (final TipoMetodoAutenticacion b : TipoMetodoAutenticacion
                    .values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }
        }
        return respuesta;
    }

    /**
     * Indica si es un metodo de Cl@ve.
     *
     * @return true si es un metodo de Cl@ve.
     */
    public boolean isClave() {
        return (this == CLAVE_CERTIFICADO || this == CLAVE_PERMANENTE
                || this == CLAVE_PIN);
    }

}
