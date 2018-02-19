package es.caib.sistrages.core.api.model.types;

import es.caib.sistrages.core.api.model.comun.ConstantesAmbitos;

/**
 * Tipo para indicar los ámbitos principales de la aplicación.
 *
 * @author Indra
 *
 */
public enum TypeAmbito {

	GLOBAL(ConstantesAmbitos.GLOBAL), ENTIDAD(ConstantesAmbitos.ENTIDAD), AREA(ConstantesAmbitos.AREA);

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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return ambito;
	}
}
