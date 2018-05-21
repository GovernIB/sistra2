package es.caib.sistramit.core.api.model.comun.types;

/**
 * Tipo para indicar entorno.
 *
 * @author Indra
 *
 */
public enum TypeEntorno {
	/**
	 * Desarrollo.
	 */
	DESARROLLO("DES"),
	/**
	 * Preproducción.
	 */
	PREPRODUCCION("PRE"),
	/**
	 * Producción.
	 */
	PRODUCCION("PRO");

	/**
	 * Valor como string.
	 */
	private final String stringValue;

	/**
	 * Constructor.
	 *
	 * @param valueSiNo
	 *            Valor como string.
	 */
	private TypeEntorno(final String valueSiNo) {
		stringValue = valueSiNo;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
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
	public static TypeEntorno fromString(final String text) {
		TypeEntorno respuesta = null;
		if (text != null) {
			for (final TypeEntorno b : TypeEntorno.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
