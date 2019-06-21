package es.caib.sistramit.core.service.model.script.flujo;

import es.caib.sistramit.core.service.model.script.PluginScriptRes;

/**
 * Permite establecer propiedades aviso.
 *
 * @author Indra
 *
 */
public interface ResAvisoInt extends PluginScriptRes {

	/**
	 * Id plugin.
	 */
	String ID = "DATOS_AVISO";

	/**
	 * Establece email aviso.
	 * 
	 * @param email
	 *                  email
	 */
	void setEmail(String email);

}
