package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar parametro dominio.
 *
 * @author Indra
 *
 */
public enum TypeParametroDominio {

	/** Valor constante. */
	CONSTANTE("C"),
	/** Valor de un campo. */
	CAMPO("M"),
	/** Valor de un parámetro. */
	PARAMETRO("P"),
	/** Valor de una sesion. **/
	SESION("S"),
	/** Texto de búsqueda **/
	BUSQUEDA("B");

	private String valor;

	private TypeParametroDominio(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeParametroDominio fromString(final String text) {
		TypeParametroDominio respuesta = null;
		if (text != null) {
			for (final TypeParametroDominio b : TypeParametroDominio.values()) {
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
