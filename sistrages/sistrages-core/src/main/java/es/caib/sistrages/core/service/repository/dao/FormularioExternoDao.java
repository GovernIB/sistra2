package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.comun.FilaImportarGestor;
import es.caib.sistrages.core.api.model.types.TypeIdioma;

/**
 * La interface FormularioExternoDao.
 */
public interface FormularioExternoDao {

	/**
	 * Obtiene el formulario externo.
	 *
	 * @param pId identificador de formulario externo
	 * @return el formulario externo
	 */
	GestorExternoFormularios getById(Long pId);

	/**
	 * Añade el formulario externo.
	 *
	 * @param removeByArea       identificador de la area
	 * @param pFormularioExterno formulario externo
	 */
	void add(final Long pIdArea, GestorExternoFormularios pFormularioExterno);

	/**
	 * Elimina el formulario externo.
	 *
	 * @param pId el identificador de formulario externo
	 */
	void remove(Long pId);

	/**
	 * Comprueba si tiene tramites un Formulario gestor externo (FGE)
	 * 
	 * @param idFGE
	 * @return
	 */
	boolean tieneTramitesAsociados(Long idFGE);

	/**
	 * Elimina el formulario externo.
	 *
	 * @param pIdArea identificador del area
	 */
	void removeByArea(Long pIdArea);

	/**
	 * Actualiza el formulario externo.
	 *
	 * @param pFormularioExterno el formulario externo
	 */
	void update(GestorExternoFormularios pFormularioExterno);

	/**
	 * Lista de avisos de area
	 *
	 * @param pIdArea identificador de la area
	 * @return la lista form externos del area
	 */
	List<GestorExternoFormularios> getAll(Long pIdArea);

	/**
	 * Lista de avisos del area
	 *
	 * @param pIdArea identificador de la area
	 * @param pFiltro filtro
	 * @param pIdioma idioma
	 * @return la lista de Formulario Externo
	 */
	List<GestorExternoFormularios> getAllByFiltro(Long pIdArea, TypeIdioma pIdioma, String pFiltro);

	/**
	 * Existe formulario gestor externo.
	 *
	 * @param identificador
	 * @return
	 */
	boolean existeFormulario(String identificador, final Long idCodigo);

	/**
	 * Lista de gestores externos segun configuracion
	 *
	 * @param id
	 * @param idArea
	 * @return
	 */
	List<GestorExternoFormularios> getGestorExternoByAutenticacion(Long id, Long idArea);

	/**
	 * Gestor externo de formulario por identificador
	 *
	 * @param identificador
	 * @return
	 */
	GestorExternoFormularios getFormularioExternoByIdentificador(String identificador);

	/**
	 * Método que importa un gestor area
	 *
	 * @param filaGestor
	 * @param idArea
	 * @return
	 */
	Long importar(FilaImportarGestor filaGestor, Long idArea);

}
