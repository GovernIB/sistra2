package es.caib.sistrages.core.api.model.types;

/**
 * Tipos de normativa: general o específica.
 *
 * @author Indra
 *
 */
public enum TypeNormativa {

	/**
	 * General (Código String: G).
	 */
	GENERAL("G"),
	/**
	 * Específica (Código String: E).
	 */
	ESPECIFICA("E");

	/**
	 * Valor como string.
	 */
	private final String valor;

	/**
	 * Constructor.
	 *
	 * @param pvalue
	 *                   Valor a devolver como string.
	 */
	private TypeNormativa(final String pvalue) {
		valor = pvalue;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return valor;
	}

	/**
	 * Método para obtener TypeNormativa a partir de un string.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type normativa
	 */
	public static TypeNormativa fromString(final String text) {
		TypeNormativa respuesta = null;
		if (text != null) {
			for (final TypeNormativa b : TypeNormativa.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}
}