package es.caib.sistra2.commons.plugins.autenticacion.api;

/**
 * Tipos QAA.
 *
 * @author Indra
 *
 */
public enum TipoQAA {
	/**
	 * Bajo (Código String: 1).
	 */
	BAJO("1"),
	/**
	 * Medio (Código String: 2).
	 */
	MEDIO("2"),
	/**
	 * Alto (Código String: 3).
	 */
	ALTO("3");

	/**
	 * Valor como string.
	 */
	private final String stringValueQaa;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TipoQAA(final String value) {
		stringValueQaa = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueQaa;
	}

	/**
	 * Método para From string de la clase TypeAutenticacion.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type autenticacion
	 */
	public static TipoQAA fromString(final String text) {
		TipoQAA respuesta = null;
		if (text != null) {
			for (final TipoQAA b : TipoQAA.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
