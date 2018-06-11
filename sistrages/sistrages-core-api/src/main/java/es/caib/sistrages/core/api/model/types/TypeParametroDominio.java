package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar parametro dominio.
 *
 * @author Indra
 *
 */
public enum TypeParametroDominio {

	CONSTANTE("C"), CAMPO("M"), PARAMETRO("P");

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
