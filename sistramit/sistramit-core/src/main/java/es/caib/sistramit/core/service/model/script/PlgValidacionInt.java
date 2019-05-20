package es.caib.sistramit.core.service.model.script;

import javax.script.ScriptException;

/**
 * Plugin para generar un mensaje de aviso. Este plugin estara disponible en los
 * scripts que implementen validación y servirá para mostrar un mensaje de aviso
 * (info/warning/error) que no para la ejecución.
 *
 * @author Indra
 *
 */
public interface PlgValidacionInt extends PluginScriptPlg {

	/**
	 * Id plugin.
	 */
	String ID = "PLUGIN_VALIDACION";

	/**
	 * Método para establecer si se genera aviso.
	 *
	 * @param pExisteAviso
	 *            establecer si se genera aviso.
	 * @param pTipo
	 *            INFO / WARNING
	 * @throws ScriptException
	 *             Excepcion
	 *
	 */
	void setExisteAviso(final boolean pExisteAviso, final String pTipo) throws ScriptException;

	/**
	 * Método para establecer si se genera aviso permitiendo indicar el campo para
	 * el que se establece la validación (para script de validación de página de
	 * formulario).
	 *
	 * @param pExisteAviso
	 *            establecer si se genera aviso.
	 * @param pTipo
	 *            INFO / WARNING
	 * @param pCampo
	 *            Id campo
	 * @throws ScriptException
	 *             Excepcion
	 *
	 */
	void setExisteAviso(final boolean pExisteAviso, final String pTipo, final String pCampo) throws ScriptException;

	/**
	 * Parámetros a substuir en el mensaje que hace referencia el código de aviso.
	 * ({0} {1} ...).
	 *
	 * @param parametro
	 *            Parámetro a substuir.
	 */
	void addParametroMensajeAviso(final String parametro);

	/**
	 * Método para establecer el mensaje de aviso a partir de un código de mensaje.
	 *
	 * @param pCodigoMensajeAviso
	 *            codigo a establecer
	 */
	void setCodigoMensajeAviso(final String pCodigoMensajeAviso);

	/**
	 * Método para establecer directamente el mesaje de aviso.
	 *
	 * @param pTextoMensajeAviso
	 *            mensaje a establecer
	 */
	void setTextoMensajeAviso(final String pTextoMensajeAviso);

}
