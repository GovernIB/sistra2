package es.caib.sistramit.core.api.model.formulario.types;

/**
 * Modo de selección para un campo de tipo selector.
 *
 * @author Indra
 *
 */
public enum TypeSelector {
	/**
	 * Selección a través de lista desplegable (Código String: d).
	 */
	LISTA("d"),
	/**
	 * Selección a través de radio buttons (Código String: U).
	 */
	UNICO("u"),
	/**
	 * Selección múltiple a través de una lista de checks (Código String: m).
	 */
	MULTIPLE("m");

	/**
	 * Valor como string.
	 */
	private final String stringValueSelector;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeSelector(final String value) {
		stringValueSelector = value;
	}

	@Override
	public String toString() {
		return stringValueSelector;
	}

	/**
	 * Convierte desde string.
	 *
	 * @param text
	 *            string
	 * @return tipo
	 */
	public static TypeSelector fromString(final String text) {
		TypeSelector respuesta = null;
		if (text != null) {
			for (final TypeSelector b : TypeSelector.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
