package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar la presentación: E (electrónica) / P (Presencial)
 *
 * @author Indra
 *
 */
public enum TypePresentacion {
	/**
	 * ELECTRONICA.
	 */
	ELECTRONICA("E"),
	/**
	 * PRESENCIAL
	 */
	PRESENCIAL("P");

	private String valor;

	private TypePresentacion(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypePresentacion fromString(final String text) {
		TypePresentacion respuesta = null;
		if (text != null) {
			for (final TypePresentacion b : TypePresentacion.values()) {
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
