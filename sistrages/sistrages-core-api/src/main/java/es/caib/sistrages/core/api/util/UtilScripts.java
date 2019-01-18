package es.caib.sistrages.core.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.caib.sistrages.core.api.model.types.TypePluginScript;
import es.caib.sistrages.core.api.model.types.TypeScript;

/**
 * Util de scripts.
 *
 * @author Indra
 *
 */
public class UtilScripts {

	/**
	 * Constructor vacio
	 */
	private UtilScripts() {
		// Vacio
	}

	/**
	 * Devuelve los tipos de plugin que tiene el tipo de script
	 *
	 * @param tipoScript
	 * @return
	 */
	public static final List<TypePluginScript> getPluginsScript(final TypeScript tipoScript) {
		return new ArrayList<>(Arrays.asList(TypePluginScript.values()));
	}

	/**
	 * Devuelve la lista de formularios según el paso actual, la lista de pasos y el
	 * tipo de script.
	 *
	 * @return
	 */
	public static List<Long> getFormularios() {
		final List<Long> formularios = new ArrayList<>();
		formularios.add(61l);
		formularios.add(121l);
		return formularios;
	}
}
