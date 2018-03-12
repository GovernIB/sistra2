package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar la presentación: E (electrónica) / P (Presencial)
 *
 * @author Indra
 *
 */
public enum TypeTamanyo {
	/**
	 * KILOBYTES.
	 */
	KILOBYTES("KB"),
	/**
	 * MEGABYTES
	 */
	MEGABYTES("MB");

	private String valor;

	TypeTamanyo(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static String fromEnum(final TypeTamanyo tipo) {
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
	public TypeTamanyo toEnum(final String text) {
		TypeTamanyo respuesta = null;
		if (text != null) {
			for (final TypeTamanyo b : TypeTamanyo.values()) {
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
