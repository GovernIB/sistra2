package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Tipos destino: registro o envío.
 *
 * @author Indra
 *
 */
public enum TypeDestino {

	/**
	 * Registro (Código String: registro).
	 */
	REGISTRO("registro"),
	/**
	 * Envío (Código String: envio).
	 */
	ENVIO("envio");

	/**
	 * Valor como string.
	 */
	private final String stringValueTipoDestino;

	/**
	 * Constructor.
	 *
	 * @param pvalue
	 *                   Valor a devolver como string.
	 */
	private TypeDestino(final String pvalue) {
		stringValueTipoDestino = pvalue;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueTipoDestino;
	}

	/**
	 * Método para From string de la clase TypeFlujoTramitacion.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type flujo tramitacion
	 */
	public static TypeDestino fromString(final String text) {
		TypeDestino respuesta = null;
		if (text != null) {
			for (final TypeDestino b : TypeDestino.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}
}
