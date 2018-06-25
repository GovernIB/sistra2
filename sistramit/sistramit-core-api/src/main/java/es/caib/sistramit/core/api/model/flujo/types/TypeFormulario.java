package es.caib.sistramit.core.api.model.flujo.types;

/**
 *
 * Tipo de formulario: externo o interno.
 *
 * @author Indra
 *
 */
public enum TypeFormulario {
    /**
     * Interno: gestionado por la propia plataforma (Código String: i).
     */
    INTERNO("i"),
    /**
     * Externo: gestionado por un gestor de formularios externo (Código String:
     * e).
     */
    EXTERNO("e");

    /**
     * Valor como string.
     */
    private final String stringValueFormulario;

    /**
     * Constructor.
     *
     * @param value
     *            Valor como string.
     */
    private TypeFormulario(final String value) {
        stringValueFormulario = value;
    }

    @Override
    public String toString() {
        return stringValueFormulario;
    }

    /**
     * Método para From string de la clase TypeFormulario.
     *
     * @param text
     *            Parámetro text
     * @return el type formulario
     */
    public static TypeFormulario fromString(final String text) {
        TypeFormulario respuesta = null;
        if (text != null) {
            for (final TypeFormulario b : TypeFormulario.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }

        }
        return respuesta;
    }

}
