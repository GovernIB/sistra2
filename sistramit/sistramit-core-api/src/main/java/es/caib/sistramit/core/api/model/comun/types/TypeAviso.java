package es.caib.sistramit.core.api.model.comun.types;

/**
 * Tipo aviso.
 *
 * @author Indra
 *
 */
public enum TypeAviso {
	/**
	 * Información.
	 */
	// TODO VER DE CAMBIAR
	// INFO("info"),
	INFO("informa"),
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
	private TypeAviso(final String value) {
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
	public static TypeAviso fromString(final String text) {
		TypeAviso respuesta = null;
		if (text != null) {
			for (final TypeAviso b : TypeAviso.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}
}
