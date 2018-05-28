package es.caib.sistrages.core.api.model.types;

/**
 * Enum para indicar el tipo de acción del historial de versiones de tramites.
 *
 * @author Indra
 *
 */
public enum TypeAccionHistorial {
	/**
	 * Creación de una versión de trámite.
	 */
	CREACION("C"),
	/**
	 * Bloqueo de una versión de trámite
	 */
	BLOQUEAR("B"),
	/**
	 * Desbloqueo de una versión de trámite
	 */
	DESBLOQUEAR("D"),
	/**
	 * Exportación de una versión de trámite
	 */
	EXPORTACION("E"),
	/**
	 * Importación de una versión de trámite
	 */
	IMPORTACION("I");

	/** Valor. **/
	private String valor;

	/**
	 * Constructor.
	 *
	 * @param iValor
	 */
	private TypeAccionHistorial(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeAccionHistorial fromString(final String text) {
		TypeAccionHistorial respuesta = null;
		if (text != null) {
			for (final TypeAccionHistorial b : TypeAccionHistorial.values()) {
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
