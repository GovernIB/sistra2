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

	TypeFormulario(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static String fromEnum(final TypeFormulario tipo) {
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
	public TypeFormulario toEnum(final String text) {
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
