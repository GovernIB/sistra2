package es.caib.sistramit.core.service.model.flujo.types;

/**
 * Estado de un paso de tramitación.
 *
 * @author Indra
 *
 */
public enum TypeEstadoPaso {

	/**
	 * No inicializado. Todavía no se han inicializado los datos del paso. (Código
	 * String: n).
	 */
	NO_INICIALIZADO("n"),
	/**
	 * Paso pendiente. (Código String: p).
	 */
	PENDIENTE("p"),
	/**
	 * Paso completado. (Código String: c).
	 */
	COMPLETADO("c"),
	/**
	 * Paso pendiente revisar porque se ha modificado un paso anterior. Se deben
	 * revisar los datos del paso. (Código String: r).
	 */
	REVISAR("r");
	/**
	 * Valor como string.
	 */
	private final String stringValueEstadoPaso;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeEstadoPaso(final String value) {
		stringValueEstadoPaso = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueEstadoPaso;
	}

	/**
	 * Método para From string de la clase TypeEstadoPaso.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type estado paso
	 */
	public static TypeEstadoPaso fromString(final String text) {
		TypeEstadoPaso respuesta = null;
		if (text != null) {
			for (final TypeEstadoPaso b : TypeEstadoPaso.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
