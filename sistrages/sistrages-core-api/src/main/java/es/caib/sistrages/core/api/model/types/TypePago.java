package es.caib.sistrages.core.api.model.types;

/**
 * Indica si un pago es de tipo telemático o presencial.
 *
 * @author Indra
 *
 */
public enum TypePago {
	/** TELEMÁTICA. **/
	TELEMATICO("T"),
	/** PRESENCIAL. **/
	PRESENCIAL("P");

	private String valor;

	TypePago(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypePago toEnum(final String text) {
		TypePago respuesta = null;
		if (text != null) {
			for (final TypePago b : TypePago.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

	@Override
	public String toString() {
		return valor;
	}
}
