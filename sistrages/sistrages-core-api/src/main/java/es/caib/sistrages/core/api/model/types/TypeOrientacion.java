package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar la orientación (vertical o horizontal).
 *
 * @author Indra
 *
 */
public enum TypeOrientacion {

	/** Vertical. */
	VERTICAL("V"),
	/** Horizontal. */
	HORIZONTAL("H");

	private String valor;

	private TypeOrientacion(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeOrientacion fromString(final String text) {
		TypeOrientacion respuesta = null;
		if (text != null) {
			for (final TypeOrientacion b : TypeOrientacion.values()) {
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
