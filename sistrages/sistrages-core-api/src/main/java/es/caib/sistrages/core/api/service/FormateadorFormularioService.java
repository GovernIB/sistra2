package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.FormateadorFormulario;

/**
 * FormateadorFormularioService.
 */
public interface FormateadorFormularioService {

	/**
	 * Obtiene el formateador de formulario.
	 *
	 * @param idFmt
	 *            el identificador
	 * @return el formateador de formulario.
	 */
	FormateadorFormulario getFormateadorFormulario(Long idFmt);

	/**
	 * Añade el formateador de formulario.
	 *
	 * @param fmt
	 *            el formateador de formulario
	 */
	void addFormateadorFormulario(FormateadorFormulario fmt);

	/**
	 * Elimina el formateador de formulario.
	 *
	 * @param idFmt
	 *            el identificador
	 * @return true, si se realiza correctamente
	 */
	boolean removeFormateadorFormulario(Long idFmt);

	/**
	 * Actualiza el formateador de formulario.
	 *
	 * @param fmt
	 *            el formateador de formulario
	 */
	void updateFormateadorFormulario(FormateadorFormulario fmt);

	/**
	 * Lista de formateadores de formulario.
	 *
	 * @param filtro
	 *            filtro busqueda
	 * @return la lista de formateadores de formulario
	 */
	List<FormateadorFormulario> listFormateadorFormulario(String filtro);

}
