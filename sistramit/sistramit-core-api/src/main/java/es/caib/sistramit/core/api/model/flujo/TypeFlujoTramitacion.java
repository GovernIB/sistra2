package es.caib.sistramit.core.api.model.flujo;

/**
 * Tipos de flujo de tramitación: normalizado y personalizado.
 *
 * @author Indra
 *
 */
public enum TypeFlujoTramitacion {

	/**
	 * Flujo normalizado (Código String: n).
	 */
	NORMALIZADO("n"),
	/**
	 * Flujo personalizado (Código String: p).
	 */
	PERSONALIZADO("p");

	/**
	 * Valor como string.
	 */
	private final String stringValueTipoFlujo;

	/**
	 * Constructor.
	 *
	 * @param pvalue
	 *            Valor a devolver como string.
	 */
	private TypeFlujoTramitacion(final String pvalue) {
		stringValueTipoFlujo = pvalue;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueTipoFlujo;
	}

	/**
	 * Método para From string de la clase TypeFlujoTramitacion.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type flujo tramitacion
	 */
	public static TypeFlujoTramitacion fromString(final String text) {
		TypeFlujoTramitacion respuesta = null;
		if (text != null) {
			for (final TypeFlujoTramitacion b : TypeFlujoTramitacion.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}
}
