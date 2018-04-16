package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Tramite;

/**
 * La interface TramiteDao.
 */
public interface TramiteDao {

	/**
	 * Obtiene el tramite.
	 *
	 * @param id
	 *            identificador
	 * @return el valor del tramite
	 */
	Tramite getById(final Long id);

	/**
	 * Obtiene el valor de todos los tramites.
	 *
	 * @param idArea
	 *            Id entidad
	 *
	 * @return el valor de todos los tramites
	 */
	List<Tramite> getAll(final Long idArea);

	/**
	 * Obtiene el valor de todos los tramites.
	 *
	 * @param idArea
	 *            Id entidad
	 * @param pFiltro
	 *            filtro
	 * @return el valor de todos los tramites
	 */
	List<Tramite> getAllByFiltro(final Long idArea, final String pFiltro);

	/**
	 * Añade tramite.
	 *
	 * @param idArea
	 *            idArea
	 * @param pTramite
	 *            Tramite
	 */
	void add(Long idArea, final Tramite pTramite);

	/**
	 * Elimina tramite.
	 *
	 * @param id
	 *            id
	 */
	void remove(final Long id);

	/**
	 * Actualiza tramite.
	 *
	 * @param pTramite
	 *            Tramite
	 */
	void update(final Tramite pTramite);
}