package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.LiteralScript;
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

    /**
     * Obtiene literales script.
     * 
     * @param idScript
     *            id script
     * @return lista literales
     */
    public List<LiteralScript> getLiterales(final Long idScript);
}