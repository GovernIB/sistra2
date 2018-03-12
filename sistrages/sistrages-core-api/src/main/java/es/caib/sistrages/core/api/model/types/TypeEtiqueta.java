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
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static String fromEnum(final TypeEtiqueta tipo) {
		return tipo.valor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public String fromEnum() {
		return this.valor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public TypeEtiqueta toEnum(final String text) {
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return valor;
	}

}
