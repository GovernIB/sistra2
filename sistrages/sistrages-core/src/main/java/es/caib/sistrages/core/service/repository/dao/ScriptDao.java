package es.caib.sistrages.core.service.repository.dao;

import es.caib.sistrages.core.api.model.Script;

/**
 * La interface ScriptDao.
 */
public interface ScriptDao {

	/**
	 * Obtiene el script
	 * 
	 * @param idScript
	 * @return
	 */
	public Script getScript(final Long idScript);
}