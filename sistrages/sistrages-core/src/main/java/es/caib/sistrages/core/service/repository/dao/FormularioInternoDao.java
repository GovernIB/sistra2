package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.FormularioInterno;

/**
 * La interface FormularioInternoDao.
 */
public interface FormularioInternoDao {

	/**
	 * Obtiene el formulario interno.
	 *
	 * @param pId
	 *            el identificador del formulario interno
	 * @return formulario interno
	 */
	FormularioInterno getById(Long pId);

	/**
	 * Obtiene el formulario interno y las paginas.
	 *
	 * @param pId
	 *            el identificador del formulario interno
	 * @return formulario interno
	 */
	FormularioInterno getFormPagById(Long pId);

	/**
	 * Añade el formulario interno.
	 *
	 * @param pFormInt
	 *            formulario interno
	 */
	void add(FormularioInterno pFormInt);

	/**
	 * Elimina el formulario interno.
	 *
	 * @param pId
	 *            el identificador del formulario interno
	 */
	void remove(Long pId);

	/**
	 * Actualiza el formulario interno.
	 *
	 * @param pFormInt
	 *            formulario interno
	 */
	void update(FormularioInterno pFormInt);

	/**
	 * Obtiene la lista de formulario internos.
	 *
	 * @return la lista de formulario internos
	 */
	List<FormularioInterno> getAll();

}
