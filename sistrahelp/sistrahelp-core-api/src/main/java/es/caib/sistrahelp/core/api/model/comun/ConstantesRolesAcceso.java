/**
 *
 */
package es.caib.sistrahelp.core.api.model.comun;

/**
 * @author Indra
 *
 */
public final class ConstantesRolesAcceso {

	private ConstantesRolesAcceso() {
		super();
	}

	public static final String HELPDESK = "STH_OPE";

	public static final String[] listaRoles() {
		final String[] rolesPrincipales = { HELPDESK };
		return rolesPrincipales;
	}
}
