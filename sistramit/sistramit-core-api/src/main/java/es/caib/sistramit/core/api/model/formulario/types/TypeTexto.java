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
	 * Texto hora (Código String: ho).
	 */
	HORA("ho"),
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
	 * Password.
	 */
	PASSWORD("pw"),
	/**
	 * IBAN.
	 */
	IBAN("ib");

	/**
	 * Valor como string.
	 */
	private final String stringValueTexto;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TypeTexto(final String value) {
		stringValueTexto = value;
	}

	@Override
	public String toString() {
		return stringValueTexto;
	}

}
