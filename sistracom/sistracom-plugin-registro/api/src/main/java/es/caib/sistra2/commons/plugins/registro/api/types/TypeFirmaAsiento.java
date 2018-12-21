package es.caib.sistra2.commons.plugins.registro.api.types;

/**
 * Tipo de firma de un documento.
 *
 */
public enum TypeFirmaAsiento {
    /** Sin firma */
    SIN_FIRMA(0),
    /** Firma modo attached */
    FIRMA_ATTACHED(1),
    /** Firma modo detached */
    FIRMA_DETACHED(2);

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
    private TypeFirmaAsiento(final int pValor) {
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
     * @return TypeFirma
     */
    public static TypeFirmaAsiento fromString(final String text) {
    	TypeFirmaAsiento respuesta = null;
        if (text != null) {
            for (final TypeFirmaAsiento b : TypeFirmaAsiento.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }

        }
        return respuesta;
    }

}
