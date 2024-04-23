package es.caib.sistramit.core.service.model.flujo.types;

/**
 * Indica estado trámite.
 *
 * @author Indra
 *
 */
public enum TypeEntregar {

	/**
	 * No entregar (Código String: x).
	 */
	NO_ENTREGAR("r"),
	/**
	 * Entregar de forma inmediata (Código String: i).
	 */
	ENTREGA_INMEDIATA("i"),
	/**
	 * Entregar de forma periódica (Código String: p).
	 */
	ENTREGA_PERIODICA("p");

	/**
	 * Valor como string.
	 */
	private final String stringValueEntregar;

	/**
	 * Constructor.
	 *
	 * @param pvalue
	 *            Valor a devolver como string.
	 */
	private TypeEntregar(final String pvalue) {
		stringValueEntregar = pvalue;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueEntregar;
	}

	/**
	 * Método para From string de la clase TypeEntregar.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type estado tramite
	 */
	public static TypeEntregar fromString(final String text) {
		TypeEntregar respuesta = null;
		if (text != null) {
			for (final TypeEntregar b : TypeEntregar.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
