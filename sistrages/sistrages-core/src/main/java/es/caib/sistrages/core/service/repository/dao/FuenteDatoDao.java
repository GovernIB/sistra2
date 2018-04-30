package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

public interface FuenteDatoDao {

	FuenteDatos getById(final Long idFuenteDato);

	FuenteDatosValores getValoresById(final Long idFuenteDato);

	List<FuenteDatos> getAll(TypeAmbito ambito, Long id);

	List<FuenteDatos> getAllByFiltro(TypeAmbito ambito, Long id, String filtro);

	void add(final FuenteDatos entidad, final Long id);

	void remove(final Long idFuenteDato);

	/**
	 * Elimina fuente datos de una entidad.
	 *
	 * @param idEntidad
	 *            idEntidad
	 */
	void removeByEntidad(final Long idEntidad);

	void updateFuenteDato(FuenteDatos fuenteDato);

	FuenteFila loadFuenteDatoFila(Long idFuenteDatoFila);

	void addFuenteDatoFila(FuenteFila fila, Long idFuente);

	void updateFuenteDatoFila(FuenteFila fila);

	void removeFuenteFila(Long idFila);

	boolean isCorrectoPK(FuenteFila fuenteFila, Long idFuenteDato);

	void importarCSV(Long idFuenteDatos, CsvDocumento csv);

	FuenteDatos getByIdentificador(String idFuenteDato);

	void removeByArea(Long idArea);

}