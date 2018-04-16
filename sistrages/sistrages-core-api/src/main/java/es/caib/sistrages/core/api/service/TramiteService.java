package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Tramite;

/**
 * La interface TramiteService.
 */
public interface TramiteService {

	/**
	 * Lista de areas.
	 *
	 * @param idEntidad
	 *            idEntidad
	 * @param filtro
	 *            filtro
	 * @return lista de areas
	 */
	public List<Area> listArea(final Long idEntidad, String filtro);

	/**
	 * Obtiene el area.
	 *
	 * @param id
	 *            identificador
	 * @return area
	 */
	public Area getArea(Long id);

	/**
	 * Añade un area.
	 *
	 * @param idEntidad
	 *            idEntidad
	 * @param area
	 *            area
	 */
	public void addArea(Long idEntidad, Area area);

	/**
	 * Elimina un area.
	 *
	 * @param id
	 *            identificador
	 * @return true, si se realiza con exito
	 */
	public boolean removeArea(Long id);

	/**
	 * Actualiza un area.
	 *
	 * @param area
	 *            area
	 */
	public void updateArea(Area area);

	/**
	 * Lista de tramites.
	 *
	 * @param idArea
	 *            idArea
	 * @param pFiltro
	 *            filtro
	 * @return lista de tramites
	 */
	public List<Tramite> listTramite(final Long idArea, String pFiltro);

	/**
	 * Obtiene el tramite.
	 *
	 * @param id
	 *            identificador
	 * @return tramite
	 */
	public Tramite getTramite(Long id);

	/**
	 * Añade un Tramite.
	 *
	 * @param idArea
	 *            identificador de area
	 * @param pTramite
	 *            tramite
	 */
	public void addTramite(Long idArea, Tramite pTramite);

	/**
	 * Elimina un Tramite.
	 *
	 * @param id
	 *            identificador
	 * @return true, si se realiza con exito
	 */
	public boolean removeTramite(Long id);

	/**
	 * Actualiza un Tramite.
	 *
	 * @param pTramite
	 *            tramite
	 */
	public void updateTramite(Tramite pTramite);

}
