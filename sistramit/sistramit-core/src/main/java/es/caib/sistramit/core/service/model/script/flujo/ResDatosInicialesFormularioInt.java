package es.caib.sistramit.core.service.model.script.flujo;

import javax.script.ScriptException;

import es.caib.sistramit.core.service.model.script.ClzValorCampoListaElementosInt;
import es.caib.sistramit.core.service.model.script.ClzValorCampoMultipleInt;
import es.caib.sistramit.core.service.model.script.PluginScriptRes;

/**
 * Permite establecer datos iniciales en un formulario.
 *
 * @author Indra
 *
 */
public interface ResDatosInicialesFormularioInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_VALORESINICIALES";

	/**
	 * Inicializa el valor de un campo simple.
	 *
	 * @param campo
	 *                  Id campo
	 * @param valor
	 *                  Valor formulario
	 * @throws ScriptException
	 *                             Excepcion
	 */
	void setValor(final String campo, final String valor) throws ScriptException;

	/**
	 * Inicializa el valor de un campo compuesto.
	 *
	 * @param campo
	 *                        Campo
	 * @param codigo
	 *                        Código
	 * @param descripcion
	 *                        Descripción
	 * @throws ScriptException
	 *                             Excepcion
	 */
	void setValorCompuesto(final String campo, final String codigo, final String descripcion) throws ScriptException;

	/**
	 * Inicializa el valor de un campo multiple.
	 *
	 * @param campo
	 *                    Id campo.
	 * @param valores
	 *                    Valores seleccionados.
	 * @throws ScriptException
	 *                             Excepcion
	 */
	void setValorMultiple(final String campo, final ClzValorCampoMultipleInt valores) throws ScriptException;

	/**
	 * Crea valor múltiple para alimentarlo y después poder establecerlo mediante
	 * setValorMultiple.
	 *
	 * @return Valor múltiple para ser alimentado.
	 */
	ClzValorCampoMultipleInt crearValorMultiple();

	/**
	 * Crea valor lista elementos para alimentarlo y después poder establecerlo
	 * mediante setValorMultiple.
	 *
	 * @return Valor múltiple para ser alimentado.
	 */
	ClzValorCampoListaElementosInt crearValorListaElementos();

	/**
	 * Inicializa el valor de un campo lista elementos.
	 *
	 * @param campo
	 *                    Id campo.
	 * @param valores
	 *                    Valores seleccionados.
	 * @throws ScriptException
	 *                             Excepcion
	 */
	void setValorListaElementos(String campo, ClzValorCampoListaElementosInt valores) throws ScriptException;

}
