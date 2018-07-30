/**
 *
 */
package es.caib.sistrages.core.api.model.comun;

/**
 * @author Indra
 *
 */
public final class ConstantesRolesAcceso {

    private ConstantesRolesAcceso() {
        super();
    }

    public static final String SUPER_ADMIN = "STG_SUP";
    public static final String ADMIN_ENT = "STG_ADM";
    public static final String DESAR = "STG_DES";
    public static final String REST = "STG_API_INT";
    public static final String REST_EXT = "STG_API_EXT";

    public static final String[] listaRoles() {
        final String[] rolesPrincipales = {SUPER_ADMIN, ADMIN_ENT, DESAR, REST, REST_EXT};
        return rolesPrincipales;
    }
}
