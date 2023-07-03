package es.caib.sistramit.core.service.model.script.flujo;

import javax.script.ScriptException;

import es.caib.sistramit.core.service.model.script.ClzValorCampoCompuestoInt;
import es.caib.sistramit.core.service.model.script.PluginScriptPlg;

/**
 * Plugin de acceso a datos de formularios.
 *
 * @author Indra
 *
 */
public interface PlgFormulariosInt extends PluginScriptPlg {

	/**
	 * Id plugin.
	 */
	String ID = "PLUGIN_FORMULARIOS";

	/**
	 * Devuelve si el formulario esta rellenado.
	 *
	 * @param idFormulario
	 *                         Id formulario.
	 * @return boolean indicando si esta rellenado.
	 */
	boolean isFormularioRellenado(final String idFormulario);

	/**
	 * Obtiene el valor de una acción personalizada para un formulario (en caso de
	 * tenerla).
	 *
	 * @param idFormulario
	 *                         Id formulario
	 * @return Acción personalizada. Si no existe devolvemos cadena vacía.
	 * @throws ScriptException
	 *                             En caso de que el formulario no exista o no sea
	 *                             accesible.
	 */
	String getAccionPersonalizada(final String idFormulario) throws ScriptException;

	/**
	 * Obtiene el xml de valores del formulario.
	 *
	 * @param idFormulario
	 *                         Id formulario
	 * @return Xml de valores.
	 * @throws ScriptException
	 *                             En caso de que el formulario no exista o no sea
	 *                             accesible.
	 */
	String getXml(final String idFormulario) throws ScriptException;

	/**
	 * Obtiene un valor de un campo simple.
	 *
	 * @param idFormulario
	 *                         Id formulario
	 * @param campo
	 *                         Id campo
	 * @return Valor del campo. Si no existe el campo devolvemos cadena vacía.
	 * @throws ScriptException
	 *                             En caso de que el campo no sea de tipo simple
	 *                             devolvemos una excepción.
	 */
	String getValor(final String idFormulario, final String campo) throws ScriptException;

	/**
	 * Obtiene un valor de un campo compuesto.
	 *
	 * @param idFormulario
	 *                         Id formulario
	 * @param campo
	 *                         Id campo
	 * @return Valor del campo compuesto por codigo y descripción. Si no existe el
	 *         campo devolvemos valor indexado con codigo y descripcion vacíos.
	 * @throws ScriptException
	 *                             En caso de que el campo no sea de tipo simple
	 *                             devolvemos una excepción.
	 */
	ClzValorCampoCompuestoInt getValorCompuesto(final String idFormulario, final String campo) throws ScriptException;

	/**
	 * Obtiene número valores de un valor múltiple.
	 *
	 * @param idFormulario
	 *                         Id formulario
	 * @param campo
	 *                         Id campo
	 * @return Número de valores.
	 * @throws ScriptException
	 *                             Genera una excepción si el campo no es de tipo
	 *                             valor múltiple
	 */
	int getNumeroValoresValorMultiple(final String idFormulario, final String campo) throws ScriptException;

	/**
	 * Obtiene un valor de un campo múltiple.
	 *
	 * @param idFormulario
	 *                         Id formulario
	 * @param campo
	 *                         Id campo
	 * @param indice
	 *                         Indice valor
	 * @return Valor del campo con el indice indicado compuesto por codigo y
	 *         descripción. Si no existe el campo devolvemos valor indexado con
	 *         codigo y descripcion vacíos.
	 * @throws ScriptException
	 *                             En caso de que el campo no sea de tipo simple
	 *                             devolvemos una excepción.
	 */
	ClzValorCampoCompuestoInt getValorMultiple(final String idFormulario, final String campo, final int indice)
			throws ScriptException;

	/**
	 * Obtiene número elementos de un campo de lista elementos.
	 * 
	 * @param campoListaElementos
	 *                                campo lista elementos
	 * @return número elementos
	 * @throws ScriptException
	 */
	int getNumeroElementos(final String idFormulario, String campoListaElementos) throws ScriptException;

	/**
	 * Obtiene valor simple de una lista de elementos.
	 * 
	 * @param idFormulario
	 *                                Id formulario
	 * @param campoListaElementos
	 *                                id campo lista elementos
	 * @param numElemento
	 *                                número de elemento (empieza en 0)
	 * @param campo
	 *                                id campo del elemento
	 * @return valor
	 * @throws ScriptException
	 */
	String getValorElemento(final String idFormulario, final String campoListaElementos, final int numElemento,
			final String campo) throws ScriptException;

	/**
	 * Obtiene valor compuesto de una lista de elementos.
	 * 
	 * @param idFormulario
	 *                                Id formulario
	 * @param campoListaElementos
	 *                                id campo lista elementos
	 * @param numElemento
	 *                                número de elemento (empieza en 0)
	 * @param campo
	 *                                id campo del elemento
	 * @return Valor del campo compuesto por codigo y descripción. Si no existe el
	 *         campo devolvemos valor indexado con codigo y descripcion vacíos.
	 * @throws ScriptException
	 */
	ClzValorCampoCompuestoInt getValorElementoCompuesto(final String idFormulario, final String campoListaElementos,
			final int numElemento, final String campo) throws ScriptException;

	/**
	 * Obtiene valor simple de una lista de elementos.
	 * 
	 * @param idFormulario
	 *                                Id formulario
	 * @param campoListaElementos
	 *                                id campo lista elementos
	 * @param numElemento
	 *                                número de elemento (empieza en 0)
	 * @param campo
	 *                                id campo del elemento
	 * @param indice
	 *                                Indice valor
	 * @return Valor del campo con el indice indicado compuesto por codigo y
	 *         descripción. Si no existe el campo devolvemos valor indexado con
	 *         codigo y descripcion vacíos.
	 * @throws ScriptException
	 */
	ClzValorCampoCompuestoInt getValorElementoMultiple(final String idFormulario, final String campoListaElementos,
			final int numElemento, final String campo, final int indice) throws ScriptException;

	/**
	 * Obtiene número valores de un valor múltiple.
	 *
	 * @param idFormulario
	 *                                Id formulario
	 * @param numElemento
	 *                                número de elemento (empieza en 0)
	 * @param campoListaElementos
	 *                                id campo lista elementos
	 * @param campo
	 *                                Id campo
	 * @return Número de valores.
	 * @throws ScriptException
	 *                             Genera una excepción si el campo no es de tipo
	 *                             valor múltiple
	 */
	int getNumeroValoresValorElementoMultiple(final String idFormulario, final String campoListaElementos,
			final int numElemento, final String campo) throws ScriptException;

}
