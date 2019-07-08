package es.caib.sistramit.core.service.model.formulario.interno.types;

/**
 * Tipo para indicar parametro dominio.
 *
 * @author Indra
 *
 */
public enum TypeParametroDominio {

	/** Valor que es una constante. */
	CONSTANTE("C"),
	/** Valor proveniente de un campo. */
	CAMPO("M"),
	/** Valor proveniente de un parámetro. */
	PARAMETRO("P"),
	/** Valor proveniente de la sesión. */
	SESION("S");

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
