package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar que tipo de fichero/formateador es.
 *
 * @author Indra
 *
 */
public enum TypeFichero {

	FIN_REGISTRO("FR");

	/**
	 * Ambito nombre;
	 */
	private String tipo;

	/**
	 * Constructor.
	 *
	 * @param pTipo
	 *            Role name
	 */
	private TypeFichero(final String pTipo) {
		tipo = pTipo;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *            string
	 * @return TypeSiNo
	 */
	public static TypeFichero fromString(final String text) {
		TypeFichero respuesta = null;
		if (text != null) {
			for (final TypeFichero b : TypeFichero.values()) {
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
		return tipo;
	}

}
