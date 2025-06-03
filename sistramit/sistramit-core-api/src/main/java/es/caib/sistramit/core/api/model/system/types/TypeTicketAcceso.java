package es.caib.sistramit.core.api.model.system.types;

/**
 * Tipo de ticket acceso.
 *
 * @author Indra
 *
 */
public enum TypeTicketAcceso {

	/**
	 * Acceso desde Carpeta (se abre una sesión de tramitación).
	 */
	CARPETA("CA"),
	/**
	 * Acceso de FH.
	 */
	FUNCIONARIO_HABILITADO("FH");

	/**
	 * Valor como string.
	 */
	private final String stringValueTicketAcceso;

	/**
	 * Constructor.
	 *
	 * @param value
	 *                  Valor como string.
	 */
	private TypeTicketAcceso(final String value) {
		stringValueTicketAcceso = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValueTicketAcceso;
	}

	/**
	 * Método para From string de la clase TypeAutenticacion.
	 *
	 * @param text
	 *                 Parámetro text
	 * @return el type autenticacion
	 */
	public static TypeTicketAcceso fromString(final String text) {
		TypeTicketAcceso respuesta = null;
		if (text != null) {
			for (final TypeTicketAcceso b : TypeTicketAcceso.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
