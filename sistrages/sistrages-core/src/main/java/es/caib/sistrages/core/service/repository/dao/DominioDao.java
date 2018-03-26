package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

public interface DominioDao {

	Dominio getById(final Long idDominio);

	List<Dominio> getAll(TypeAmbito ambito, Long id);

	List<Dominio> getAllByFiltro(TypeAmbito ambito, Long id, String filtro);

	void add(final Dominio entidad, final Long idEntidad, final Long idArea);

	void remove(final Long idDominio);

	void updateDominio(Dominio dominio);

}