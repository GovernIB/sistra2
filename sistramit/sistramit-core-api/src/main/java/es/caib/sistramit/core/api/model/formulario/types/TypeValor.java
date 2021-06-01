package es.caib.sistramit.core.api.model.formulario.types;

/**
 * Tipo de valor de un campo.
 *
 * @author Indra
 *
 */
public enum TypeValor {

	/**
	 * Valor simple, texto plano (Código String: n).
	 */
	SIMPLE("s"),
	/**
	 * Valor compuesto por codigo / valor (Código String: i).
	 */
	INDEXADO("i"),
	/**
	 * Lista de valores indexados para selección múltiple (Código String: l)..
	 */
	LISTA_INDEXADOS("l"),
	/**
	 * Lista de elementos (Código String: e).
	 */
	LISTA_ELEMENTOS("e");

	/**
	 * Valor como string.
	 */
	private final String stringValueValor;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TypeValor(final String value) {
		stringValueValor = value;
	}

	@Override
	public String toString() {
		return stringValueValor;
	}

	/**
	 * Método para From string de la clase TypeValor.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type paso
	 */
	public static TypeValor fromString(final String text) {
		TypeValor respuesta = null;
		if (text != null) {
			for (final TypeValor b : TypeValor.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
