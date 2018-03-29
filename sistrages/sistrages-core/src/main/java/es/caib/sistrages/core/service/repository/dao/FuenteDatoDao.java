package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

public interface FuenteDatoDao {

	FuenteDatos getById(final Long idFuenteDato);

	List<FuenteDatos> getAll(TypeAmbito ambito, Long id);

	List<FuenteDatos> getAllByFiltro(TypeAmbito ambito, Long id, String filtro);

	void add(final FuenteDatos entidad, final Long id);

	void remove(final Long idFuenteDato);

	void updateFuenteDato(FuenteDatos fuenteDato);

}