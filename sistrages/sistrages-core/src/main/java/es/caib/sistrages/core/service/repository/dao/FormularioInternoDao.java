package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormulario;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.comun.DisenyoFormularioSimple;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * La interface FormularioInternoDao.
 */
public interface FormularioInternoDao {

	/**
	 * Obtiene el valor de formulario.
	 *
	 * @param pId
	 *            identificador de formulario
	 * @return el valor de DisenyoFormulario
	 */
	DisenyoFormulario getFormularioById(Long pId);

	/**
	 * Obtiene el valor de formulario y sus paginas sin contenido.
	 *
	 * @param pId
	 *            identificador de formulario
	 * @return el valor de DisenyoFormulario
	 */
	DisenyoFormulario getFormularioPaginasById(Long pId);

	/**
	 * Obtiene el valor de formulario y sus paginas con su contenido.
	 *
	 * @param pId
	 *            identificador de formulario
	 * @return el valor de DisenyoFormulario
	 */
	DisenyoFormulario getFormularioCompletoById(Long pId);

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
	 * Crea un formulario con una pagina por defecto.
	 *
	 * @param pFormTra
	 *            formulario tramite
	 * @return identificador formulario
	 */
	Long addFormulario(FormularioTramite pFormTra);

	/**
	 * Crea una página pudiendo elegir si se pone página por defecto o no.
	 *
	 * @param pFormTra
	 * @param crearPaginaInicial
	 * @return
	 */
	Long addFormulario(FormularioTramite pFormTra, boolean crearPaginaInicial);

	/**
	 * Actualiza un formulario.
	 *
	 * @param pFormInt
	 *            formulario
	 */
	void updateFormulario(DisenyoFormulario pFormInt);

	/**
	 * Añade componente o linea.
	 *
	 * @param pTipoObjeto
	 *            tipo de objeto
	 * @param pIdLinea
	 *            identificador de linea
	 * @param pOrden
	 *            orden del elemento seleccionado
	 * @param pPosicion
	 *            posicion a insertar sobre el elemento seleccionado
	 * @return TODO
	 */
	ObjetoFormulario addComponente(final TypeObjetoFormulario pTipoObjeto, final Long pIdPagina, final Long pIdLinea,
			final Integer pOrden, final String pPosicion);

	/**
	 * Actualiza un componente de formulario.
	 *
	 * @param pComponente
	 *            componente de formulario
	 * @return
	 */
	ObjetoFormulario updateComponente(ComponenteFormulario pComponente);

	void removeLineaFormulario(Long pId);

	void removeComponenteFormulario(Long pId);

	void updateOrdenComponente(Long pId, Integer pOrden);

	void updateOrdenLinea(Long pId, Integer pOrden);

	List<PlantillaIdiomaFormulario> getListaPlantillaIdiomaFormularioById(Long pId);

	PlantillaIdiomaFormulario uploadPlantillaIdiomaFormulario(final Long idPlantilla,
			PlantillaIdiomaFormulario plantilla);

	void removePlantillaIdiomaFormulario(Long idPlantilla);

	/**
	 * Añade una página a un formulario. TODO Debería incluirse en addComponent un
	 * Tipo de Pagina.
	 *
	 * @param idFormulario
	 * @param paginaFormulario
	 * @return
	 */
	Long addPagina(Long idFormulario, PaginaFormulario paginaFormulario);

	/**
	 * Añade una plantilla a un formulario. TODO Debería incluirse en addComponent
	 * en un tipo de plantilla formulario.
	 *
	 * @param idFormulario
	 * @param mplantilla
	 * @return
	 */
	Long addPlantilla(Long idFormulario, PlantillaFormulario mplantilla);

	/**
	 * Borra un formulario
	 *
	 * @param codigo
	 */
	void removeFormulario(Long codigo);

	/**
	 * Devuelve el formulario interno simple.
	 *
	 * @param idFormulario
	 * @param idFormularioTramite
	 * @param idComponente
	 * @param idPagina
	 * @return
	 */
	DisenyoFormularioSimple getFormularioInternoSimple(Long idFormularioTramite, Long idFormulario, String idComponente,
			String idPagina);

	/**
	 * Devuelve el identificador del formulario.
	 *
	 * @param idFormulario
	 * @return
	 */
	String getIdentificadorFormularioInterno(Long idFormulario);

	/**
	 * Verifica si el identificador de elementoFormulario está duplicado.
	 *
	 * @param idFormulario
	 *            id formulario
	 * @param codElemento
	 *            cod elemento
	 * @param identificador
	 *            identificador
	 * @return true, si el identificador de elementoFormulario es duplicado
	 */
	boolean isIdElementoFormularioDuplicated(Long idFormulario, Long codElemento, String identificador);

}
