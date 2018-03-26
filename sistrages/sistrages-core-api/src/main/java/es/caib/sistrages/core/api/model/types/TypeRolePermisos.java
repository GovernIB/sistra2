package es.caib.sistrages.core.api.model.types;

/**
 * Tipo permisos role.
 *
 * @author Indra
 *
 */
public enum TypeRolePermisos {

	/**
	 * Alta/Baja.
	 */
	ALTA_BAJA("A"),
	/**
	 * Modificación.
	 */
	MODIFICACION("M"),
	/**
	 * Consulta.
	 */
	CONSULTA("C"),
	/**
	 * Helpdesk.
	 */
	HELPDESK("H");

	private String valor;

	TypeRolePermisos(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static TypeRolePermisos fromString(final String text) {
		TypeRolePermisos respuesta = null;
		if (text != null) {
			for (final TypeRolePermisos b : TypeRolePermisos.values()) {
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
