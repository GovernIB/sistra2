package es.caib.sistramit.core.service.model.integracion.types;

/**
 * Tipo documento trámite catálogo procedimientos.
 *
 * @author Indra
 *
 */
public enum TypeDocumentoTramiteCP {
    /** Solicitud **/
    SOLICITUD("S"),
    /** Anexo. **/
    ANEXO("A");

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
    private TypeDocumentoTramiteCP(final String pValor) {
        valor = pValor;
    }

    /**
     * Obtiene enum desde string.
     *
     * @param text
     *            string
     * @return TypeSiNo
     */
    public static TypeDocumentoTramiteCP fromString(final String text) {
        TypeDocumentoTramiteCP respuesta = null;
        if (text != null) {
            for (final TypeDocumentoTramiteCP b : TypeDocumentoTramiteCP
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
