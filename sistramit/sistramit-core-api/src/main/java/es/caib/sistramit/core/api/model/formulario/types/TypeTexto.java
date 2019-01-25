package es.caib.sistramit.core.api.model.formulario.types;

/**
 * Tipos para un campo de tipo texto.
 *
 * @author Indra
 *
 */
public enum TypeTexto {
	/**
	 * Texto alfanumérico (Código String: an).
	 */
	NORMAL("an"),
	/**
	 * Expresión regular (Código String: ex).
	 */
	EXPRESION_REGULAR("ex"),
	/**
	 * Texto númerico (Código String: nu).
	 */
	NUMERO("nu"),
	/**
	 * Texto fecha (Código String: fe).
	 */
	FECHA("fe"),
	/**
	 * Texto fecha (Código String: em).
	 */
	EMAIL("em"),
	/**
	 * Texto fecha (Código String: id).
	 */
	IDENTIFICADOR("id"),
	/**
	 * Texto fecha (Código String: cp).
	 */
	CODIGO_POSTAL("cp"),
	/**
	 * Texto fecha (Código String: te).
	 */
	TELEFONO("te"),
	/**
	 * Texto oculto (Código String: oc).
	 */
	OCULTO("oc"),
	/**
	 * Password.
	 */
	PASSWORD("pw");

	/**
	 * Valor como string.
	 */
	private final String stringValueTexto;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeTexto(final String value) {
		stringValueTexto = value;
	}

	@Override
	public String toString() {
		return stringValueTexto;
	}

	/**
	 * Convierte desde string.
	 *
	 * @param text
	 *            string
	 * @return tipo
	 */
	public static TypeTexto fromString(final String text) {
		TypeTexto respuesta = null;
		if (text != null) {
			for (final TypeTexto b : TypeTexto.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
