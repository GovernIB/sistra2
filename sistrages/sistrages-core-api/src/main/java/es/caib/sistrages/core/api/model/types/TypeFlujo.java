package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar los tipo de flujo de version de tramite.
 *
 * @author Indra
 *
 */
public enum TypeFlujo {

	/**
	 * NORMAL.
	 */
	NORMAL("N"),
	/**
	 * PERSONALIZADO.
	 */
	PERSONALIZADO("P");

	/**
	 * tipo.
	 */
	private String tipo;

	/**
	 * Crea una nueva instancia de TypeFlujo.
	 *
	 * @param pTipo
	 *            tipo
	 */
	private TypeFlujo(final String pTipo) {
		tipo = pTipo;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *            string
	 * @return TypeSiNo
	 */
	public static TypeFlujo fromString(final String text) {
		TypeFlujo respuesta = null;
		if (text != null) {
			for (final TypeFlujo b : TypeFlujo.values()) {
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
