package es.caib.sistrahelp.core.api.model.types;

/**
 * Propiedades configuración.
 *
 * @author Indra
 *
 */
public enum TypePropiedadConfiguracion {

	// TODO REVISAR NOMBRES PROPIEDADES
	PATH_PROPERTIES("es.caib.sistrahelp.properties.path"),
	/** Entorno. */
	ENTORNO("entorno"),
	/** STG Url. */
	SISTRAGES_URL("sistrages.rest.url"),
	/** STG Url. */
	SISTRAGES_USR("sistrages.rest.user"),
	/** STG Url. */
	SISTRAGES_PWD("sistrages.rest.pwd"),
	SISTRAGES_AYUDA_PATH("ayuda.sistrahelp.path"),

	PATH_FICHEROS_EXTERNOS("ficherosExternos.path"),

	SISTRAMIT_URL("sistramit.rest.url"),
	/** STG Url. */
	SISTRAMIT_USR("sistramit.rest.user"),
	/** STG Url. */
	SISTRAMIT_PWD("sistramit.rest.pwd");

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
