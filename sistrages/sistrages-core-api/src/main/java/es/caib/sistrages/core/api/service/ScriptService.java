package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.LiteralScript;
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

	/**
	 * Obtiene los literales del script.
	 *
	 * @param idScript
	 * @return
	 */
	public List<LiteralScript> getLiterales(Long idScript);

	/**
	 * Obtiene el literal del script.
	 *
	 * @param idLiteralScript
	 * @return
	 */
	public LiteralScript getLiteralScript(Long idLiteralScript);

	/**
	 * Anyade un literal de script.
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
	 * Actualiza un literal script.
	 *
	 * @param literalScript
	 */
	public void removeLiteralScript(Long idLiteralScript);

	/**
	 * Check si identificador repetido.
	 *
	 * @param identificador
	 * @param codigo
	 * @param idScript
	 * @return
	 */
	public boolean checkIdentificadorRepetido(String identificador, Long codigo, Long idScript);
}
