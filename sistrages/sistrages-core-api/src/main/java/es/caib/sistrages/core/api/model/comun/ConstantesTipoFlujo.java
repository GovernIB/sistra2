/**
 *
 */
package es.caib.sistrages.core.api.model.comun;

/**
 * @author Indra
 *
 */
public final class ConstantesTipoFlujo {

	private ConstantesTipoFlujo() {
		super();
	}

	public static final String NORMAL = "N";
	public static final String PERSONALIZADO = "P";

	public static final String[] listaTipoFlujo() {
		final String[] tipoFlujo = { NORMAL, PERSONALIZADO };
		return tipoFlujo;
	}
}
