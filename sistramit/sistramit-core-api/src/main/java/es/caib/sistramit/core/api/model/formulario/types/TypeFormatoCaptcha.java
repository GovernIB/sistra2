package es.caib.sistramit.core.api.model.formulario.types;

/**
 * Tipo captcha.
 *
 * @author Indra
 *
 */
public enum TypeFormatoCaptcha {
	/**
	 * Imagen (Código String: i).
	 */
	IMAGEN("i"),
	/**
	 * Sonido (Código String: s).
	 */
	SONIDO("s");

	/**
	 * Valor como string.
	 */
	private final String stringValueCaptcha;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TypeFormatoCaptcha(final String value) {
		stringValueCaptcha = value;
	}

	@Override
	public String toString() {
		return stringValueCaptcha;
	}

}
