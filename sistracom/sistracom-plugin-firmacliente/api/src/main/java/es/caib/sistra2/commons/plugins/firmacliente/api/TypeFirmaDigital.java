package es.caib.sistra2.commons.plugins.firmacliente.api;

/**
 * Indica tipo de firma digital.
 *
 * @author Indra
 *
 */
public enum TypeFirmaDigital {

	/**
	 * XAdES internally detached signature.
	 */
	XADES_DETACHED("TF02"),
	/**
	 * XAdES enveloped signature.
	 */
	XADES_ENVELOPED("TF03"),
	/**
	 * CAdES detached/explicit signature.
	 */
	CADES_DETACHED("TF04"),
	/**
	 * CAdES attached/implicit signature.
	 */
	CADES_ATTACHED("TF05"),
	/**
	 * PADES.
	 */
	PADES("TF06");

	/**
	 * Valor como string.
	 */
	private final String stringValueTypeFirmaDigital;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TypeFirmaDigital(final String value) {
		stringValueTypeFirmaDigital = value;
	}

	@Override
	public String toString() {
		return stringValueTypeFirmaDigital;
	}

	/**
	 * Método para From string de la clase TypeFirmaDigital.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type estado paso
	 */
	public static TypeFirmaDigital fromString(final String text) {
		TypeFirmaDigital respuesta = null;
		if (text != null) {
			for (final TypeFirmaDigital b : TypeFirmaDigital.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
