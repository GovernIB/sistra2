package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.FormateadorFormulario;

/**
 * La interface FormateadorFormularioDao.
 */
public interface FormateadorFormularioDao {

	/**
	 * Obtiene el Formateador de Formulario.
	 *
	 * @param id
	 *            el identificador
	 * @return el Formateador Formulario
	 */
	FormateadorFormulario getById(Long id);

	/**
	 * Añade el Formateador de Formulario.
	 *
	 * @param fmt
	 *            el formateador de formulario
	 */
	void add(FormateadorFormulario fmt);

	/**
	 * Elimina el Formateador de Formulario..
	 *
	 * @param id
	 *            el identificador
	 */
	void remove(Long id);

	/**
	 * Actualiza el Formateador de Formulario.
	 *
	 * @param fmt
	 *            el formateador de formulario
	 */
	void update(FormateadorFormulario fmt);

	/**
	 * Obtiene la lista Formateadores de Formulario.
	 *
	 * @return lista de Formateadores de Formulario
	 */
	List<FormateadorFormulario> getAll();

	/**
	 * Obtiene la lista de Formateadores de Formulario.
	 *
	 * @param filtro
	 *            filtro
	 * @return la lista de Formateadores de Formulario
	 */
	List<FormateadorFormulario> getAllByFiltro(String filtro);

}
