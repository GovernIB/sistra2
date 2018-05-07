package es.caib.sistrages.core.api.model.types;

/**
 * Tipo: role / permiso.
 *
 * @author Indra
 *
 */
public enum TypeRoleUser {
	/**
	 * Rol.
	 */
	ROL("R"),
	/**
	 * Usuario.
	 */
	USUARIO("U");

	private String valor;

	private TypeRoleUser(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param text
	 *            tipo
	 * @return TypeRoleUser
	 */
	public static TypeRoleUser fromString(final String text) {
		TypeRoleUser respuesta = null;
		if (text != null) {
			for (final TypeRoleUser b : TypeRoleUser.values()) {
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
