package es.caib.sistra2.commons.plugins.registro.api.types;

/**
 * Estado verificación registro.
 *
 * @author Indra
 *
 */
public enum TypeEstadoRegistro {
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
	 * @param pValor
	 *                   Role name
	 */
	private TypeEstadoRegistro(final String pValor) {
		valor = pValor;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *                 string
	 * @return TypeDocumento
	 */
	public static TypeEstadoRegistro fromString(final String text) {
		TypeEstadoRegistro respuesta = null;
		if (text != null) {
			for (final TypeEstadoRegistro b : TypeEstadoRegistro.values()) {
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
