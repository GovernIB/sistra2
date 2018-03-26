package es.caib.sistrages.core.api.model.types;

/**
 * Indica si un formulario de un trámite es obligatorio.
 *
 * @author Indra
 *
 */
public enum TypeFormularioObligatoriedad {
	/** OBLIGATORIO. **/
	OBLIGATORIO("S"),
	/** OPCIONAL. **/
	OPCIONAL("O"),
	/** DEPENDIENTE. **/
	DEPENDIENTE("D");

	private String valor;

	TypeFormularioObligatoriedad(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeFormularioObligatoriedad fromString(final String text) {
		TypeFormularioObligatoriedad respuesta = null;
		if (text != null) {
			for (final TypeFormularioObligatoriedad b : TypeFormularioObligatoriedad.values()) {
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
