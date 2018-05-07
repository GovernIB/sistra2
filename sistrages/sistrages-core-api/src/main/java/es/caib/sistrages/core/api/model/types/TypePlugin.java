package es.caib.sistrages.core.api.model.types;

/**
 * Enum para indicar el tipo de plugin global.
 *
 * @author Indra
 *
 */
public enum TypePlugin {
	/**
	 * Plugin de login (Global).
	 */
	LOGIN("L"),
	/**
	 * Plugin de representacion (Global).
	 */
	REPRESENTACION("R"),
	/**
	 * Plugin de dominio remoto (Global).
	 */
	DOMINIO_REMOTO("D"),
	/**
	 * Catálogo procedimientos (Entidad).
	 */
	CATALOGO_PROCEDIMIENTOS("C"),
	/**
	 * Plugin de firma (Entidad).
	 */
	FIRMA("F"),
	/**
	 * Plugin de pagos (Entidad).
	 */
	PAGOS("P"),
	/**
	 * Plugin de registro (Entidad).
	 */
	FORMULARIOS_EXTERNOS("G"),
	/**
	 * Plugin de formularios externo (Entidad).
	 */
	REGISTRO("E");

	private String valor;

	private TypePlugin(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypePlugin fromString(final String text) {
		TypePlugin respuesta = null;
		if (text != null) {
			for (final TypePlugin b : TypePlugin.values()) {
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
