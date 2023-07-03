package es.caib.sistramit.core.api.model.formulario.types;

/**
 * Tipo edición.
 *
 * @author Indra
 *
 */
public enum TypeEdicion {
	/**
	 * Pantalla principal.
	 */
	PRINCIPAL("P"),
	/**
	 * Pantalla elemento (nuevo).
	 */
	ELEMENTO_NUEVO("EN"),
	/**
	 * Pantalla elemento (modificación).
	 */
	ELEMENTO_MODIFICACION("EM");

	/**
	 * Valor como string.
	 */
	private final String stringValueAviso;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TypeEdicion(final String value) {
		stringValueAviso = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueAviso;
	}

	/**
	 * Convierte string a TypeValor.
	 *
	 * @param text
	 *                 string
	 * @return TypeValor
	 */
	public static TypeEdicion fromString(final String text) {
		TypeEdicion respuesta = null;
		if (text != null) {
			for (final TypeEdicion b : TypeEdicion.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}
}
