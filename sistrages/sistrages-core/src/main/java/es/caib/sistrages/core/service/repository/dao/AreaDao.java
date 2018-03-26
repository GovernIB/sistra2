package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Area;

public interface AreaDao {

	Area getById(final Long pCodigo);

	List<Area> getAll();

	List<Area> getAllByFiltro(final String pFiltro);

	void add(Long idEntidad, final Area pArea);

	void remove(final Long pCodigo);

	void update(final Area pArea);
}