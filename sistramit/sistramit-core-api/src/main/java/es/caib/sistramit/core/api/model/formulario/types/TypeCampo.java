package es.caib.sistramit.core.api.model.formulario.types;

/**
 * Tipos de campo.
 *
 * @author Indra
 *
 */
public enum TypeCampo {
	/**
	 * Campo de texto (Código String: texto).
	 */
	TEXTO("texto"),
	/**
	 * Campo de verificación (Código String: verificacion).
	 */
	VERIFICACION("verificacion"),
	/**
	 * Campo de selección (Código String: selector).
	 */
	SELECTOR("selector"),
	/**
	 * Captcha.
	 */
	CAPTCHA("captcha");

	/**
	 * Valor como string.
	 */
	private final String stringValueCampo;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeCampo(final String value) {
		stringValueCampo = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueCampo;
	}

	/**
	 * Convierte string a TypeCampo.
	 *
	 * @param text
	 *            string
	 * @return TypeValor
	 */
	public static TypeCampo fromString(final String text) {
		TypeCampo respuesta = null;
		if (text != null) {
			for (final TypeCampo b : TypeCampo.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

}
