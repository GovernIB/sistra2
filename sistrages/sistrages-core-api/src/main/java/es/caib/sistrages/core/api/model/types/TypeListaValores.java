package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar alineacion texto.
 *
 * @author Indra
 *
 */
public enum TypeListaValores {

	FIJA("F"), DOMINIO("D"), SCRIPT("S");

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
