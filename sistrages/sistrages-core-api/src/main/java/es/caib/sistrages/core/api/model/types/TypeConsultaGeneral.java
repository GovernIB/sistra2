package es.caib.sistrages.core.api.model.types;

/**
 * Tipo segun el view de consultaGeneral.
 *
 * @author Indra
 *
 */
public enum TypeConsultaGeneral {

	DOMINIO("D"), CONFIG_AUTENTICACION("C"), GFE("G");

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
	private TypeConsultaGeneral(final String pAmbito) {
		ambito = pAmbito;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *            string
	 * @return TypeSiNo
	 */
	public static TypeConsultaGeneral fromString(final String text) {
		TypeConsultaGeneral respuesta = null;
		if (text != null) {
			for (final TypeConsultaGeneral b : TypeConsultaGeneral.values()) {
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
