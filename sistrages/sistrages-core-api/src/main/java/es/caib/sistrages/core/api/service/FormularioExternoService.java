package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.types.TypeIdioma;

/**
 * FormularioExterno Service.
 */
public interface FormularioExternoService {

	/**
	 * Obtiene el valor de Formulario Externo.
	 *
	 * @param id el identificador
	 * @return el valor de Formulario Externo
	 */
	GestorExternoFormularios getFormularioExterno(Long id);

	/**
	 * Añade Formulario Externo.
	 *
	 * @param idEntidad         id Entidad
	 * @param GestorExternoFormularios el valor de Formulario Externo
	 */
	void addFormularioExterno(Long idEntidad, GestorExternoFormularios formularioExterno);

	/**
	 * Elimina Formulario Externo.
	 *
	 * @param id el identificador
	 * @return true, si se realiza correctamente
	 */
	boolean removeFormularioExterno(Long id);

	/**
	 * Actualiza Formulario Externo.
	 *
	 * @param GestorExternoFormularios el valor de Formulario Externo
	 */
	void updateFormularioExterno(GestorExternoFormularios formularioExterno);

	/**
	 * Lista de Formulario Externo.
	 *
	 * @param idEntidad id Entidad
	 * @param idioma    idioma
	 * @param filtro    filtro busqueda
	 * @return la lista de Formulario Externo
	 */
	List<GestorExternoFormularios> listFormularioExterno(Long idEntidad, TypeIdioma idioma, String filtro);

}
