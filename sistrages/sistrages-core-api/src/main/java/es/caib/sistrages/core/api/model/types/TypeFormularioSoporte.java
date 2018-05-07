package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar el tipo de formulario de soporte.
 *
 * @author Indra
 *
 */
public enum TypeFormularioSoporte {
	/**
	 * Responsable de incidencias.
	 */
	RESPONSABLE_DE_INCIDENCIAS("R"),
	/**
	 * Lista fija de emails.
	 */
	LISTA_DE_EMAILS("E");

	private String valor;

	private TypeFormularioSoporte(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeFormularioSoporte fromString(final String text) {
		TypeFormularioSoporte respuesta = null;
		if (text != null) {
			for (final TypeFormularioSoporte b : TypeFormularioSoporte.values()) {
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
