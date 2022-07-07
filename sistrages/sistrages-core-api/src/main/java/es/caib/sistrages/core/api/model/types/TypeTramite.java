package es.caib.sistrages.core.api.model.types;

/**
 * Tipos de flujo de tramitación: normalizado y personalizado.
 *
 * @author Indra
 *
 */
public enum TypeTramite {

	/**
	 * Trámite (Código String: T).
	 */
	TRAMITE("T"),
	/**
	 * Servicio (Código String: S).
	 */
	SERVICIO("S");

	/**
	 * Valor como string.
	 */
	private final String valor;

	/**
	 * Constructor.
	 *
	 * @param pvalue
	 *                   Valor a devolver como string.
	 */
	private TypeTramite(final String pvalue) {
		valor = pvalue;
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

	/**
	 * Método para From string de la clase TypeFlujoTramitacion.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type flujo tramitacion
	 */
	public static TypeTramite fromString(final String text) {
		TypeTramite respuesta = null;
		if (text != null) {
			for (final TypeTramite b : TypeTramite.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}
}
