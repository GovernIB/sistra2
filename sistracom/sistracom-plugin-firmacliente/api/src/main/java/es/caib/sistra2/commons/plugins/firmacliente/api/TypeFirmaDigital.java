package es.caib.sistra2.commons.plugins.firmacliente.api;

/**
 * Indica tipo de firma digital.
 *
 * @author Indra
 *
 */
public enum TypeFirmaDigital {

	// TODO REVISAR TIPOS DE FIRMA DIGITAL, SI DESGLOSAMOS EN ATTACHED Y DETACHED
	// MIRAR DE HACER COINCIDIR CON EL TYPE DEL CORE-MODEL
	/**
	 * PADES.
	 */
	PADES("PADES"),
	/**
	 * XADES.
	 */
	XADES_DETACHED("XADES_DETACHED"),
	/**
	 * CADES.
	 */
	CADES_DETACHED("CADES_DETACHED");

	/**
	 * Valor como string.
	 */
	private final String stringValueTypeFirmaDigital;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
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
	 *            Parámetro text
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
