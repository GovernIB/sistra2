package es.caib.sistrages.core.api.service;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

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

	ObjetoFormulario addComponenteFormulario(TypeObjetoFormulario pTipoObjeto, Long pIdPagina, Long pIdLinea,
			Integer pOrden, String pPosicion);

	void updateComponenteFormulario(ComponenteFormulario pComponente);

	ComponenteFormulario getComponenteFormulario(final Long pId);

	void removeComponenteFormulario(final Long pId);

	void removeLineaFormulario(final Long pId);

	void updateOrdenComponenteFormulario(Long pId, Integer pOrden);

	void updateOrdenLineaFormulario(Long pId, Integer pOrden);

}
