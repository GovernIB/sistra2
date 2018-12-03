package es.caib.sistramit.core.api.model.formulario.types;

/**
 * Separador de miles/decimales para un campo de tipo texto numérico.
 *
 * @author Indra
 *
 */
public enum TypeSeparador {
    /**
     * Punto para miles y coma para decimales (Código String: pc).
     */
    PUNTO_COMA("pc"),
    /**
     * Coma para miles y punto para decimales (Código String: cp).
     */
    COMA_PUNTO("cp");

    /**
     * Valor como string.
     */
    private final String stringValueSeparador;

    /**
     * Constructor.
     *
     * @param value
     *            Valor como string.
     */
    private TypeSeparador(final String value) {
        stringValueSeparador = value;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return stringValueSeparador;
    }

}
