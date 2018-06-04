package es.caib.sistramit.core.api.model.flujo.types;

/**
 *
 * Tipo de presentación: electrónica o presencial.
 *
 * @author Indra
 *
 */
public enum TypePresentacion {
	/**
	 * Electrónica (Código String: e).
	 */
	ELECTRONICA("e"),
	/**
	 * Presencial (Código String: p).
	 */
	PRESENCIAL("p");

	/**
	 * Valor como string.
	 */
	private final String stringValuePresentacion;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypePresentacion(final String value) {
		stringValuePresentacion = value;
	}

	@Override
	public String toString() {
		return stringValuePresentacion;
	}

	/**
	 * Método para From string de la clase TypePresentacion.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type plantilla anexo
	 */
	public static TypePresentacion fromString(final String text) {
		TypePresentacion respuesta = null;
		if (text != null) {
			for (final TypePresentacion b : TypePresentacion.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
