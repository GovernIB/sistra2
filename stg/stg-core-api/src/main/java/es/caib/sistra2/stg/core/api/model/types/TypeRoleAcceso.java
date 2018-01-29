package es.caib.sistra2.stg.core.api.model.types;

import es.caib.sistra2.stg.core.api.model.comun.ConstantesRolesAcceso;
/**
 * Tipo para indicar los roles principales.
 *
 * @author Indra
 *
 */
public enum TypeRoleAcceso {

	SUPER_ADMIN (ConstantesRolesAcceso.SUPER_ADMIN),
	ADMIN_ENT (ConstantesRolesAcceso.ADMIN_ENT),
	DESAR (ConstantesRolesAcceso.DESAR);

    /**
     * Role name;
     */
    private String roleName;

    /**
     * Constructor.
     * @param pRoleName Role name
     */
    private TypeRoleAcceso(String pRoleName) {
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

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return roleName;
    }
}
