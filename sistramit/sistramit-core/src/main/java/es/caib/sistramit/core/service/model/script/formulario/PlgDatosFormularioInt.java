package es.caib.sistramit.core.service.model.script.formulario;

import javax.script.ScriptException;

import es.caib.sistramit.core.service.model.script.ClzValorCampoCompuestoInt;
import es.caib.sistramit.core.service.model.script.PluginScriptPlg;

/**
 * Plugin de acceso a los datos de los campos del formulario.
 *
 * @author Indra
 *
 */
public interface PlgDatosFormularioInt extends PluginScriptPlg {

	/**
	 * Id plugin.
	 */
	String ID = "PLUGIN_DATOSFORMULARIO";

	/**
	 * Obtiene un valor de un campo simple.
	 *
	 * @param campo
	 *            Id campo
	 * @return Valor del campo. Si no existe el campo devolvemos cadena vacía.
	 * @throws ScriptException
	 *             En caso de que el campo no sea de tipo simple devolvemos una
	 *             excepción.
	 */
	String getValor(final String campo) throws ScriptException;

	/**
	 * Obtiene un valor de un campo compuesto.
	 *
	 * @param campo
	 *            Id campo
	 * @return Valor del campo compuesto por codigo y descripción. Si no existe el
	 *         campo devolvemos valor indexado con codigo y descripcion vacíos.
	 * @throws ScriptException
	 *             En caso de que el campo no sea de tipo simple devolvemos una
	 *             excepción.
	 */
	ClzValorCampoCompuestoInt getValorCompuesto(final String campo) throws ScriptException;

	/**
	 * Obtiene número valores para un campo con valor múltiple.
	 *
	 * @param campo
	 *            Id campo
	 * @return Número de valores.
	 * @throws ScriptException
	 *             Genera una excepción si el campo no es de tipo valor múltiple
	 */
	int getNumeroValoresValorMultiple(final String campo) throws ScriptException;

	/**
	 * Obtiene un valor de un campo múltiple.
	 *
	 * @param campo
	 *            Id campo
	 * @param indice
	 *            Indice valor
	 * @return Valor del campo con el indice indicado compuesto por codigo y
	 *         descripción. Si no existe el campo devolvemos valor indexado con
	 *         codigo y descripcion vacíos.
	 * @throws ScriptException
	 *             En caso de que el campo no sea de tipo simple devolvemos una
	 *             excepción.
	 */
	ClzValorCampoCompuestoInt getValorMultiple(final String campo, final int indice) throws ScriptException;

}
