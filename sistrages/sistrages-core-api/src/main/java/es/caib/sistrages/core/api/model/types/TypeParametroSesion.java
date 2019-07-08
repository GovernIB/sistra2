package es.caib.sistrages.core.api.model.types;

/**
 * Tipo para indicar parametro de sesion para el parametro de dominio.
 *
 * @author Indra
 *
 */
public enum TypeParametroSesion {

	/** Idioma. */
	IDIOMA("idioma");

	private String valor;

	private TypeParametroSesion(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeParametroSesion fromString(final String text) {
		TypeParametroSesion respuesta = null;
		if (text != null) {
			for (final TypeParametroSesion b : TypeParametroSesion.values()) {
				if (text.equalsIgnoreCase(b.toString())) {
					respuesta = b;
					break;
				}
			}
		}
		return respuesta;
	}

	/** Obtiene la ruta del label. **/
	public String getLabel() {
		return "typeSesion." + valor;
	}

	@Override
	public String toString() {
		return valor;
	}

}
