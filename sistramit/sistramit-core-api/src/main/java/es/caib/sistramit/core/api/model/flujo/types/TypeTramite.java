package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Tipos de flujo de tramitación: normalizado y personalizado.
 *
 * @author Indra
 *
 */
public enum TypeTramite {

	/**
	 * Trámite (Código String: t).
	 */
	TRAMITE("t"),
	/**
	 * Servicio (Código String: s).
	 */
	SERVICIO("s");

	/**
	 * Valor como string.
	 */
	private final String stringValueTipoTramite;

	/**
	 * Constructor.
	 *
	 * @param pvalue
	 *                   Valor a devolver como string.
	 */
	private TypeTramite(final String pvalue) {
		stringValueTipoTramite = pvalue;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueTipoTramite;
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
