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

	TypePresentacion(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static String fromEnum(final TypePresentacion tipo) {
		return tipo.valor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public String fromEnum() {
		return this.valor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public TypePresentacion toEnum(final String text) {
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return valor;
	}
}
