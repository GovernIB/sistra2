package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.Area;

/**
 * La interface AreaService.
 */
public interface AreaService {

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

}
