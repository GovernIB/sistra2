package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.GestorExternoFormularios;
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
	 * @param pIdEntidad         identificador de la entidad
	 * @param pFormularioExterno formulario externo
	 */
	void add(final Long pIdEntidad, GestorExternoFormularios pFormularioExterno);

	/**
	 * Elimina el formulario externo.
	 *
	 * @param pId el identificador de formulario externo
	 */
	void remove(Long pId);

	/**
	 * Elimina el formulario externo.
	 *
	 * @param pIdEntidad identificador de la entidad
	 */
	void removeByEntidad(Long pIdEntidad);

	/**
	 * Actualiza el formulario externo.
	 *
	 * @param pFormularioExterno el formulario externo
	 */
	void update(GestorExternoFormularios pFormularioExterno);

	/**
	 * Lista de avisos de entidad
	 *
	 * @param pIdEntidad identificador de la entidad
	 * @return la lista de avisos de entidad
	 */
	List<GestorExternoFormularios> getAll(Long pIdEntidad);

	/**
	 * Lista de avisos de entidad
	 *
	 * @param pIdEntidad identificador de la entidad
	 * @param pFiltro    filtro
	 * @param pIdioma    idioma
	 * @return la lista de Formulario Externo
	 */
	List<GestorExternoFormularios> getAllByFiltro(Long pIdEntidad, TypeIdioma pIdioma, String pFiltro);

}
