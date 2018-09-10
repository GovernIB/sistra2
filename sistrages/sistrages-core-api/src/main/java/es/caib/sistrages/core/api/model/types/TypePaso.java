package es.caib.sistrages.core.api.model.types;

/**
 * Tipo de paso.
 *
 * @author Indra
 *
 */
public enum TypePaso {
	/**
	 * Paso inicial de Debe saber (Código String: ds).
	 */
	DEBESABER("ds"),
	/**
	 * Paso de rellenar formularios (Código String: rf).
	 */
	RELLENAR("rf"),
	/**
	 * Paso de anexar documentos (Código String: ad).
	 */
	ANEXAR("ad"),
	/**
	 * Paso de captura de datos a través de un formulario (Código String: cd).
	 */
	CAPTURAR("cd"),
	/**
	 * Paso informativo que muestra datos a partir de una plantilla de visualización
	 * (Código String: in).
	 */
	INFORMACION("in"),
	/**
	 * Paso de pago de tasas (Código String: pt).
	 */
	PAGAR("pt"),
	/**
	 * Paso de registro del trámite (Código String: rt).
	 */
	REGISTRAR("rt"),;

	/**
	 * Valor como string.
	 */
	private final String stringValuePaso;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypePaso(final String value) {
		stringValuePaso = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValuePaso;
	}

	/**
	 * Método para From string de la clase TypePaso.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type paso
	 */
	public static TypePaso fromString(final String text) {
		TypePaso respuesta = null;
		if (text != null) {
			for (final TypePaso b : TypePaso.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
