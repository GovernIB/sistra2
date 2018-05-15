package es.caib.sistramit.core.api.model.comun.types;

/**
 * Tipo para indicar si (s) y no (n).
 *
 * @author Indra
 *
 */
public enum TypeSiNo {
	/**
	 * Si (Código String: s).
	 */
	SI("s"),
	/**
	 * No (Código String: n).
	 */
	NO("n");

	/**
	 * Valor como string.
	 */
	private final String stringValueSino;

	/**
	 * Constructor.
	 *
	 * @param valueSiNo
	 *            Valor como string.
	 */
	private TypeSiNo(final String valueSiNo) {
		stringValueSino = valueSiNo;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueSino;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *            string
	 * @return TypeSiNo
	 */
	public static TypeSiNo fromString(final String text) {
		TypeSiNo respuesta = null;
		if (text != null) {
			for (final TypeSiNo b : TypeSiNo.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

	/**
	 * Obtiene enum desde booleano.
	 *
	 * @param text
	 *            string
	 * @return TypeSiNo
	 */
	public static TypeSiNo fromBoolean(final boolean si) {
		TypeSiNo respuesta = null;
		if (si) {
			respuesta = TypeSiNo.SI;
		} else {
			respuesta = TypeSiNo.NO;
		}
		return respuesta;
	}

}
