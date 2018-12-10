package es.caib.sistrahelp.core.api.model.types;

/**
 *
 * Tipo de estado pago
 *
 * @author Indra
 *
 */
public enum TypeEstadoPago {
	COMPLETADO("C"), EN_CURSO("EC"), TIEMPO_EXCEDIDO("TE");

	/**
	 * Valor como string.
	 */
	private final String stringValuePresentacion;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeEstadoPago(final String value) {
		stringValuePresentacion = value;
	}

	@Override
	public String toString() {
		return stringValuePresentacion;
	}

	/**
	 * Método para From string de la clase TypePresentacion.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type plantilla anexo
	 */
	public static TypeEstadoPago fromString(final String text) {
		TypeEstadoPago respuesta = null;
		if (text != null) {
			for (final TypeEstadoPago b : TypeEstadoPago.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
