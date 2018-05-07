package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar campo indexado.
 *
 * @author Indra
 *
 */
public enum TypeCampoIndexado {

	SELECTOR, DESPLEGABLE, MULTIPLE, UNICA;

	private String valor;

	private TypeCampoIndexado() {
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeCampoIndexado fromString(final String text) {
		TypeCampoIndexado respuesta = null;
		if (text != null) {
			for (final TypeCampoIndexado b : TypeCampoIndexado.values()) {
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
