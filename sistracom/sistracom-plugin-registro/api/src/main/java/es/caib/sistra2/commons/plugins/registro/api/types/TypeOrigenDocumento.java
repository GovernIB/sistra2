package es.caib.sistra2.commons.plugins.registro.api.types;

/**
 * Origen del documento
 *
 */
public enum TypeOrigenDocumento {
    /** Origen ciudadano */
    CIUDADANO(0),
    /** Origen administracion */
    ADMINISTRACION(1);

    /**
     * Valor como string.
     */
    private final int valor;

    /**
     * Constructor.
     *
     * @param valor
     *            Valor como int.
     */
    private TypeOrigenDocumento(final int pValor) {
    	valor = pValor;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return String.valueOf(valor);
    }

    /**
     * Valor de tipo int.
     * @return
     */
    public int intValue() {
    	return valor;
    }

    /**
     * Obtiene enum desde string.
     *
     * @param text
     *            string
     * @return TypeOrigenDocumento
     */
    public static TypeOrigenDocumento fromString(final String text) {
    	TypeOrigenDocumento respuesta = null;
        if (text != null) {
            for (final TypeOrigenDocumento b : TypeOrigenDocumento.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }

        }
        return respuesta;
    }

}
