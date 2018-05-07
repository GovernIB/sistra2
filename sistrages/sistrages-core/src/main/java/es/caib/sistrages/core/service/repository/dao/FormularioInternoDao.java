package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.PaginaFormulario;

/**
 * La interface FormularioInternoDao.
 */
public interface FormularioInternoDao {

	/**
	 * Obtiene el valor de formulario.
	 *
	 * @param pId
	 *            identificador de formulario
	 * @return el valor de FormularioInterno
	 */
	FormularioInterno getById(Long pId);

	/**
	 * Obtiene el valor de formulario y sus paginas.
	 *
	 * @param pId
	 *            identificador de formulario
	 * @return el valor de FormularioInterno
	 */
	FormularioInterno getFormularioPaginasById(Long pId);

	/**
	 * Obtiene el valor de pagina.
	 *
	 * @param pId
	 *            identificador de pagina
	 * @return el valor de pagina
	 */
	PaginaFormulario getPaginaById(Long pId);

	/**
	 * Obtiene el valor de la pagina y su contenido.
	 *
	 * @param pId
	 *            identificador de pagina
	 * @return el valor de pagina
	 */
	PaginaFormulario getContenidoPaginaById(Long pId);

	/**
	 * Obtiene el valor del componente.
	 *
	 * @param pId
	 *            identificador de componente
	 * @return el valor de componente
	 */
	ComponenteFormulario getComponenteById(Long pId);

	/**
	 * Añade un formulario.
	 *
	 * @param pFormInt
	 *            formulario
	 */
	void add(FormularioInterno pFormInt);

	/**
	 * Elimina un formulario.
	 *
	 * @param pId
	 *            identificador de formulario
	 */
	void remove(Long pId);

	/**
	 * Actualiza un formulario.
	 *
	 * @param pFormInt
	 *            formulario
	 */
	void update(FormularioInterno pFormInt);

	/**
	 * Obtiene el valor de all.
	 *
	 * @return el valor de all
	 */
	List<FormularioInterno> getAll();

	/**
	 * Añade un componente.
	 *
	 * @param pFormInt
	 *            formulario
	 */
	void addComponente(FormularioInterno pFormInt);

	/**
	 * Actualiza un componente de formulario.
	 *
	 * @param pComponente
	 *            componente de formulario
	 */
	void updateComponente(ComponenteFormulario pComponente);

}
