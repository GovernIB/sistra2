package es.caib.sistramit.core.service.model.script.flujo;

import es.caib.sistramit.core.service.model.script.PluginScriptRes;

/**
 * @author Indra
 *
 */
public interface ResParametrosFormularioInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_PARAMETROS_FORMULARIO";

	/**
	 * Añade un nuevo parámetro al formulario.
	 *
	 * @param codigo
	 *            Código parámetro
	 * @param valor
	 *            Valor parámetro
	 */
	void addParametro(final String codigo, final String valor);

}
