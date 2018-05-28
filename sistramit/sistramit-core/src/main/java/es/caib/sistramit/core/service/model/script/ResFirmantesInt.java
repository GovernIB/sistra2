package es.caib.sistramit.core.service.model.script;

import javax.script.ScriptException;

/**
 * Datos para establecer los firmantes de un documento.
 *
 * @author Indra
 *
 */
public interface ResFirmantesInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_FIRMANTES";

	/**
	 * Añade firmante.
	 *
	 * @param nif
	 *            Nif.
	 * @param nombre
	 *            Nombre completo.
	 * @throws ScriptException
	 *             Excepcion
	 */
	void addFirmante(final String nif, final String nombre) throws ScriptException;

}
