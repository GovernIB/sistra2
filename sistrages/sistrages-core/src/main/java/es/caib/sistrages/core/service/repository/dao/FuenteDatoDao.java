package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeClonarAccion;
import es.caib.sistrages.core.service.repository.model.JFuenteDatos;

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
	 * @param idEntidad idEntidad
	 */
	void removeByEntidad(final Long idEntidad);

	void updateFuenteDato(FuenteDatos fuenteDato);

	FuenteFila loadFuenteDatoFila(Long idFuenteDatoFila);

	void addFuenteDatoFila(FuenteFila fila, Long idFuente);

	void updateFuenteDatoFila(FuenteFila fila);

	void removeFuenteFila(Long idFila);

	boolean isCorrectoPK(FuenteFila fuenteFila, Long idFuenteDato);

	void importarCSV(Long idFuenteDatos, CsvDocumento csv);

	void removeByArea(Long idArea);

	ValoresDominio realizarConsultaFuenteDatos(final TypeAmbito ambito, final String idEntidad, final String idArea, final String idDominio, final List<ValorParametroDominio> parametros);

	JFuenteDatos importarFD(FilaImportarDominio filaDominio, TypeAmbito ambito, Long idDominio, final Long idArea)
			throws Exception;

	ValoresDominio realizarConsultaBD(String datasource, String sql, List<ValorParametroDominio> parametros);

	boolean existeFDByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad, Long codigoArea,
			Long codigoFD);

	FuenteDatos getByIdentificador(TypeAmbito ambito, String identificador, Long codigoEntidad, Long codigoArea,
			Long codigoFD);

	FuenteDatos clonar(String dominioID, TypeClonarAccion accion, FuenteDatos fd, final Long idEntidad, final Long areaID);


}