package es.caib.sistra2.commons.plugins.registro.api.types;

/**
 * Tipo de documento según SICRES3
 *
 * @author Indra
 *
 */
public enum TypeDocumento {
    /** NIF **/
    NIF("N"),
    /** CIF **/
    CIF("C"),
    /** PASAPORTE **/
    PASAPORTE("P"),
    /** DOCUMENTO IDENTIFICACION EXTRANJEROS **/
    ID_EXTRANJERO("E"),
    /** OTRAS PERSONAS FISICAS **/
    OTROS("X"),
    /** CODIGO ORIGEN (CODIGO DIR3 SI ADMINISTRACION) **/
    ORIGEN("O");

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
    private TypeDocumento(final String pValor) {
        valor = pValor;
    }

    /**
     * Obtiene enum desde string.
     *
     * @param text
     *            string
     * @return TypeDocumento
     */
    public static TypeDocumento fromString(final String text) {
        TypeDocumento respuesta = null;
        if (text != null) {
            for (final TypeDocumento b : TypeDocumento
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
