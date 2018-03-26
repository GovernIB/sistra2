package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.Area;

/**
 * Area Service.
 *
 * @author Indra.
 *
 */
public interface AreaService {

	public List<Area> list(String filtro);

	public Area load(Long id);

	public void add(Long idEntidad, Area area);

	public void remove(Long id);

	public void update(Area area);

}
