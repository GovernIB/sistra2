package es.caib.sistramit.core.service.model.system.types;

/**
 * Tipo para indicar los tipos de envío.
 *
 * @author Indra
 *
 */
public enum TypeEnvio {
	/** Email **/
	EMAIL("E");

	/**
	 * Ambito nombre;
	 */
	private String valor;

	/**
	 * Constructor.
	 *
	 * @param pValor
	 *                   Valor
	 */
	private TypeEnvio(final String pValor) {
		valor = pValor;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *                 string
	 * @return TypeSiNo
	 */
	public static TypeEnvio fromString(final String text) {
		TypeEnvio respuesta = null;
		if (text != null) {
			for (final TypeEnvio b : TypeEnvio.values()) {
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
