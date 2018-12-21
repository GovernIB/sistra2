package es.caib.sistra2.commons.plugins.registro.api.types;

/**
 * Tipo de documento según SICRES3
 *
 * @author Indra
 *
 */
public enum TypeDocumentoIdentificacion {
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
    private TypeDocumentoIdentificacion(final String pValor) {
        valor = pValor;
    }

    /**
     * Obtiene enum desde string.
     *
     * @param text
     *            string
     * @return TypeDocumento
     */
    public static TypeDocumentoIdentificacion fromString(final String text) {
        TypeDocumentoIdentificacion respuesta = null;
        if (text != null) {
            for (final TypeDocumentoIdentificacion b : TypeDocumentoIdentificacion
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
