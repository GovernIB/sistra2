package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistra2.commons.plugins.dominio.api.ValoresDominio;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.comun.FilaImportarDominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

// TODO: Auto-generated Javadoc
/**
 * La interface DominioDao.
 */
public interface DominioDao {

	/**
	 * Obtiene el valor de byId.
	 *
	 * @param idDominio
	 *            the id dominio
	 * @return el valor de byId
	 */
	Dominio getByCodigo(final Long idDominio);

	/**
	 * Obtiene el valor de byCodigo.
	 *
	 * @param codigoDominio
	 *            the codigo dominio
	 * @return el valor de byCodigo
	 */
	Dominio getByIdentificador(final String codigoDominio);

	/**
	 * Obtiene el valor de all.
	 *
	 * @param ambito
	 *            the ambito
	 * @param id
	 *            the id
	 * @return el valor de all
	 */
	List<Dominio> getAll(TypeAmbito ambito, Long id);

	/**
	 * Obtiene el valor de allByFiltro.
	 *
	 * @param ambito
	 *            the ambito
	 * @param id
	 *            the id
	 * @param filtro
	 *            the filtro
	 * @return el valor de allByFiltro
	 */
	List<Dominio> getAllByFiltro(TypeAmbito ambito, Long id, String filtro);

	/**
	 * Obtiene el valor de allByFuenteDatos.
	 *
	 * @param idFuenteDatos
	 *            the id fuente datos
	 * @return el valor de allByFuenteDatos
	 */
	List<Dominio> getAllByFuenteDatos(Long idFuenteDatos);

	/**
	 * Añade.
	 *
	 * @param entidad
	 *            the entidad
	 * @param idEntidad
	 *            the id entidad
	 * @param idArea
	 *            the id area
	 */
	void add(final Dominio entidad, final Long idEntidad, final Long idArea);

	/**
	 * Elimina.
	 *
	 * @param idDominio
	 *            the id dominio
	 */
	void remove(final Long idDominio);

	/**
	 * Elimina by entidad.
	 *
	 * @param idEntidad
	 *            the id entidad
	 */
	void removeByEntidad(final Long idEntidad);

	/**
	 * Elimina by area.
	 *
	 * @param idArea
	 *            the id area
	 */
	void removeByArea(final Long idArea);

	/**
	 * Actualiza dominio.
	 *
	 * @param dominio
	 *            the dominio
	 */
	void updateDominio(Dominio dominio);

	/**
	 * Añade tramite version.
	 *
	 * @param idDominio
	 *            the id dominio
	 * @param idTramiteVersion
	 *            the id tramite version
	 */
	void addTramiteVersion(Long idDominio, Long idTramiteVersion);

	/**
	 * Elimina tramite version.
	 *
	 * @param idDominio
	 *            the id dominio
	 * @param idTramiteVersion
	 *            the id tramite version
	 */
	void removeTramiteVersion(Long idDominio, Long idTramiteVersion);

	/**
	 * Tiene tramite version.
	 *
	 * @param idDominio
	 *            the id dominio
	 * @param idTramiteVersion
	 *            the id tramite version
	 * @return true, if successful
	 */
	boolean tieneTramiteVersion(Long idDominio, Long idTramiteVersion);

	/**
	 * Obtiene lista de dominios.
	 *
	 * @param idTramite
	 *            idtramite
	 * @param filtro
	 *            filtro
	 * @return lista de dominios
	 */
	List<Dominio> getAllByFiltro(Long idTramite, String filtro);

	/**
	 * Importa un DAO según las condiciones.
	 *
	 * @param filaDominio
	 * @return El codigo del dominio, tanto cuando se mantiene como cuando se
	 *         reemplaza o crea.
	 * @throws Exception
	 */
	Long importar(FilaImportarDominio filaDominio, Long idDominio) throws Exception;

	/**
	 * Clona un dominio.
	 *
	 * @param dominioID
	 *            Codigo del dominio
	 * @param nuevoIdentificador
	 *            Nuevo identificador del dominio
	 * @param areaID
	 *            Codigo del area si es necesaria.
	 * @param fdID
	 *            Codigo de la FD si es necesaria.
	 * @param idEntidad
	 *            Codigo de la entidad si de ambito entidad.
	 */
	void clonar(String dominioID, final String nuevoIdentificador, Long areaID, Long fdID, final Long idEntidad);

	/**
	 * Obtiene el valores dominio.
	 * 
	 * @param identificador
	 * @return
	 */
	ValoresDominio realizarConsultaListaFija(String identificador);
}