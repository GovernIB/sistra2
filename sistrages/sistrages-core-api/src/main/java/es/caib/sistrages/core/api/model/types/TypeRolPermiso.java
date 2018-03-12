package es.caib.sistrages.core.api.model.types;

/**
 * Tipo de rol / permiso.
 *
 * @author Indra
 *
 */
public enum TypeRolPermiso {
	/**
	 * Rol.
	 */
	ROL("R"),
	/**
	 * Usuario.
	 */
	USUARIO("U");

	private String valor;

	TypeRolPermiso(final String iValor) {
		this.valor = iValor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public static String fromEnum(final TypeRolPermiso tipo) {
		return tipo.valor;
	}

	/**
	 * Devuelve el valor de un enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public String fromEnum() {
		return this.valor;
	}

	/**
	 * Convierte un string en enumerado.
	 *
	 * @param tipo
	 * @return
	 */
	public TypeRolPermiso toEnum(final String text) {
		TypeRolPermiso respuesta = null;
		if (text != null) {
			for (final TypeRolPermiso b : TypeRolPermiso.values()) {
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
		return valor;
	}
}
