package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Area;

/**
 * La interface AreaDao.
 */
public interface AreaDao {

	/**
	 * Obtiene el area.
	 *
	 * @param pCodigo
	 *            identificador
	 * @return el valor area
	 */
	Area getById(final Long pCodigo);

	/**
	 * Obtiene el valor de todas las areas.
	 *
	 * @param idEntidad
	 *            Id entidad
	 *
	 * @return el valor de todas las areas
	 */
	List<Area> getAll(final Long idEntidad);

	/**
	 * Obtiene el valor de todas las areas.
	 *
	 * @param idEntidad
	 *            Id entidad
	 * @param pFiltro
	 *            filtro
	 * @return el valor de todas las areas
	 */
	List<Area> getAllByFiltro(final Long idEntidad, final String pFiltro);

	/**
	 * Añade area.
	 *
	 * @param idEntidad
	 *            idEntidad
	 * @param pArea
	 *            area
	 */
	void add(Long idEntidad, final Area pArea);

	/**
	 * Elimina area.
	 *
	 * @param pCodigo
	 *            identificador
	 */
	void remove(final Long pCodigo);

	/**
	 * Actualiza area.
	 *
	 * @param pArea
	 *            area
	 */
	void update(final Area pArea);
}