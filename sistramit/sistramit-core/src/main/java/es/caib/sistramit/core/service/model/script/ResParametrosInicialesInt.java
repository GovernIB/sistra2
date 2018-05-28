package es.caib.sistramit.core.service.model.script;

/**
 * Datos para establecer los parámetros iniciales trámite.
 *
 * @author Indra
 *
 */
public interface ResParametrosInicialesInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_PARAMETROS_INICIALES";

	/**
	 * Añade un nuevo parámetro.
	 *
	 * @param codigo
	 *            Código parámetro
	 * @param valor
	 *            Valor parámetro
	 */
	void addParametro(final String codigo, final String valor);

}
