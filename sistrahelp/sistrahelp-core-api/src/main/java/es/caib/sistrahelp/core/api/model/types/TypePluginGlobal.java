package es.caib.sistrahelp.core.api.model.types;

/**
 * Plugins globales.
 *
 * @author Indra
 *
 */
public enum TypePluginGlobal {

	/**
	 * Plugin de login (Global).
	 */
	LOGIN("L"),
	/**
	 * Plugin de email (Global).
	 */
	EMAIL("M"),
	/**
	 * Plugin de representacion (Global).
	 */
	REPRESENTACION("R"),
	/**
	 * Plugin de dominio remoto (Global).
	 */
	DOMINIO_REMOTO("D"),
	/**
	 * Plugin de conversión PDF (Global).
	 */
	CONVERSION_PDF("V");

	/**
	 * Valor como string.
	 */
	private final String stringValue;

	/**
	 * Constructor.
	 *
	 * @param valueStr Valor como string.
	 */
	private TypePluginGlobal(final String valueStr) {
		stringValue = valueStr;
	}

	@Override
	public String toString() {
		return stringValue;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text string
	 * @return TypeSiNo
	 */
	public static TypePluginGlobal fromString(final String text) {
		TypePluginGlobal respuesta = null;
		if (text != null) {
			for (final TypePluginGlobal b : TypePluginGlobal.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
