package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar la extension: T (Todas) / P (Personalizadas)
 *
 * @author Indra
 *
 */
public enum TypeExtension {
	/**
	 * TODAS.
	 */
	TODAS("T"),
	/**
	 * PERSONALIZADAS
	 */
	PERSONALIZADAS("P");

	private String valor;

	private TypeExtension(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeExtension fromString(final String text) {
		TypeExtension respuesta = null;
		if (text != null) {
			for (final TypeExtension b : TypeExtension.values()) {
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
