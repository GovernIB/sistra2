package es.caib.sistramit.core.service.model.script.flujo;

import javax.script.ScriptException;

import es.caib.sistramit.core.service.model.script.PluginScriptRes;

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
	 * Añade firmante (deberán firmar todos los firmantes indicados).
	 *
	 * @param nif
	 *                   Nif.
	 * @param nombre
	 *                   Nombre completo.
	 * @throws ScriptException
	 *                             Excepcion
	 */
	void addFirmante(final String nif, final String nombre) throws ScriptException;

	/**
	 * Añade firmante opcional (de los firmantes opcionales, al menos deberá firmar
	 * uno de ellos).
	 *
	 * @param nif
	 *                   Nif.
	 * @param nombre
	 *                   Nombre completo.
	 * @throws ScriptException
	 *                             Excepcion
	 */
	void addFirmanteOpcional(final String nif, final String nombre) throws ScriptException;

}
