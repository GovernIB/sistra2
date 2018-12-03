package es.caib.sistra2.commons.plugins.registro.api.types;

/**
 * Tipo de documento según SICRES3
 *
 * @author Indra
 *
 */
public enum TypeDocumental {
    /** Formulario **/
    FORMULARIO("01"),
    /** Adjunto al formulario **/
    ANEXO("02"),
    /** Fichero tecnico interno **/
    FICHERO_TECNICO("03");

    /**
     * Ambito nombre;
     */
    private String valor;

    /**
     * Constructor.
     *
     * @param pValor
     *            Role name
     */
    private TypeDocumental(final String pValor) {
        valor = pValor;
    }

    /**
     * Obtiene enum desde string.
     *
     * @param text
     *            string
     * @return TypeDocumento
     */
    public static TypeDocumental fromString(final String text) {
        TypeDocumental respuesta = null;
        if (text != null) {
            for (final TypeDocumental b : TypeDocumental
                    .values()) {
                if (text.equalsIgnoreCase(b.toString())) {
                    respuesta = b;
                    break;
                }
            }

        }
        return respuesta;
    }

    @Override
    public String toString() {
        return valor;
    }

}
