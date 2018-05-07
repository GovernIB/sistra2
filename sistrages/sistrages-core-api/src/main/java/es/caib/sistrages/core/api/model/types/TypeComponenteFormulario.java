package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar el tipo de componente de formulario.
 *
 * @author Indra
 *
 */
public enum TypeComponenteFormulario {

	/**
	 * Campo de texto.
	 */
	CAMPO_TEXTO("CT"),
	/**
	 * Selector.
	 */
	SELECTOR("SE"),
	/**
	 * Seccion.
	 */
	SECCION("SC"),
	/**
	 * Checkbox.
	 */
	CHECKBOX("CK"),
	/**
	 * Etiqueta.
	 */
	ETIQUETA("ET"),
	/**
	 * Captcha.
	 */
	CAPTCHA("CP"),
	/**
	 * Imagen.
	 */
	IMAGEN("IM"),
	/**
	 * Lista elementos.
	 */
	LISTA_ELEMENTOS("LE");

	private String valor;

	private TypeComponenteFormulario(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeComponenteFormulario fromString(final String text) {
		TypeComponenteFormulario respuesta = null;
		if (text != null) {
			for (final TypeComponenteFormulario b : TypeComponenteFormulario.values()) {
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
