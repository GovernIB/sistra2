package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FuenteDatos;
import es.caib.sistrages.core.api.model.FuenteDatosValores;
import es.caib.sistrages.core.api.model.FuenteFila;
import es.caib.sistrages.core.api.model.comun.CsvDocumento;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

/**
 * Dominio service.
 *
 * @author Indra.
 *
 */
public interface DominioService {

	/**
	 * Obtener dominio.
	 *
	 * @param idDominio
	 *            ID del dominio
	 * @return dominio
	 */
	public Dominio loadDominio(Long idDominio);

	/**
	 * Obtener dominio.
	 *
	 * @param codigoDominio
	 *            codigo del dominio
	 * @return dominio
	 */
	public Dominio loadDominio(String codigoDominio);

	/**
	 * Añade dominio.
	 *
	 * @param dominio
	 *            Dominio a crear.
	 * @param idEntidad
	 *            Id de la entidad
	 * @param idArea
	 *            Id del area.
	 */
	public void addDominio(Dominio dominio, Long idEntidad, Long idArea);

	/**
	 * Actualiza dominio.
	 *
	 * @param dominio
	 *            Dominio con los datos requeridos por superadministrador.
	 */
	public void updateDominio(Dominio dominio);

	/**
	 * Elimina dominio.
	 *
	 * @param idDominio
	 *            the id dominio
	 * @return true, si se realiza correctamente
	 */
	public boolean removeDominio(Long idDominio);

	/**
	 * Listar dominios.
	 *
	 * @param ambito
	 *            Ambito GLOBAL(G), ENTIDAD(E) o AREA(A)
	 * @param id
	 *            Id de la entidad o area
	 * @param filtro
	 *            Filro aplicado al código o descripcion.
	 * @return ambito
	 */
	public List<Dominio> listDominio(final TypeAmbito ambito, final Long id, String filtro);

	/**
	 * Obtener fuenteDato.
	 *
	 * @param idFuenteDato
	 *            ID del fuenteDato
	 * @return fuenteDato
	 */
	public FuenteDatos loadFuenteDato(Long idFuenteDato);

	/**
	 * Obtener fuenteDato.
	 *
	 * @param idFuenteDato
	 *            ID del fuenteDato
	 * @return fuenteDato
	 */
	public FuenteDatos loadFuenteDato(String idFuenteDato);

	/**
	 * Obtener fuenteDato.
	 *
	 * @param idFuenteDato
	 *            ID del fuenteDato
	 * @return fuenteDato
	 */
	public FuenteDatosValores loadFuenteDatoValores(Long idFuenteDato);

	/**
	 * Añade fuenteDato.
	 *
	 * @param fuenteDato
	 *            FuenteDato a crear.
	 * @param idEntidad
	 *            Id de la entidad
	 * @param idArea
	 *            Id del area.
	 */
	public void addFuenteDato(FuenteDatos fuenteDato, Long idArea);

	/**
	 * Actualiza fuenteDato.
	 *
	 * @param fuenteDato
	 *            FuenteDato con los datos requeridos por superadministrador.
	 */
	public void updateFuenteDato(FuenteDatos fuenteDato);

	/**
	 * Borrar fuenteDato.
	 *
	 * @param idFuenteDato
	 *            idFuenteDato
	 */
	public boolean removeFuenteDato(Long idFuenteDato);

	/**
	 * Listar fuenteDatos.
	 *
	 * @param ambito
	 *            Ambito GLOBAL(G), ENTIDAD(E) o AREA(A)
	 * @param id
	 *            Id de la entidad o area
	 * @param filtro
	 *            Filro aplicado al código o descripcion.
	 * @return ambito
	 */
	public List<FuenteDatos> listFuenteDato(final TypeAmbito ambito, final Long id, String filtro);

	/**
	 * Obtiene la fuente fila.
	 *
	 * @param idFuenteDatoFila
	 *            Id de la fuente fila
	 * @return fila
	 */
	public FuenteFila loadFuenteDatoFila(Long idFuenteDatoFila);

	/**
	 * Añade una fila.
	 *
	 * @param fila
	 * @param idFuente
	 */
	public void addFuenteDatoFila(FuenteFila fila, Long idFuente);

	/**
	 * Actualiza una fila.
	 *
	 * @param fila
	 *
	 */
	public void updateFuenteDatoFila(FuenteFila fila);

	/**
	 * Borra un fila.
	 *
	 * @param idFila
	 */
	public void removeFuenteFila(Long idFila);

	/**
	 * Estan todas las PK correctas? No hay repetidos vamos.
	 *
	 * @param fuenteFila
	 * @param idFuenteDato
	 * @return
	 */
	public boolean isCorrectoPK(FuenteFila fuenteFila, Long idFuenteDato);

	/**
	 * Importar csv a la fuente de datos.
	 *
	 * @param idFuenteDatos
	 * @param csv
	 */
	public void importarCSV(Long idFuenteDatos, CsvDocumento csv);

	/**
	 * Comprueba si un tramite versión ya está asignado.
	 *
	 * @param idDominio
	 * @param idTramiteVersion
	 * @return
	 */
	public boolean tieneTramiteVersion(final Long idDominio, Long idTramiteVersion);

	/**
	 * Borra la relación entre trámite versión y dominio.
	 *
	 * @param idDominio
	 * @param idTramiteVersion
	 */
	public void removeTramiteVersion(Long idDominio, Long idTramiteVersion);

	/**
	 * Añade la relación entre trámite versión y dominio.
	 *
	 * @param idDominio
	 * @param idTramiteVersion
	 */
	public void addTramiteVersion(Long idDominio, Long idTramiteVersion);
}
