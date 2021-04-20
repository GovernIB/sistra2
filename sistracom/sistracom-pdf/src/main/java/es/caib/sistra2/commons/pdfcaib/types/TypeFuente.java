package es.caib.sistra2.commons.pdfcaib.types;

/**
 * @author Indra
 *
 */
public enum TypeFuente {

	TIMES("Times"),

	HELVETICA("Helvetica"),

	COURIER("Courier"),

	TIMES_ROMAN("Times-Roman"),

	ZAPFDINGBATS("ZapfDingbats"),

	NOTOSANSBOND("fontNotoSansBold"),

	NOTOSANS("fontNotoSans");
	/**
	 * Valor como string.
	 */
	private final String stringValue;

	/**
	 * Constructor.
	 *
	 * @param tipoIdentificacion Valor como string.
	 */
	private TypeFuente(final String tipoAmbito) {
		stringValue = tipoAmbito;
	}

	/**
	 * toString
	 */
	@Override
	public String toString() {
		return stringValue;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text string
	 * @return TypeSiNo
	 */
	public static TypeFuente fromString(final String text) {
		TypeFuente respuesta = null;
		if (text != null) {
			for (final TypeFuente b : TypeFuente.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
