package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.types.TypeIdioma;

public interface EntidadDao {

	/**
	 * Obtiene la entidad.
	 *
	 * @param idEntidad
	 *            Identificador de entidad
	 * @return la entidad
	 */
	Entidad getById(final Long idEntidad);

	/**
	 * Obtiene todas las entidades.
	 *
	 * @return Lista con todas las entidades
	 */
	List<Entidad> getAll();

	/**
	 * Obtiene las entidades.
	 *
	 * @param idioma
	 *            idioma para utilizar el filtro
	 * @param filtro
	 *            filtro
	 * @return Lista con las entidades
	 */
	List<Entidad> getAllByFiltro(TypeIdioma idioma, String filtro);

	/**
	 * Añade una entidad.
	 *
	 * @param entidad
	 *            la entidad
	 */
	void add(final Entidad entidad);

	/**
	 * Elimina una entidad.
	 *
	 * @param idEntidad
	 *            el identificador de entidad
	 */
	void remove(final Long idEntidad);

	/**
	 * Actualiza una entidad.
	 *
	 * @param entidad
	 *            la entidad
	 */
	void updateSuperAdministrador(final Entidad entidad);

	/**
	 * Actualiza una entidad.
	 *
	 * @param entidad
	 *            la entidad
	 */
	void updateAdministradorEntidad(final Entidad entidad);

}