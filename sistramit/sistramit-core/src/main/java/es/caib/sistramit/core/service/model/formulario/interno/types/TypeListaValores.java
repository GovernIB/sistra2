package es.caib.sistramit.core.service.model.formulario.interno.types;

/**
 * Tipo para indicar lista valores.
 *
 * @author Indra
 *
 */
public enum TypeListaValores {

	/** Valores fijos. */
	FIJA("F"),
	/** Valores provienen de un dominio. */
	DOMINIO("D"),
	/** Valores provienen de un script. */
	SCRIPT("S");

	private String valor;

	private TypeListaValores(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeListaValores fromString(final String text) {
		TypeListaValores respuesta = null;
		if (text != null) {
			for (final TypeListaValores b : TypeListaValores.values()) {
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
