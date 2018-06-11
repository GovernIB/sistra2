package es.caib.sistramit.core.api.model.comun.types;

/**
 * Propiedades configuración.
 *
 * @author Indra
 *
 */
public enum TypePropiedadConfiguracion {

    // TODO REVISAR NOMBRES PROPIEDADES

    /** Entorno. */
    ENTORNO("entorno"),
    /** Url asistente. */
    URL_SISTRAMIT("sistramit.url"),
    /** Idiomas soportados. */
    IDIOMAS_SOPORTADOS("sistramit.idiomas"),
    /** Revision invalidaciones. */
    REVISION_INVALIDACIONES("sistramit.revisionInvalidaciones");

    /**
     * Valor como string.
     */
    private final String stringValue;

    /**
     * Constructor.
     *
     * @param valueStr
     *            Valor como string.
     */
    private TypePropiedadConfiguracion(final String valueStr) {
        stringValue = valueStr;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    /**
     * Obtiene enum desde string.
     *
     * @param text
     *            string
     * @return TypeSiNo
     */
    public static TypePropiedadConfiguracion fromString(final String text) {
        TypePropiedadConfiguracion respuesta = null;
        if (text != null) {
            for (final TypePropiedadConfiguracion b : TypePropiedadConfiguracion
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
