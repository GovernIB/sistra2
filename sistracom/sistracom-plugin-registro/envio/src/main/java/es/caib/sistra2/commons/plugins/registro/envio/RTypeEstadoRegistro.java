package es.caib.sistra2.commons.plugins.registro.envio;

/**
 * Estado verificación registro.
 *
 * @author Indra
 *
 */
public enum RTypeEstadoRegistro {
	/** Registro realizado **/
	REALIZADO("r"),
	/** Registro no realizado **/
	NO_REALIZADO("n"),
	/** En proceso **/
	EN_PROCESO("p");

	/**
	 * Ambito nombre;
	 */
	private String valor;

	/**
	 * Constructor.
	 *
	 * @param pValor Role name
	 */
	private RTypeEstadoRegistro(final String pValor) {
		valor = pValor;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text string
	 * @return TypeDocumento
	 */
	public static RTypeEstadoRegistro fromString(final String text) {
		RTypeEstadoRegistro respuesta = null;
		if (text != null) {
			for (final RTypeEstadoRegistro b : RTypeEstadoRegistro.values()) {
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
