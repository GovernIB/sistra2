package es.caib.sistramit.frontend.model.types;

/**
 * Tipos estilo error: cambia estilo en función del tipo.
 *
 * @author Indra
 *
 */
public enum TypeEstiloError {

	/**
	 * ERROR .
	 */
	ERROR("error"),
	/**
	 * ALERTA.
	 */
	ALERTA("warning"),
	/**
	 * AUTENTICACION.
	 */
	AUTENTICACION("autenticacion");

	/**
	 * Valor como string.
	 */
	private final String stringValueError;

	/**
	 * Constructor.
	 *
	 * @param pvalue
	 *                   Valor a devolver como string.
	 */
	private TypeEstiloError(final String pvalue) {
		stringValueError = pvalue;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueError;
	}

	/**
	 * Método para From string de la clase TypeFlujoTramitacion.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type flujo tramitacion
	 */
	public static TypeEstiloError fromString(final String text) {
		TypeEstiloError respuesta = null;
		if (text != null) {
			for (final TypeEstiloError b : TypeEstiloError.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
