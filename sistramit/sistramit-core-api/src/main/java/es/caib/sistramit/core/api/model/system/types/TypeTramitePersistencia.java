package es.caib.sistramit.core.api.model.system.types;

/**
 * Tipos de Tramite Persistencia
 *
 * @author Indra
 *
 */
public enum TypeTramitePersistencia {

	TODOS("T"),

	PAGO_REALIZADO_TRAMITE_SIN_FINALIZAR("X");

	/**
	 * Valor como string.
	 */
	private final String stringValue;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypeTramitePersistencia(final String value) {
		stringValue = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValue;
	}

	/**
	 * Método para From string de la clase TypeAutenticacion.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type autenticacion
	 */
	public static TypeTramitePersistencia fromString(final String text) {
		TypeTramitePersistencia respuesta = null;
		if (text != null) {
			for (final TypeTramitePersistencia b : TypeTramitePersistencia.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
