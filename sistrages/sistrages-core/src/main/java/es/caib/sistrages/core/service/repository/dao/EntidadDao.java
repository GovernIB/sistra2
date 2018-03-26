package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.types.TypeIdioma;

public interface EntidadDao {

	Entidad getById(final Long idEntidad);

	List<Entidad> getAll();

	List<Entidad> getAllByFiltro(TypeIdioma idioma, String filtro);

	void add(final Entidad entidad);

	void remove(final Long idEntidad);

	void updateSuperAdministrador(final Entidad entidad);

}