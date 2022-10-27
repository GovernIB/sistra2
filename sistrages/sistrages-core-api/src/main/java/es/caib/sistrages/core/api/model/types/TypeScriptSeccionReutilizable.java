package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar el tipo de script de seccion reutilizable.
 *
 * @author Indra
 *
 */
public enum TypeScriptSeccionReutilizable implements TypeScript {

	/**
	 * Carga de datos inicial (Seccion Reutilizable Inicial)
	 */
	CARGA_DATOS_INICIAL("SRI");

	private String valor;

	private TypeScriptSeccionReutilizable(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeScriptSeccionReutilizable fromString(final String text) {
		TypeScriptSeccionReutilizable respuesta = null;
		if (text != null) {
			for (final TypeScriptSeccionReutilizable b : TypeScriptSeccionReutilizable.values()) {
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
