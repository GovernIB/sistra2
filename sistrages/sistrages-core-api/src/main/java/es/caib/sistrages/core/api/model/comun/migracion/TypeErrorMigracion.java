package es.caib.sistrages.core.api.model.comun.migracion;

/**
 * Tipo error migracion.
 *
 * @author Indra
 *
 */
public enum TypeErrorMigracion {
	/**
	 * Info.
	 */
	INFO("I"),
	/**
	 * Warning.
	 */
	WARNING("W"),
	/**
	 * Error.
	 */
	ERROR("E");

	private String valor;

	private TypeErrorMigracion(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeErrorMigracion fromString(final String text) {
		TypeErrorMigracion respuesta = null;
		if (text != null) {
			for (final TypeErrorMigracion b : TypeErrorMigracion.values()) {
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
