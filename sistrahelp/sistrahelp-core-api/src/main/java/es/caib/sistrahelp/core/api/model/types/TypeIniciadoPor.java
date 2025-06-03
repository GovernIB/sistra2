package es.caib.sistrahelp.core.api.model.types;

public enum TypeIniciadoPor {


    CIUDADANO("ciudadano"),

    FUNCIONARIO_HABILITADO("fh");


    private final String stringValueEvento;

    private TypeIniciadoPor(final String value) {
        stringValueEvento = value;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return stringValueEvento;
    }

    /**
     * Método para From string de la clase TypeAutenticacion.
     *
     * @param text Parámetro text
     * @return el type autenticacion
     */
    public static TypeEvento fromString(final String text) {
        TypeEvento respuesta = null;
        if (text != null) {
            for (final TypeEvento b : TypeEvento.values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }

        }
        return respuesta;
    }
}
