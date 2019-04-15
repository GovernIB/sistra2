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

	/**
	 * Obtiene el literal del script.
	 *
	 * @param idLiteralScript
	 * @return
	 */
	public LiteralScript getLiteralScript(Long idLiteralScript);

	/**
	 * Anyade un literal script a un script.
	 *
	 * @param literalScript
	 * @param idScript
	 * @return
	 */
	public LiteralScript addLiteralScript(LiteralScript literalScript, Long idScript);

	/**
	 * Actualiza un literal script.
	 *
	 * @param literalScript
	 */
	public void updateLiteralScript(LiteralScript literalScript);

	/**
	 * Borra un literal script.
	 *
	 * @param literalScript
	 */
	public void removeLiteralScript(Long idLiteralScript);

	/**
	 * Comprueba si un identificador está repetido.
	 *
	 * @param identificador
	 * @param codigo
	 * @return
	 */
	public boolean checkIdentificadorRepetido(String identificador, Long codigo, Long idScript);

	/**
	 * Actualiza script.
	 *
	 * @param pScript
	 *            script
	 */
	void updateScript(Script pScript);

}