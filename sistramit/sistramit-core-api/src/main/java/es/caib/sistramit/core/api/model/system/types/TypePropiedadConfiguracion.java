package es.caib.sistramit.core.api.model.system.types;

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
    /** STG Url. */
    SISTRAGES_URL("sistrages.url"),
    /** STG Url. */
    SISTRAGES_USR("sistrages.usr"),
    /** STG Url. */
    SISTRAGES_PWD("sistrages.pwd"),

    /** Url asistente. */
    SISTRAMIT_URL("sistramit.url"),
    /** Idiomas soportados. */
    IDIOMAS_SOPORTADOS("sistramit.idiomas"),
    /**
     * Realiza chequeo de si los datos de persistencia del paso se corresponden
     * con los almacenados en BBDD (true/false). Una vez depurada la aplicacion
     * se puede deshabilitar
     */
    CHECK_PERSISTENCIA_POSTACCION("sistramit.checkPersistenciaPostAccion"),
    /** Timeout sesión web (minutos). */
    TIMEOUT_SESION_WEB("sesionweb.timeout"),
    /** Prefijo plugin. */
    PLUGINS_PREFIJO("plugins.prefix");

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
