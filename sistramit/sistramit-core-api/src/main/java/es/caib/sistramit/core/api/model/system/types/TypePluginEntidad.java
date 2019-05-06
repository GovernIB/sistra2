package es.caib.sistramit.core.api.model.system.types;

/**
 * Plugins entidad.
 *
 * @author Indra
 *
 */
public enum TypePluginEntidad {

	/**
	 * Catálogo procedimientos (Entidad).
	 */
	CATALOGO_PROCEDIMIENTOS("C"),
	/**
	 * Plugin de firma (Entidad).
	 */
	FIRMA("F"),
	/**
	 * Plugin de pagos (Entidad).
	 */
	PAGOS("P"),
	/**
	 * Plugin de formularios externo (Entidad).
	 */
	FORMULARIOS_EXTERNOS("G"),
	/**
	 * Plugin de registro (Entidad).
	 */
	REGISTRO("E"),
	/**
	 * Plugin de validación de firma (Entidad).
	 */
	VALIDACION_FIRMA_SERVIDOR("S");

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
	private TypePluginEntidad(final String valueStr) {
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
	public static TypePluginEntidad fromString(final String text) {
		TypePluginEntidad respuesta = null;
		if (text != null) {
			for (final TypePluginEntidad b : TypePluginEntidad.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
