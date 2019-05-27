package es.caib.sistra2.commons.plugins.registro.api.types;

/**
 * Tipo de descarga justificante.
 *
 * @author Indra
 *
 */
public enum TypeJustificante {
	/**
	 * Descarga a un fichero (Código String: fichero).
	 */
	FICHERO("fichero"),
	/**
	 * Redirección a url externa (Código String: url).
	 */
	URL_EXTERNA("url"),
	/**
	 * Redirección a carpeta (Código String: carpeta).
	 */
	CARPETA_CIUDADANA("carpeta");

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
	private TypeJustificante(final String value) {
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
	public static TypeJustificante fromString(final String text) {
		TypeJustificante respuesta = null;
		if (text != null) {
			for (final TypeJustificante b : TypeJustificante.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
