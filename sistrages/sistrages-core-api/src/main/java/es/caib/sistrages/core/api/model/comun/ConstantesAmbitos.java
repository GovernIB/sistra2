/**
 *
 */
package es.caib.sistrages.core.api.model.comun;

/**
 * @author Indra
 *
 */
public final class ConstantesAmbitos {

	private ConstantesAmbitos() {
		super();
	}

	public static final String GLOBAL = "G";
	public static final String ENTIDAD = "E";
	public static final String AREA = "A";

	public static final String[] listaRoles() {
		final String[] ambitosPrincipales = { GLOBAL, ENTIDAD, AREA };
		return ambitosPrincipales;
	}
}
