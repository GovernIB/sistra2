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
	 * @param idArea                   id Area
	 * @param GestorExternoFormularios el valor de Formulario Externo
	 */
	void addFormularioExterno(Long idArea, GestorExternoFormularios formularioExterno);

	/**
	 * Elimina Formulario Externo.
	 *
	 * @param id el identificador
	 * @return true, si se realiza correctamente
	 */
	boolean removeFormularioExterno(Long id);

	/**
	 * Comprueba si un GFE tiene trámites asociados.
	 *
	 * @param id el identificador
	 * @return true, si tiene trámites asociados
	 * @return false, si no tiene trámites asociados
	 */

	boolean tieneTramitesAsociados(Long idGFE);

	/**
	 * Actualiza Formulario Externo.
	 *
	 * @param GestorExternoFormularios el valor de Formulario Externo
	 */
	void updateFormularioExterno(GestorExternoFormularios formularioExterno);

	/**
	 * Lista de Formulario Externo.
	 *
	 * @param idArea id Area
	 * @param idioma idioma
	 * @param filtro filtro busqueda
	 * @return la lista de Formulario Externo
	 */
	List<GestorExternoFormularios> listFormularioExterno(Long idArea, TypeIdioma idioma, String filtro);

	/**
	 * Comprueba si ya existe un identificador.
	 *
	 * @param identificador
	 * @para midCodigo
	 * @return
	 */
	boolean existeFormulario(String identificador, Long idCodigo);

	/**
	 * Lista de gestores externos segun configuracion
	 *
	 * @param valueOf
	 * @param valueOf2
	 * @return
	 */
	List<GestorExternoFormularios> getGestorExternoByAutenticacion(Long id, Long idArea);

	/**
	 * Gestor externo formulario por identificador
	 *
	 * @param identificador
	 * @return
	 */
	GestorExternoFormularios getFormularioExternoByIdentificador(String identificador);

}
