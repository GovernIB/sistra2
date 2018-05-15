package es.caib.sistramit.core.service.model.flujo.types;

/**
 * Tipo de documento de persistencia.
 *
 * @author Indra
 *
 */
public enum TypeDocumentoPersistencia {

	/**
	 * Formulario (Código String: f).
	 */
	FORMULARIO("f"),
	/**
	 * Formulario (Código String: a).
	 */
	ANEXO("a"),
	/**
	 * Formulario (Código String: p).
	 */
	PAGO("p");

	// TODO PENDIENTE Ver si son necesarios mas tipos

	/**
	 * Valor como string.
	 */
	private final String stringValueDocumentoPersistencia;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeDocumentoPersistencia(final String value) {
		stringValueDocumentoPersistencia = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueDocumentoPersistencia;
	}

	/**
	 * Método para From string de la clase TypeDocumentoPersistencia.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type
	 */
	public static TypeDocumentoPersistencia fromString(final String text) {
		TypeDocumentoPersistencia respuesta = null;
		if (text != null) {
			for (final TypeDocumentoPersistencia b : TypeDocumentoPersistencia.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}
}
