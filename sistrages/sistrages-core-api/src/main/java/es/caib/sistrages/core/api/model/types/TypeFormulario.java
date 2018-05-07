package es.caib.sistrages.core.api.model.types;

/**
 * Indica si un formulario de tipo tramite o captura.
 *
 * @author Indra
 *
 */
public enum TypeFormulario {
	/** FORMULARIO TRAMITE. **/
	TRAMITE("T"),
	/** FORMULARIO CAPTURA. **/
	CAPTURA("C");

	private String valor;

	private TypeFormulario(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeFormulario fromString(final String text) {
		TypeFormulario respuesta = null;
		if (text != null) {
			for (final TypeFormulario b : TypeFormulario.values()) {
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
