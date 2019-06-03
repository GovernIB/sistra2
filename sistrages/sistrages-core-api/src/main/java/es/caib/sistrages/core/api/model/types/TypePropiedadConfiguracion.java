package es.caib.sistrages.core.api.model.types;

/**
 * Propiedades configuración.
 *
 * @author Indra
 *
 */
public enum TypePropiedadConfiguracion {

	// TODO V0 REVISAR NOMBRES PROPIEDADES
	/** Entorno. */
	ENTORNO("entorno"),
	/** Versión. */
	VERSION("sistra2.version"),
	/** Url asistente. */
	SISTRAMIT_URL("sistramit.url"),
	/** Prefijo plugin. */
	PLUGINS_PREFIJO("plugins.prefix"),
	/** Tinymce botón code activo. **/
	TINYMCE_CODE("tinymce.code");
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
			for (final TypePropiedadConfiguracion b : TypePropiedadConfiguracion.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
