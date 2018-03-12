package es.caib.sistrages.core.api.model.types;

/**
 * Indica si un formulario de tipo interno / externo.
 *
 * @author Indra
 *
 */
public enum TypeInterno {
	/** INTERNO. **/
	INTERNO("I"),
	/** EXTERNO. **/
	EXTERNO("E");

	private String valor;

	TypeInterno(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static String fromEnum(final TypeInterno tipo) {
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
	public TypeInterno toEnum(final String text) {
		TypeInterno respuesta = null;
		if (text != null) {
			for (final TypeInterno b : TypeInterno.values()) {
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
