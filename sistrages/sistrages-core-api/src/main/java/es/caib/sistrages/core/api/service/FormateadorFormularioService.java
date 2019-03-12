package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormateador;

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
	 * @param idEntidad
	 *            código de la entidad
	 */
	void updateFormateadorFormulario(FormateadorFormulario fmt, Long idEntidad);

	/**
	 * Lista de formateadores de formulario.
	 *
	 * @param idEntidad
	 *            idEntidad
	 * @param filtro
	 *            filtro busqueda
	 * @param bloqueado
	 *            filtro que indica si se busca con el bloqueado activo o ono
	 * @return la lista de formateadores de formulario
	 */
	List<FormateadorFormulario> listFormateadorFormulario(Long idEntidad, String filtro, Boolean bloqueado);

	/**
	 * Comprueba si un id de formateador tiene relaciones.
	 *
	 * @param idFmt
	 * @return
	 */
	boolean tieneRelacionesFormateadorFormulario(Long idFmt);

	/**
	 * Obtiene la lista de plantillas
	 *
	 * @param idFormateador
	 * @return
	 */
	List<PlantillaFormateador> getListaPlantillasFormateador(Long idFormateador);

	/**
	 * Obtiene el formateador de formulario por defecto para la entidad.
	 *
	 * @param idEntidad
	 *            el identificador de la entidad
	 * @return el formateador de formulario por defecto para la entidad, puede ser
	 *         nulo.
	 */
	FormateadorFormulario getFormateadorPorDefecto(Long idEntidad);
}
