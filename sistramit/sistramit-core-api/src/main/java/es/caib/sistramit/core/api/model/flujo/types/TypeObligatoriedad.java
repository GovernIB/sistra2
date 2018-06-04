package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Indica la obligatoriedad de un documento.
 *
 * @author Indra
 *
 */
public enum TypeObligatoriedad {
	/**
	 * Obligatorio (Código String: s).
	 */
	OBLIGATORIO("s"),
	/**
	 * Opcional (Código String: n).
	 */
	OPCIONAL("n"),
	/**
	 * Dependiente (Código String: d).
	 */
	DEPENDIENTE("d");

	/**
	 * Valor como string.
	 */
	private final String stringValueObligatoriedad;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeObligatoriedad(final String value) {
		stringValueObligatoriedad = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueObligatoriedad;
	}

	/**
	 * Método para From string de la clase TypeObligatoriedad.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type obligatoriedad
	 */
	public static TypeObligatoriedad fromString(final String text) {
		TypeObligatoriedad respuesta = null;
		if (text != null) {
			for (final TypeObligatoriedad b : TypeObligatoriedad.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}
}
