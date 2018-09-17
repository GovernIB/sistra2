package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.types.TypeIdioma;

public interface EntidadDao {

	/**
	 * Obtiene la entidad por codigo.
	 *
	 * @param idEntidad
	 *            Identificador de entidad
	 * @return la entidad
	 */
	Entidad getById(final Long idEntidad);

	/**
	 * Obtiene la entidad por el codigo dir3.
	 *
	 * @param codigoDir3
	 *            Identificador de entidad
	 * @return la entidad
	 */
	Entidad getByCodigo(String codigoDir3);

	/**
	 * Obtiene la entidad.
	 *
	 * @param idArea
	 *            Identificador de area
	 * @return la entidad
	 */
	Entidad getByArea(Long idArea);

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

	/**
	 * Elimina el logo de gestor de tramites de una entidad.
	 *
	 * @param idEntidad
	 *            el identificador de entidad
	 */
	void removeLogoGestor(final Long idEntidad);

	/**
	 * Elimina el logo de asistente de tramites de una entidad.
	 *
	 * @param idEntidad
	 *            el identificador de entidad
	 */
	void removeLogoAsistente(final Long idEntidad);

	/**
	 * Elimina el css del asistente de tramites de una entidad.
	 *
	 * @param idEntidad
	 *            el identificador de entidad
	 */
	void removeCssAsistente(final Long idEntidad);

	/**
	 * Incorpora el logo de gestor de tramites de una entidad.
	 *
	 * @param idEntidad
	 *            el identificador de entidad
	 * @param fichero
	 *            fichero
	 * @return fichero
	 */
	Fichero uploadLogoGestor(final Long idEntidad, final Fichero fichero);

	/**
	 * Incorpora el logo de asistente de tramites de una entidad.
	 *
	 * @param idEntidad
	 *            el identificador de entidad
	 * @param fichero
	 *            fichero
	 * @return fichero
	 */
	Fichero uploadLogoAsistente(final Long idEntidad, final Fichero fichero);

	/**
	 * Incorpora el css del asistente de tramites de una entidad.
	 *
	 * @param idEntidad
	 *            el identificador de entidad
	 * @param fichero
	 *            fichero
	 * @return fichero
	 */
	Fichero uploadCssAsistente(final Long idEntidad, final Fichero fichero);

}