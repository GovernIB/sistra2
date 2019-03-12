package es.caib.sistramit.core.api.model.comun.types;

/**
 * Tipo aviso.
 *
 * @author Indra
 *
 */
public enum TypeValidacion {
	/**
	 * Información.
	 */
	INFO("info"),
	/**
	 * Warning.
	 */
	WARNING("warning"),
	/**
	 * Error.
	 */
	ERROR("error");

	/**
	 * Valor como string.
	 */
	private final String stringValueAviso;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeValidacion(final String value) {
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
	 *            string
	 * @return TypeValor
	 */
	public static TypeValidacion fromString(final String text) {
		TypeValidacion respuesta = null;
		if (text != null) {
			for (final TypeValidacion b : TypeValidacion.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}
}
