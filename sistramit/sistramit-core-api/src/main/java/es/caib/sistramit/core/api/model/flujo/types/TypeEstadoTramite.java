package es.caib.sistramit.core.api.model.flujo.types;

/**
 * Indica estado trámite.
 *
 * @author Indra
 *
 */
public enum TypeEstadoTramite {

	/**
	 * Trámite en fase de rellenado (Código String: r).
	 */
	RELLENANDO("r"),
	/**
	 * Indica que el trámite se ha finalizado (Código String: f).
	 */
	FINALIZADO("f");

	/**
	 * Valor como string.
	 */
	private final String stringValueEstadoTramite;

	/**
	 * Constructor.
	 *
	 * @param pvalue
	 *            Valor a devolver como string.
	 */
	private TypeEstadoTramite(final String pvalue) {
		stringValueEstadoTramite = pvalue;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueEstadoTramite;
	}

	/**
	 * Método para From string de la clase TypeEstadoTramite.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type estado tramite
	 */
	public static TypeEstadoTramite fromString(final String text) {
		TypeEstadoTramite respuesta = null;
		if (text != null) {
			for (final TypeEstadoTramite b : TypeEstadoTramite.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
