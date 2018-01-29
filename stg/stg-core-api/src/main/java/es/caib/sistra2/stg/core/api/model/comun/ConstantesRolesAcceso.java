/**
 *
 */
package es.caib.sistra2.stg.core.api.model.comun;

/**
 * @author Indra
 *
 */
public final class ConstantesRolesAcceso {

	private ConstantesRolesAcceso() {
		super();
	}

	public static final String SUPER_ADMIN ="STG_SUP";
	public static final String ADMIN_ENT ="STG_ADM";
	public static final String DESAR ="STG_DES";

	public static final String[] listaRoles() {
		String[] rolesPrincipales = {SUPER_ADMIN, ADMIN_ENT, DESAR};
		return rolesPrincipales;
	}
}
