package es.caib.sistrages.core.api.model.types;

/**
 * Enum para indicar el tipo de plugin global.
 *
 * @author Indra
 *
 */
public enum TypePlugin {
	/**
	 * Plugin de login
	 */
	LOGIN("L"),
	/**
	 * Plugin de representacion
	 */
	REPRESENTACION("R"),
	/**
	 * Plugin de dominio remoto
	 */
	DOMINIO_REMOTO("D"),
	/**
	 * Plugin de firma.
	 */
	FIRMA("F");

	private String valor;

	TypePlugin(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public String fromEnum(final TypePlugin tipo) {
		return tipo.valor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public TypePlugin toEnum(final String text) {
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
