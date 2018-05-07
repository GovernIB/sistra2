package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar idioma.
 *
 * @author Indra
 *
 */
public enum TypeIdioma {

	/**
	 * Castellano.
	 */
	CASTELLANO("es"),
	/**
	 * Catalán.
	 */
	CATALAN("ca"),
	/**
	 * Inglés.
	 */
	INGLES("en"),
	/**
	 * Alemán.
	 */
	ALEMAN("de");

	/** Valor. */
	private String valor;

	/**
	 * Constructor
	 *
	 * @param iValor
	 *            Valor
	 */
	private TypeIdioma(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeIdioma fromString(final String text) {
		TypeIdioma respuesta = null;
		if (text != null) {
			for (final TypeIdioma b : TypeIdioma.values()) {
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
