package es.caib.sistrages.core.api.model.types;

/**
 * Tipo etiqueta.
 *
 * @author Indra
 *
 */
public enum TypeEtiqueta {
	/**
	 * Normal.
	 */
	NORMAL("N"),
	/**
	 * Info.
	 */
	INFO("I"),
	/**
	 * Warning.
	 */
	WARNING("W"),
	/**
	 * Error.
	 */
	ERROR("E");

	private String valor;

	TypeEtiqueta(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeEtiqueta fromString(final String text) {
		TypeEtiqueta respuesta = null;
		if (text != null) {
			for (final TypeEtiqueta b : TypeEtiqueta.values()) {
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
