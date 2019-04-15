package es.caib.sistrages.core.api.model.types;

/**
 * Enum para indicar el tipo de error de validacion.
 *
 * @author Indra
 *
 */
public enum TypeErrorValidacion {

	LITERALES("L"), SCRIPTS("S"), DOMINIOS("D"), FORMATEADOR("F"), DATOS_REGISTRO("R");

	/** Valor. **/
	private String valor;

	/** Constructor. **/
	private TypeErrorValidacion(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeErrorValidacion fromString(final String text) {
		TypeErrorValidacion respuesta = null;
		if (text != null) {
			for (final TypeErrorValidacion b : TypeErrorValidacion.values()) {
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
