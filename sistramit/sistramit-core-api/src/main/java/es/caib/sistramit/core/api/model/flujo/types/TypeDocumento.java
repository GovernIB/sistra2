package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Tipo de documento.
 *
 * @author Indra
 *
 */
public enum TypeDocumento {
	/**
	 * Formulario (Código String: f).
	 */
	FORMULARIO("f"),
	/**
	 * Anexo (Código String: a).
	 */
	ANEXO("a"),
	/**
	 * Pago (Código String: p).
	 */
	PAGO("p"),
	/**
	 * Justificante (Código String: j).
	 */
	JUSTIFICANTE("j");

	// TODO Ver si hacen falta mas tipos

	/**
	 * Valor como string.
	 */
	private final String stringValueDocumento;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeDocumento(final String value) {
		stringValueDocumento = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueDocumento;
	}

	/**
	 * Método para From string de la clase TypeDocumento.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type documento
	 */
	public static TypeDocumento fromString(final String text) {
		TypeDocumento respuesta = null;
		if (text != null) {
			for (final TypeDocumento b : TypeDocumento.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
