package es.caib.sistramit.core.service.model.script.formulario;

import javax.script.ScriptException;

import es.caib.sistramit.core.service.model.script.ClzValorCampoMultipleInt;
import es.caib.sistramit.core.service.model.script.PluginScriptRes;

/**
 * Establecimiento de valor en script autorrellenable.
 *
 * @author Indra
 *
 */
public interface ResValorCampoInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_VALOR";

	/**
	 * Resetea valor del campo.
	 */
	void resetValor();

	/**
	 * Establece el valor de un campo simple.
	 *
	 * @param valor
	 *            Valor formulario
	 * @throws ScriptException
	 *             Se lanzará una excepción si no se puede modificar el formulario
	 *             en este script.
	 */
	void setValor(final String valor) throws ScriptException;

	/**
	 * Establece el valor de un campo compuesto.
	 *
	 * @param codigo
	 *            Código
	 * @param descripcion
	 *            Descripción
	 */
	void setValorCompuesto(final String codigo, final String descripcion);

	/**
	 * Establece un valor a la lista de valores de un campo de selección múltiple.
	 *
	 * @param valores
	 *            Valores seleccionados.
	 */
	void setValorMultiple(final ClzValorCampoMultipleInt valores);

	/**
	 * Crea valor múltiple para alimentarlo y después poder establecerlo mediante
	 * setValorMultiple.
	 *
	 * @return Valor múltiple para ser alimentado.
	 */
	ClzValorCampoMultipleInt crearValorMultiple();

}
