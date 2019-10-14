package es.caib.sistrages.core.api.model.types;

/**
 * Tipo cache.
 *
 * @author Indra
 *
 */
public enum TypeCache {

	/** Explícita (24 h). **/
	CACHE_EXPLICITA("E"),
	/** Implícito (1 min). **/
	CACHE_IMPLICITA("I"),
	/** NINGUNO. **/
	CACHE_NO("N");

	/**
	 * Cache;
	 */
	private String valor;

	/**
	 * Constructor.
	 *
	 * @param cache Role name
	 */
	private TypeCache(final String cache) {
		valor = cache;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text string
	 * @return TypeSiNo
	 */
	public static TypeCache fromString(final String text) {
		TypeCache respuesta = null;
		if (text != null) {
			for (final TypeCache b : TypeCache.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

	@Override
	public String toString() {
		return valor;
	}

}
