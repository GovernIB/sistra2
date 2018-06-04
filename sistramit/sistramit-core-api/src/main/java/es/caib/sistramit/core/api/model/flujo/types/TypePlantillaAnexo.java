package es.caib.sistramit.core.api.model.flujo.types;

/**
 *
 * Tipo de plantilla: externa (url) o interna (fichero en repositorio).
 *
 * @author Indra
 *
 */
public enum TypePlantillaAnexo {
	/**
	 * Interna: fichero en repositorio de la plataforma (Código String: i).
	 */
	INTERNA("i"),
	/**
	 * Externo: url externa (Código String: e).
	 */
	EXTERNA("e");

	/**
	 * Valor como string.
	 */
	private final String stringValuePlantillaAnexo;

	/**
	 * Constructor.
	 *
	 * @param value
	 *            Valor como string.
	 */
	private TypePlantillaAnexo(final String value) {
		stringValuePlantillaAnexo = value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return stringValuePlantillaAnexo;
	}

	/**
	 * Método para From string de la clase TypePlantillaAnexo.
	 *
	 * @param text
	 *            Parámetro text
	 * @return el type plantilla anexo
	 */
	public static TypePlantillaAnexo fromString(final String text) {
		TypePlantillaAnexo respuesta = null;
		if (text != null) {
			for (final TypePlantillaAnexo b : TypePlantillaAnexo.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}

		}
		return respuesta;
	}

}
