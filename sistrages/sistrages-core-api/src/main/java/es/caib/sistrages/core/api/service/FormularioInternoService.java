package es.caib.sistrages.core.api.service;

import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.PaginaFormulario;

/**
 * La interface FormularioInternoService.
 */
public interface FormularioInternoService {

	/**
	 * Obtiene el valor de formulario.
	 *
	 * @param pId
	 *            identificador de formulario
	 * @return el valor de formulario
	 */
	FormularioInterno getFormularioInterno(Long pId);

	/**
	 * Obtiene el valor de formulario y las paginas.
	 *
	 * @param pId
	 *            identificador de formulario
	 * @return el valor de formulario y las paginas
	 */
	FormularioInterno getFormularioInternoPaginas(Long pId);

	/**
	 * Actualiza formulario.
	 *
	 * @param pFormInt
	 *            formulario
	 */
	void updateFormularioInterno(FormularioInterno pFormInt);

	/**
	 * Obtiene el valor de paginaFormulario.
	 *
	 * @param pId
	 *            identificador de formulario
	 * @return el valor de paginaFormulario
	 */
	PaginaFormulario getPaginaFormulario(Long pId);

	/**
	 * Obtiene el valor del contenido de PaginaFormulario.
	 *
	 * @param pId
	 *            identificador de formulario
	 * @return el valor de contenidoPaginaFormulario
	 */
	PaginaFormulario getContenidoPaginaFormulario(Long pId);

}
