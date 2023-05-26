package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Tipos estado soporte.
 *
 * @author Indra
 *
 */
public enum TypeSoporteEstado {

	/**
	 * Pendiente (Código String: P).
	 */
	PENDIENTE("P"),
	/**
	 * En revisión (Código String: R).
	 */
	REVISANDO("R"),
	/**
	 * Finalizada revisión (Código String: F).
	 */
	FINALIZADA("F");

	/**
	 * Valor como string.
	 */
	private final String stringValueTipoSoporteEstado;

	/**
	 * Constructor.
	 *
	 * @param pvalue
	 *                   Valor a devolver como string.
	 */
	private TypeSoporteEstado(final String pvalue) {
		stringValueTipoSoporteEstado = pvalue;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueTipoSoporteEstado;
	}

	/**
	 * Método para From string de la clase TypeFlujoTramitacion.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type flujo tramitacion
	 */
	public static TypeSoporteEstado fromString(final String text) {
		TypeSoporteEstado respuesta = null;
		if (text != null) {
			for (final TypeSoporteEstado b : TypeSoporteEstado.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}
}
