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
	 * Obtiene el formateador de formulario segun el identificador.
	 *
	 * @param codigo
	 *            codigo
	 * @return el formateador de formulario.
	 */
	FormateadorFormulario getFormateadorFormulario(String codigo);

	/**
	 * Añade el formateador de formulario.
	 *
	 * @param idEntidad
	 *            idEntidad
	 * @param fmt
	 *            el formateador de formulario
	 */
	void addFormateadorFormulario(Long idEntidad, FormateadorFormulario fmt);

	/**
	 * Elimina el formateador de formulario.
	 *
	 * @param idFmt
	 *            el identificador
	 * @return true, si se realiza correctamente
	 */
	void removeFormateadorFormulario(Long idFmt);

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
	 * @param idEntidad
	 *            idEntidad
	 * @param filtro
	 *            filtro busqueda
	 * @return la lista de formateadores de formulario
	 */
	List<FormateadorFormulario> listFormateadorFormulario(Long idEntidad, String filtro);

	/**
	 * Comprueba si un id de formateador tiene relaciones.
	 *
	 * @param idFmt
	 * @return
	 */
	boolean tieneRelacionesFormateadorFormulario(Long idFmt);

}
