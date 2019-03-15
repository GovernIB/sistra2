package es.caib.sistrahelp.core.api.model.types;

import es.caib.sistrahelp.core.api.model.comun.ConstantesRolesAcceso;

/**
 * Tipo para indicar los roles principales.
 *
 * @author Indra
 *
 */
public enum TypeRoleAcceso {

	HELPDESK(ConstantesRolesAcceso.HELPDESK), SUPERVISOR_ENTIDAD(ConstantesRolesAcceso.SUPERVISOR_ENTIDAD);

	/**
	 * Role name;
	 */
	private String roleName;

	/**
	 * Constructor.
	 *
	 * @param pRoleName
	 *            Role name
	 */
	private TypeRoleAcceso(final String pRoleName) {
		roleName = pRoleName;
	}

	/**
	 * Obtiene enum desde string.
	 *
	 * @param text
	 *            string
	 * @return TypeSiNo
	 */
	public static TypeRoleAcceso fromString(final String text) {
		TypeRoleAcceso respuesta = null;
		if (text != null) {
			for (final TypeRoleAcceso b : TypeRoleAcceso.values()) {
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
		return roleName;
	}
}
