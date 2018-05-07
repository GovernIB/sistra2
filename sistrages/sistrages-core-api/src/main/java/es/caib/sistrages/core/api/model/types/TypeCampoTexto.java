package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar campo de texto.
 *
 * @author Indra
 *
 */
public enum TypeCampoTexto {

	NORMAL, NUMERO, EMAIL, ID, CP, TELEFONO, FECHA, HORA, EXPRESION, IMPORTE;

	private String valor;

	private TypeCampoTexto() {
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
