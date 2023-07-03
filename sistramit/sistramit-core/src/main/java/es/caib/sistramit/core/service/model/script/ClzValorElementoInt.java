package es.caib.sistramit.core.service.model.script;

import javax.script.ScriptException;

/**
 * Clase de acceso a un valor elemento desde los plugins.
 *
 * @author Indra
 *
 */
public interface ClzValorElementoInt extends PluginClass {

	/**
	 * Establece el valor de un campo simple.
	 *
	 * @param idCampo
	 *                    Id campo
	 * @param valor
	 *                    Valor formulario
	 * @throws ScriptException
	 *                             Se lanzará una excepción si no se puede modificar
	 *                             el formulario en este script.
	 */
	void addValor(final String idCampo, final String valor) throws ScriptException;

	/**
	 * Establece el valor de un campo compuesto.
	 *
	 * @param idCampo
	 *                        Id campo
	 * @param codigo
	 *                        Código
	 * @param descripcion
	 *                        Descripción
	 */
	void addValorCompuesto(final String idCampo, final String codigo, final String descripcion);

	/**
	 * Establece un valor a la lista de valores de un campo de selección múltiple.
	 *
	 * @param idCampo
	 *                    Id campo
	 * @param valores
	 *                    Valores seleccionados.
	 */
	void addValorMultiple(final String idCampo, final ClzValorCampoMultipleInt valores);

}
