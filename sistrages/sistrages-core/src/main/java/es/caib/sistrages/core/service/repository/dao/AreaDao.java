package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Area;

public interface AreaDao {
	Area getAreaById(final String pCodigo);

	List<Area> getAllAreaByFiltroDescripcion(final String pFiltro);

	List<Area> getAllArea();

	void addArea(final Area pArea);

	void removeArea(final String pCodigo);

	void updateArea(final Area pArea);
}