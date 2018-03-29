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
	 * Actualiza dominio.
	 *
	 * @param dominio
	 *            dominio
	 */
	void updateDominio(Dominio dominio);

}