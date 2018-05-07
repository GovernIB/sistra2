package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar propiedades globales disponibles.
 *
 * @author Indra
 *
 */
public enum TypePropiedadGlobal {

	/** Path almacenamiento ficheros. */
	PATH_ALMACENAMIENTO_FICHEROS("ficherosExternos.path"),
	/** Propiedad test. */
	TEST("test.propiedad1");

	private String valor;

	private TypePropiedadGlobal(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param text
	 *            tipo
	 * @return tipo
	 */
	public static TypePropiedadGlobal fromString(final String text) {
		TypePropiedadGlobal respuesta = null;
		if (text != null) {
			for (final TypePropiedadGlobal b : TypePropiedadGlobal.values()) {
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
