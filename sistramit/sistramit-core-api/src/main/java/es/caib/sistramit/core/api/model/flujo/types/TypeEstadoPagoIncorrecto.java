package es.caib.sistramit.core.api.model.flujo.types;

//TODO PAGOEXT SE HA QUEDADO EN 1 SOLO ESTADO. MANTENEMOS POR SI SE HA DE AUMENTAR ESTADOS
/**
 * Indica el estado de un pago cuando es incorrecto.
 *
 * @author Indra
 *
 */
public enum TypeEstadoPagoIncorrecto {
    /**
     * Pendiente de confirmar. Se ha iniciado el proceso de pagos pero no se sabe si se ha realizado. Se
     * debe verificar si se ha realizado (Código String: i).
     */
    PAGO_INICIADO("i"),
    /**
     * Ha excedido el tiempo maximo para pagar. (Código String: x).
     */
    TIEMPO_EXCEDIDO("x");

    /**
     * Valor como string.
     */
    private final String stringValuePagoIncorrecto;

    /**
     * Constructor.
     *
     * @param value
     *            Valor como string.
     */
    private TypeEstadoPagoIncorrecto(final String value) {
        stringValuePagoIncorrecto = value;
    }


    @Override
    public String toString() {
        return stringValuePagoIncorrecto;
    }

    /**
     * Método para From string de la clase TypeEstadoPagoIncorrecto.
     *
     * @param text
     *            Parámetro text
     * @return el type estado pago incorrecto
     */
    public static TypeEstadoPagoIncorrecto fromString(final String text) {
        TypeEstadoPagoIncorrecto respuesta = null;
        if (text != null) {
            for (final TypeEstadoPagoIncorrecto b : TypeEstadoPagoIncorrecto
                    .values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }

        }
        return respuesta;
    }
}
