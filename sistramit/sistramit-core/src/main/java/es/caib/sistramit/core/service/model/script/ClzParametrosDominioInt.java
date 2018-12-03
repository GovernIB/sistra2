package es.caib.sistramit.core.service.model.script;

import es.caib.sistramit.core.service.model.integracion.ParametrosDominio;

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

	/**
	 * Obtiene lista parámetros.
	 *
	 * @return el atributo parametros
	 */
	public ParametrosDominio getParametros();
}
