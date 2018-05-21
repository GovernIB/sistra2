package es.caib.sistrages.core.api.model.types;

/**
 * Enum para indicar el tipo de acción del historial de versiones de tramites.
 *
 * @author Indra
 *
 */
public enum TypeAccion {
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
	IMPORTACION("I"),
	/**
	 * Creación de un objeto a través de un clonado.
	 */
	CLONADO_CREACION("L"),
	/**
	 * Objeto clonado a partir del cual se crea otro.
	 */
	CLONADO_OBJETO("O");

	/** Valor. **/
	private String valor;

	/**
	 * Constructor.
	 * 
	 * @param iValor
	 */
	private TypeAccion(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeAccion fromString(final String text) {
		TypeAccion respuesta = null;
		if (text != null) {
			for (final TypeAccion b : TypeAccion.values()) {
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
