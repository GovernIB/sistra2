package es.caib.sistramit.core.service.model.script.formulario;

import es.caib.sistramit.core.service.model.script.PluginScriptRes;

/**
 * Establecimiento de valores posibles de un selector en script valores
 * posibles.
 *
 * @author Indra
 *
 */
public interface ResValoresPosiblesInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_VALORESPOSIBLES";

	/**
	 * Añade un nuevo valor posible.
	 *
	 * @param codigo
	 *            Codigo valor posible
	 * @param descripcion
	 *            Descripcion valor posible
	 */
	void addValorPosible(final String codigo, final String descripcion);

}
