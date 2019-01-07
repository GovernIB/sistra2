package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Resultado de registro.
 *
 * @author Indra
 *
 */
public enum TypeResultadoRegistro {

	/**
	 * Error. (Código String: x).
	 */
	ERROR("x"),
	/**
	 * Respuesta no esperada. Reintentar. (Código String: r).
	 */
	REINTENTAR("r"),
	/**
	 * Registro completado. (Código String: s).
	 */
	CORRECTO("s");

	/**
	 * Valor como string.
	 */
	private final String estadoRegistroStringValue;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeResultadoRegistro(final String value) {
		estadoRegistroStringValue = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return estadoRegistroStringValue;
	}

	/**
	 * Método para From string de la clase TypeEstadoPaso.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type estado paso
	 */
	public static TypeResultadoRegistro fromString(final String text) {
		TypeResultadoRegistro respuesta = null;
		if (text != null) {
			for (final TypeResultadoRegistro b : TypeResultadoRegistro.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
