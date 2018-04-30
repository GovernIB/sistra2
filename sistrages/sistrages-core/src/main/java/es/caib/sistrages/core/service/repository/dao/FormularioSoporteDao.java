package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.FormularioSoporte;

/**
 * La interface FormularioSoporteDao.
 */
public interface FormularioSoporteDao {

	/**
	 * Obtiene el formulario de soporte.
	 *
	 * @param id
	 *            el identificador
	 * @return el formulario de soporte
	 */
	FormularioSoporte getById(Long id);

	/**
	 * Añade el formulario de soporte.
	 *
	 * @param idEntidad
	 *            identificador de entidad
	 * @param fst
	 *            el formulario de soporte
	 */
	void add(Long idEntidad, FormularioSoporte fst);

	/**
	 * Elimina el formulario de soporte..
	 *
	 * @param id
	 *            el identificador
	 */
	void remove(Long id);

	/**
	 * Actualiza el formulario de soporte.
	 *
	 * @param fst
	 *            el formulario de soporte
	 */
	void update(FormularioSoporte fst);

	/**
	 * Obtiene la lista de formulario de soporte.
	 *
	 * @param idEntidad
	 *            identificador de entidad
	 * @return la lista de formulario de soporte
	 */
	List<FormularioSoporte> getAll(Long idEntidad);

}
