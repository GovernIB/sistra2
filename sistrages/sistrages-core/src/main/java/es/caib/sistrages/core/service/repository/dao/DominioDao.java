package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.types.TypeAmbito;

/**
 * La interface DominioDao.
 */
public interface DominioDao {

	/**
	 * Obtiene el dominio.
	 *
	 * @param idDominio
	 *            idDominio
	 * @return el valor de dominio
	 */
	Dominio getById(final Long idDominio);

	/**
	 * Obtiene el dominio.
	 *
	 * @param codigoDominio
	 *            codigoDominio
	 * @return el valor de dominio
	 */
	Dominio getByCodigo(final String codigoDominio);

	/**
	 * Obtiene la lista de todos los dominios
	 *
	 * @param ambito
	 *            ambito
	 * @param id
	 *            identificador del ambito correspondiente (Area o Entidad)
	 * @return lista de todos los dominios
	 */
	List<Dominio> getAll(TypeAmbito ambito, Long id);

	/**
	 * Obtiene la lista de todos los dominios
	 *
	 * @param ambito
	 *            ambito
	 * @param id
	 *            identificador del ambito correspondiente (Area o Entidad)
	 * @param filtro
	 *            filtro
	 * @return lista de todos los dominios
	 */
	List<Dominio> getAllByFiltro(TypeAmbito ambito, Long id, String filtro);

	/**
	 * Obtiene la lista de todos los dominios asociados a una fuente de datos.
	 *
	 * @param idFuenteDatos
	 *            id fuente datos
	 * @return lista dominios
	 */
	List<Dominio> getAllByFuenteDatos(Long idFuenteDatos);

	/**
	 * Añade un dominio.
	 *
	 * @param entidad
	 *            entidad
	 * @param idEntidad
	 *            idEntidad
	 * @param idArea
	 *            idArea
	 */
	void add(final Dominio entidad, final Long idEntidad, final Long idArea);

	/**
	 * Elimina un dominio.
	 *
	 * @param idDominio
	 *            idDominio
	 */
	void remove(final Long idDominio);

	/**
	 * Elimina dominios entidad.
	 *
	 * @param idEntidad
	 *            idEntidad
	 */
	void removeByEntidad(final Long idEntidad);

	/**
	 * Elimina dominios area.
	 *
	 * @param idArea
	 *            idArea
	 */
	void removeByArea(final Long idArea);

	/**
	 * Actualiza dominio.
	 *
	 * @param dominio
	 *            dominio
	 */
	void updateDominio(Dominio dominio);

	/**
	 * Añade una relación entre dominio y versión trámite.
	 *
	 * @param idDominio
	 * @param idTramiteVersion
	 */
	void addTramiteVersion(Long idDominio, Long idTramiteVersion);

	/**
	 * Elimina una relación entre dominio y versión trámite.
	 *
	 * @param idDominio
	 * @param idTramiteVersion
	 */
	void removeTramiteVersion(Long idDominio, Long idTramiteVersion);

	/**
	 * Comprueba si tiene un tramite versión un dominio.
	 *
	 * @param idDominio
	 * @param idTramiteVersion
	 * @return
	 */
	boolean tieneTramiteVersion(Long idDominio, Long idTramiteVersion);
}