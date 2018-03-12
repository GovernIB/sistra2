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

	TypeFormularioSoporte(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static String fromEnum(final TypeFormularioSoporte tipo) {
		return tipo.valor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public String fromEnum() {
		return this.valor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public TypeFormularioSoporte toEnum(final String text) {
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return valor;
	}
}
