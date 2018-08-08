package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.comun.FilaImportarFormateador;

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
	 * Obtiene formateador por código.
	 *
	 * @param codigo
	 *            código
	 * @return formateador
	 */
	FormateadorFormulario getByCodigo(String codigo);

	/**
	 * Añade el Formateador de Formulario.
	 *
	 * @param idEntidad
	 *            id entidad
	 * @param fmt
	 *            el formateador de formulario
	 */
	void add(final Long idEntidad, FormateadorFormulario fmt);

	/**
	 * Elimina el Formateador de Formulario.
	 *
	 * @param id
	 *            el identificador
	 */
	void remove(Long id);

	/**
	 * Elimina el Formateadores de Formulario de entidad.
	 *
	 * @param idEntidad
	 *            el identificador entidad
	 */
	void removeByEntidad(Long idEntidad);

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
	 * @param idEntidad
	 *            el identificador entidad
	 * @return lista de Formateadores de Formulario
	 */
	List<FormateadorFormulario> getAll(Long idEntidad);

	/**
	 * Obtiene la lista de Formateadores de Formulario.
	 *
	 * @param idEntidad
	 *            el identificador entidad
	 * @param filtro
	 *            filtro
	 * @return la lista de Formateadores de Formulario
	 */
	List<FormateadorFormulario> getAllByFiltro(Long idEntidad, String filtro);

	/**
	 * Importa un formateador formulario.
	 *
	 * @param filaFormateador
	 * @param idEntidad
	 * @return
	 */
	Long importar(FilaImportarFormateador filaFormateador, Long idEntidad);

}
