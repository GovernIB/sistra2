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
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeTamanyo fromString(final String text) {
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

	@Override
	public String toString() {
		return valor;
	}
}
