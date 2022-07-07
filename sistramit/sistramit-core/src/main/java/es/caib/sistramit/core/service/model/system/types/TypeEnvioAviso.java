package es.caib.sistramit.core.service.model.system.types;

/**
 * Tipo para indicar los tipos de envío.
 *
 * @author Indra
 *
 */
public enum TypeEnvioAviso {
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
	private TypeEnvioAviso(final String pValor) {
		valor = pValor;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *                 string
	 * @return TypeSiNo
	 */
	public static TypeEnvioAviso fromString(final String text) {
		TypeEnvioAviso respuesta = null;
		if (text != null) {
			for (final TypeEnvioAviso b : TypeEnvioAviso.values()) {
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
