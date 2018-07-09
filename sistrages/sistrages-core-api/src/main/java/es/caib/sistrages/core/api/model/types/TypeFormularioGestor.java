package es.caib.sistrages.core.api.model.types;

/**
 * Indica si un formulario de tipo interno / externo.
 *
 * @author Indra
 *
 */
public enum TypeFormularioGestor {
	/** INTERNO. **/
	INTERNO("I"),
	/** EXTERNO. **/
	EXTERNO("E");

	private String valor;

	private TypeFormularioGestor(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeFormularioGestor fromString(final String text) {
		TypeFormularioGestor respuesta = null;
		if (text != null) {
			for (final TypeFormularioGestor b : TypeFormularioGestor.values()) {
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
