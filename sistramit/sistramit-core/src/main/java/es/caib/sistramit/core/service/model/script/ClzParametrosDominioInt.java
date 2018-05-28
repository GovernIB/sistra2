package es.caib.sistramit.core.service.model.script;

/**
 * Parámetros dominio.
 *
 * @author Indra
 *
 */
public interface ClzParametrosDominioInt extends PluginClass {

	/**
	 * Añade parámetro.
	 *
	 * @param codigo
	 *            codigo parametro.
	 * @param parametro
	 *            valor parametro.
	 */
	void addParametro(final String codigo, final String parametro);

}
