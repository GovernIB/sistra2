package es.caib.sistrages.core.api.model.types;

import es.caib.sistrages.core.api.model.comun.ConstantesTipoFlujo;;

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
	NORMAL(ConstantesTipoFlujo.NORMAL),
	/**
	 * PERSONALIZADO.
	 */
	PERSONALIZADO(ConstantesTipoFlujo.PERSONALIZADO);

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

	/**
	 * To string.
	 *
	 * @param pTipo
	 *            tipo
	 * @return the string
	 */
	public static String toString(final TypeFlujo pTipo) {
		String respuesta = null;
		if (pTipo != null) {
			respuesta = pTipo.tipo;
		}
		return respuesta;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return tipo;
	}

}
