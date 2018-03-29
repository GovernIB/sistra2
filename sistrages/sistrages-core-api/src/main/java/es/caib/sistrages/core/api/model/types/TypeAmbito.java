package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar los ámbitos principales de la aplicación.
 *
 * @author Indra
 *
 */
public enum TypeAmbito {

	GLOBAL("G"), ENTIDAD("E"), AREA("A");

	/**
	 * Ambito nombre;
	 */
	private String ambito;

	/**
	 * Constructor.
	 *
	 * @param pAmbito
	 *            Role name
	 */
	private TypeAmbito(final String pAmbito) {
		ambito = pAmbito;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *            string
	 * @return TypeSiNo
	 */
	public static TypeAmbito fromString(final String text) {
		TypeAmbito respuesta = null;
		if (text != null) {
			for (final TypeAmbito b : TypeAmbito.values()) {
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
		return ambito;
	}

}
