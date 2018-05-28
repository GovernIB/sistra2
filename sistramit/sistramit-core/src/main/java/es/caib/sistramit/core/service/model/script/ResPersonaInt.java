package es.caib.sistramit.core.service.model.script;

import javax.script.ScriptException;

/**
 * Establece datos persona.
 *
 * @author Indra
 *
 */
public interface ResPersonaInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_PERSONA";

	/**
	 * Establece datos persona.
	 *
	 * @param pNif
	 *            Nif
	 * @param pNombre
	 *            Nombre
	 * @param pApellido1
	 *            Apellido 1
	 * @param pApellido2
	 *            Apellido 2
	 * @throws ScriptException
	 *             Excepcion
	 */
	void setDatosPersona(final String pNif, final String pNombre, final String pApellido1, final String pApellido2)
			throws ScriptException;

}
