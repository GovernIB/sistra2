package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar campo de texto.
 *
 * @author Indra
 *
 */
public enum TypeCampoTexto {

	/** Texto normal. */
	NORMAL("NORMAL"),
	/** Texto número. */
	NUMERO("NUMERO"),
	/** Texto email. */
	EMAIL("EMAIL"),
	/** Texto identificador (dni, nif,..). */
	ID("ID"),
	/** Texto código postal. */
	CP("CP"),
	/** Texto teléfono. */
	TELEFONO("TELEFONO"),
	/** Texto fecha. */
	FECHA("FECHA"),
	/** Texto hora. */
	HORA("HORA"),
	/** Texto expresion regular. */
	EXPRESION("EXPRESION");

	private String valor;

	private TypeCampoTexto(String value) {
		valor = value;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeCampoTexto fromString(final String text) {
		TypeCampoTexto respuesta = null;
		if (text != null) {
			for (final TypeCampoTexto b : TypeCampoTexto.values()) {
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
