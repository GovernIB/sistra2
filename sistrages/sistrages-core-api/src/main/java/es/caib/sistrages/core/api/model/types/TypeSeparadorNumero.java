package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar separador numerico.
 *
 * @author Indra
 *
 */
public enum TypeSeparadorNumero {

	PUNTO_Y_COMA("PC"), COMA_Y_PUNTO("CP"), SIN_FORMATO("SF");

	private String valor;

	private TypeSeparadorNumero(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeSeparadorNumero fromString(final String text) {
		TypeSeparadorNumero respuesta = null;
		if (text != null) {
			for (final TypeSeparadorNumero b : TypeSeparadorNumero.values()) {
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
