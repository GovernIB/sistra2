package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.ObjetoFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.PlantillaFormulario;
import es.caib.sistrages.core.api.model.PlantillaIdiomaFormulario;
import es.caib.sistrages.core.api.model.SeccionReutilizable;
import es.caib.sistrages.core.api.model.comun.DisenyoFormularioSimple;
import es.caib.sistrages.core.api.model.comun.FilaImportarSeccion;
import es.caib.sistrages.core.api.model.comun.ScriptInfo;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;

/**
 * La interface FormularioInternoDao.
 */
public interface FormularioInternoDao {

	/**
	 * Obtiene el valor de formulario.
	 *
	 * @param pId identificador de formulario
	 * @return el valor de DisenyoFormulario
	 */
	DisenyoFormulario getFormularioById(Long pId);

	/**
	 * Obtiene el valor de formulario y sus paginas sin contenido.
	 *
	 * @param pId identificador de formulario
	 * @return el valor de DisenyoFormulario
	 */
	DisenyoFormulario getFormularioPaginasById(Long pId);

	/**
	 * Obtiene el valor de formulario y sus paginas con su contenido.
	 *
	 * @param pId identificador de formulario
	 * @return el valor de DisenyoFormulario
	 */
	DisenyoFormulario getFormularioCompletoById(Long pId, boolean sinSecciones);

	/**
	 * Obtiene el valor de pagina.
	 *
	 * @param pId identificador de pagina
	 * @return el valor de pagina
	 */
	PaginaFormulario getPaginaById(Long pId);

	/**
	 * Obtiene el valor de la pagina y su contenido.
	 *
	 * @param pId identificador de pagina
	 * @return el valor de pagina
	 */
	PaginaFormulario getContenidoPaginaById(Long pId, boolean sinSeccionesReutilizables);

	/**
	 * Obtiene el valor del componente.
	 *
	 * @param pId identificador de componente
	 * @return el valor de componente
	 */
	ComponenteFormulario getComponenteById(Long pId);

	/**
	 * Crea un formulario con una pagina por defecto.
	 *
	 * @param pFormTra formulario tramite
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
	 * @param pFormInt formulario
	 */
	void updateFormulario(DisenyoFormulario pFormInt);

	/**
	 * Añade componente o linea.
	 *
	 * @param pTipoObjeto tipo de objeto
	 * @param pIdLinea    identificador de linea
	 * @param pOrden      orden del elemento seleccionado
	 * @param pPosicion   posicion a insertar sobre el elemento seleccionado
	 * @param objeto 	  elemento para ser utilizado en el objeto añadido (de momento, sólo en sección reutilizable)
	 * @return El elemento añadido
	 */
	ObjetoFormulario addComponente(final TypeObjetoFormulario pTipoObjeto, final Long pIdPagina, final Long pIdLinea,
			final Integer pOrden, final String pPosicion, final Object objeto, boolean isTipoSeccion, String identificadorSeccion);

	/**
	 * Actualiza un componente de formulario.
	 *
	 * @param pComponente componente de formulario
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
	 * @param idFormularioTramite
	 * @param idFormulario
	 * @param idComponente
	 * @param idPagina
	 * @param cargarPaginasPosteriores
	 * @param isSeccion
	 * @param identificadorSeccion
	 * @return
	 */
	DisenyoFormularioSimple getFormularioInternoSimple(Long idFormularioTramite, Long idFormulario, String idComponente,
			String idPagina, boolean cargarPaginasPosteriores,  boolean isSeccion, String identificadorSeccion);

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
	 * @param idFormulario  id formulario
	 * @param codElemento   cod elemento
	 * @param identificador identificador
	 * @return true, si el identificador de elementoFormulario es duplicado
	 */
	boolean isIdElementoFormularioDuplicated(Long idFormulario, Long codElemento, String identificador);

	/**
	 * Copia/Corta un componente.
	 *
	 * @param pIdPagina
	 * @param pIdLinea
	 * @param pOrden
	 * @param pPosicion
	 * @param idComponenteOriginal
	 * @param cut
	 * @return
	 */
	ObjetoFormulario copyCutComponenteFormulario(Long pIdPagina, Long pIdLinea, Integer pOrden, String pPosicion,
			Long idComponenteOriginal, boolean cut);

	/**
	 * Copia/Corta una linea
	 *
	 * @param idPagina
	 * @param idLinea
	 * @param orden
	 * @param posicionamiento
	 * @param cut
	 * @return
	 */
	ObjetoFormulario copyCutLineaFormulario(Long idPagina, Long idLinea, Integer orden, String posicionamiento,
			boolean cut);

	/**
	 * Borra todos las referencias entre componentes de tipo selector y trámites
	 * (normalmente producido por un cambio de area al mover trámite o clonar).
	 *
	 * @param idTramite
	 * @param idArea
	 */
	void borrarReferencias(Long idTramite, Long idAreaAntigua);

	/**
	 * Actualiza pagina.
	 *
	 * @param paginaFormulario pagina formulario
	 */
	void updatePagina(PaginaFormulario paginaFormulario);

	/**
	 * Obtiene una lista de ScriptInfo con los scripts de todos los formularios.
	 *
	 * @param idFormularioInterno Identificador del formulario interno
	 * @param formulario          Formulario del trámite
	 * @return
	 */
	List<ScriptInfo> getScriptsInfo(Long idFormularioInterno, FormularioTramite formulario);

	/**
	 * Guarda los datos simples de pagina.
	 *
	 * @param pagina
	 */
	void guardarPagina(PaginaFormulario pagina);

	/**
	 * Crea formulario.
	 * @param seccion
	 * @return
	 */
	Long addFormulario(SeccionReutilizable seccion);

	/**
	 * Importa un formulario de una seccion dependiendo de la accion.
	 * @param filaSeccion
	 * @return
	 */
	Long importarFormularioSeccion(FilaImportarSeccion filaSeccion);

}
