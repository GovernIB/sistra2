package es.caib.sistramit.core.service.model.flujo.types;

public enum TypeEntregaEstado {

    /** Pendiente de entregar. */
    PENDIENTE_ENTREGAR("n"),
    /** Error en la entrega (iniciar de nuevo entrega). */
    ERROR_ENTREGA("e"),
    /** Reintentar entrega. */
    REINTENTAR_ENTREGA("r"),
    /** Entregado. */
    ENTREGADO("s");

    /**
     * Valor como string.
     */
    private final String stringValueEntregaEstado;

    /**
     * Constructor.
     *
     * @param value
     *            Valor como string.
     */
    private TypeEntregaEstado(final String value) {
        stringValueEntregaEstado = value;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return stringValueEntregaEstado;
    }

    /**
     * Método para From string de la clase TypeEntregaEstado.
     *
     * @param text
     *            Parámetro text
     * @return el type TypeEntregaEstado
     */
    public static TypeEntregaEstado fromString(final String text) {
        TypeEntregaEstado respuesta = null;
        if (text != null) {
            for (final TypeEntregaEstado b : TypeEntregaEstado.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }
        }
        return respuesta;
    }
}
