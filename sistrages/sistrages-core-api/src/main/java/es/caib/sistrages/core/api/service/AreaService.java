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

	public Area load(String id);

	public void add(Area area);

	public void remove(String id);

	public void update(Area area);

}
