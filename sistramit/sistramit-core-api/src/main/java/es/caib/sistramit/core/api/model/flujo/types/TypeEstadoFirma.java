package es.caib.sistramit.core.api.model.flujo.types;

/**
 *
 * Indica el estado de una firma: firmado, no firmado o rechazado.
 *
 * @author Indra
 *
 */
public enum TypeEstadoFirma {
	/**
	 * El firmante ha realizado la firma (Código String: s).
	 */
	FIRMADO("s"),
	/**
	 * El firmante todavía no ha realizado la firma (Código String: n).
	 */
	NO_FIRMADO("n");

	/**
	 * Valor como string.
	 */
	private final String stringValueEstadoFirma;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeEstadoFirma(final String value) {
		stringValueEstadoFirma = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueEstadoFirma;
	}

	/**
	 * Método para From string de la clase TypeEstadoFirma.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type estado firma
	 */
	public static TypeEstadoFirma fromString(final String text) {
		TypeEstadoFirma respuesta = null;
		if (text != null) {
			for (final TypeEstadoFirma b : TypeEstadoFirma.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}
}
