package es.caib.sistrages.core.api.service;

import es.caib.sistrages.core.api.model.Script;

/**
 * La interface ScriptService.
 */
public interface ScriptService {

	/**
	 * Obtiene el script.
	 *
	 * @param idScript
	 * @return
	 */
	public Script getScript(Long idScript);

}
