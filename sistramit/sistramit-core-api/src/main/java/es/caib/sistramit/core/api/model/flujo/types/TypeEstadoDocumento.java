package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Indica el estado de un documento: vacío, rellenado correctamente o rellenado
 * de forma incorrecta.
 *
 * @author Indra
 *
 */
public enum TypeEstadoDocumento {
	/**
	 * Vacío, todavía no ha sido rellenado (Código String: v).
	 */
	SIN_RELLENAR("v"),
	/**
	 * Rellenado incorrectamente (Código String: i).
	 */
	RELLENADO_INCORRECTAMENTE("i"),
	/**
	 * Rellenado correctamente (Código String: c).
	 */
	RELLENADO_CORRECTAMENTE("c");

	/**
	 * Valor como string.
	 */
	private final String stringValueEstadoDocumento;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeEstadoDocumento(final String value) {
		stringValueEstadoDocumento = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueEstadoDocumento;
	}

	/**
	 * Método para From string de la clase TypeEstadoDocumento.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type estado documento
	 */
	public static TypeEstadoDocumento fromString(final String text) {
		TypeEstadoDocumento respuesta = null;
		if (text != null) {
			for (final TypeEstadoDocumento b : TypeEstadoDocumento.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
