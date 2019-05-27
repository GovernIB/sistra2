package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Tipo de descarga justificante.
 *
 * @author Indra
 *
 */
public enum TypeDescargaJustificante {
	/**
	 * Descarga a un fichero (Código String: fic).
	 */
	FICHERO("fic"),
	/**
	 * Redirección a url externa (Código String: url).
	 */
	URL_EXTERNA("url"),
	/**
	 * Redirección a carpeta (Código String: car).
	 */
	CARPETA_CIUDADANA("car");

	/**
	 * Valor como string.
	 */
	private final String stringValueDescargaJustificante;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeDescargaJustificante(final String value) {
		stringValueDescargaJustificante = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueDescargaJustificante;
	}

	/**
	 * Método para From string de la clase TypePaso.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type paso
	 */
	public static TypeDescargaJustificante fromString(final String text) {
		TypeDescargaJustificante respuesta = null;
		if (text != null) {
			for (final TypeDescargaJustificante b : TypeDescargaJustificante.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
