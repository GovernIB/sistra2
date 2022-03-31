package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Indica la obligatoriedad de una firma.
 *
 * @author Indra
 *
 */
public enum TypeObligatoriedadFirmante {

	/**
	 * Obligatorio: es necesario firmar (Código String: s).
	 */
	OBLIGATORIO("s"),
	/**
	 * Opcional: no es necesario firmar (Código String: n).
	 */
	OPCIONAL("n"),
	/**
	 * Opcional requerido: entre todas los firmantes opcionales requeridos, al menos
	 * se requiere 1 de ellos (Código String: d).
	 */
	OPCIONAL_REQUERIDO("r");

	/**
	 * Valor como string.
	 */
	private final String stringValueObligatoriedad;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TypeObligatoriedadFirmante(final String value) {
		stringValueObligatoriedad = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueObligatoriedad;
	}

	/**
	 * Método para From string de la clase TypeObligatoriedad.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type obligatoriedad
	 */
	public static TypeObligatoriedadFirmante fromString(final String text) {
		TypeObligatoriedadFirmante respuesta = null;
		if (text != null) {
			for (final TypeObligatoriedadFirmante b : TypeObligatoriedadFirmante.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}
}
