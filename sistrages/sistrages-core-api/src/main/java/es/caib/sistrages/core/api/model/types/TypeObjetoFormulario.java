package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar el tipo de componente de formulario.
 *
 * @author Indra
 *
 */
public enum TypeObjetoFormulario {

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
	LISTA_ELEMENTOS("LE"),
	/**
	 * Linea
	 */
	LINEA("LN"),

	/**
	 * pagina.
	 */
	PAGINA("PG"),

	/**
	 * oculto.
	 */
	CAMPO_OCULTO("OC");

	private String valor;

	private TypeObjetoFormulario(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeObjetoFormulario fromString(final String text) {
		TypeObjetoFormulario respuesta = null;
		if (text != null) {
			for (final TypeObjetoFormulario b : TypeObjetoFormulario.values()) {
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
