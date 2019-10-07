package es.caib.sistramit.core.api.model.security.types;

/**
 * Tipos QAA.
 *
 * @author Indra
 *
 */
public enum TypeQAA {
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
	private TypeQAA(final String value) {
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
	public static TypeQAA fromString(final String text) {
		TypeQAA respuesta = null;
		if (text != null) {
			for (final TypeQAA b : TypeQAA.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

	/**
	 * Compara QAA para saber si es superior a otro QAA
	 *
	 * @param qaaCompare
	 *                       otro QAA
	 * @return boolean
	 */
	public boolean esSuperior(final TypeQAA qaaCompare) {
		// Pasamos a entero para comparar
		return Integer.parseInt(this.toString()) > Integer.parseInt(qaaCompare.toString());
	}

}
