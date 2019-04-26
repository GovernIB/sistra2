package es.caib.sistramit.core.service.model.script.flujo;

import javax.script.ScriptException;

import es.caib.sistramit.core.service.model.script.PluginScriptRes;

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

	/**
	 * Indica si no se devuelven datos de persona.
	 * 
	 * @param nulo
	 */
	void setNulo(boolean nulo);

}
