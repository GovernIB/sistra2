package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.AvisoEntidad;
import es.caib.sistrages.core.api.model.types.TypeIdioma;

/**
 * La interface AvisoEntidadDao.
 */
public interface AvisoEntidadDao {

	/**
	 * Obtiene el valor de Aviso Entidad.
	 *
	 * @param id
	 *            el identificador
	 * @return el valor de Aviso Entidad
	 */
	AvisoEntidad getById(Long id);

	/**
	 * Añade Aviso Entidad.
	 *
	 * @param idEntidad
	 *            Id entidad
	 * @param avisoEntidad
	 *            el valor de Aviso Entidad
	 */
	void add(final long idEntidad, AvisoEntidad avisoEntidad);

	/**
	 * Elimina Aviso Entidad.
	 *
	 * @param id
	 *            el identificador
	 */
	void remove(Long id);

	/**
	 * Actualiza Aviso Entidad.
	 *
	 * @param avisoEntidad
	 *            el valor de Aviso Entidad
	 */
	void update(AvisoEntidad avisoEntidad);

	/**
	 * Lista de Aviso Entidad.
	 *
	 * @param idEntidad
	 *            id Entidad
	 * @return la lista de Aviso Entidad
	 */
	List<AvisoEntidad> getAll(Long idEntidad);

	/**
	 * Lista de Aviso Entidad.
	 *
	 * @param idEntidad
	 *            id Entidad
	 * @param filtro
	 *            filtro
	 * @param idioma
	 *            idioma
	 * @return la lista de Aviso Entidad
	 */
	List<AvisoEntidad> getAllByFiltro(Long idEntidad, TypeIdioma idioma, String filtro);

}
