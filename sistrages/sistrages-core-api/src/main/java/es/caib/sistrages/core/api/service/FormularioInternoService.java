package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.FormularioInterno;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * La interface FormularioInternoService.
 */
public interface FormularioInternoService {

	/**
	 * Obtiene el valor de un formulario.
	 *
	 * @param pId
	 *            identificador de formulario
	 * @return el valor del formulario
	 */
	FormularioInterno getFormularioInterno(Long pId);

	/**
	 * Obtiene el valor de formulario y las paginas sin su contenido.
	 *
	 * @param pId
	 *            identificador de formulario
	 * @return el valor de formulario y las paginas
	 */
	FormularioInterno getFormularioInternoPaginas(Long pId);

	/**
	 * Obtiene el valor de formulario y las paginas con su contenido.
	 *
	 * @param pId
	 *            identificador de formulario
	 * @return el valor de formulario y las paginas
	 */
	FormularioInterno getFormularioInternoCompleto(Long pId);

	/**
	 * Actualiza un formulario.
	 *
	 * @param pFormInt
	 *            formulario
	 */
	void updateFormularioInterno(FormularioInterno pFormInt);

	/**
	 * Obtiene el valor de una pagina del formulario.
	 *
	 * @param pId
	 *            identificador de la pagina del formulario
	 * @return el valor de la pagina del formulario
	 */
	PaginaFormulario getPaginaFormulario(Long pId);

	/**
	 * Obtiene el valor del contenido de una pagina del formulario.
	 *
	 * @param pId
	 *            identificador de la pagina del formulario
	 * @return el contenido de la pagina del formulario
	 */
	PaginaFormulario getContenidoPaginaFormulario(Long pId);

	/**
	 * Añade un componente o linea al formulario.
	 *
	 * @param pTipoObjeto
	 *            tipo de objeto a añadir
	 * @param pIdPagina
	 *            identificador de la pagina
	 * @param pIdLinea
	 *            identificador de la linea
	 * @param pOrden
	 *            orden
	 * @param pPosicion
	 *            posicion
	 * @return objeto formulario
	 */
	ObjetoFormulario addComponenteFormulario(TypeObjetoFormulario pTipoObjeto, Long pIdPagina, Long pIdLinea,
			Integer pOrden, String pPosicion);

	/**
	 * Actualiza un componente del formulario.
	 *
	 * @param pComponente
	 *            componente
	 * @return objeto formulario
	 */
	ObjetoFormulario updateComponenteFormulario(ComponenteFormulario pComponente);

	/**
	 * Obtiene el valor de un componente del formulario.
	 *
	 * @param pId
	 *            identificador del componente
	 * @return el valor del componente del formulario
	 */
	ComponenteFormulario getComponenteFormulario(final Long pId);

	/**
	 * Elimina un componente del formulario.
	 *
	 * @param pId
	 *            identificador del componente
	 */
	void removeComponenteFormulario(final Long pId);

	/**
	 * Elimina un linea del formulario.
	 *
	 * @param pId
	 *            identificador de la linea
	 */
	void removeLineaFormulario(final Long pId);

	/**
	 * Cambia el orden de un componente del formulario.
	 *
	 * @param pId
	 *            identificador del componente
	 * @param pOrden
	 *            orden
	 */
	void updateOrdenComponenteFormulario(Long pId, Integer pOrden);

	/**
	 * Cambia el orden de la linea del formulario.
	 *
	 * @param pId
	 *            identificador de la linea
	 * @param pOrden
	 *            orden
	 */
	void updateOrdenLineaFormulario(Long pId, Integer pOrden);

	/**
	 * Obtiene la lista de las plantillas idioma del formulario.
	 *
	 * @param pId
	 *            identificador de la plantilla
	 * @return el valor de la lista de plantillas idioma del formulario
	 */
	List<PlantillaIdiomaFormulario> getListaPlantillaIdiomaFormulario(Long pId);

	/**
	 * Actualiza la plantilla idioma del formulario.
	 *
	 * @param idEntidad
	 *            identificador de la entidad
	 * @param idPlantilla
	 *            identificador de la plantilla
	 * @param plantilla
	 *            plantilla idioma
	 * @param contents
	 *            contenido del fichero de la plantilla para el idioma
	 * @return the plantilla idioma formulario
	 */
	PlantillaIdiomaFormulario uploadPlantillaIdiomaFormulario(Long idEntidad, final Long idPlantilla,
			PlantillaIdiomaFormulario plantilla, byte[] contents);

	/**
	 * Elimina la plantilla idioma del formulario.
	 *
	 * @param plantillaIdiomaFormulario
	 *            plantilla idioma del formulario
	 */
	void removePlantillaIdiomaFormulario(PlantillaIdiomaFormulario plantillaIdiomaFormulario);

}
