package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.comun.FilaImportarArea;

/**
 * La interface AreaDao.
 */
public interface AreaDao {

	/**
	 * Obtiene un area.
	 *
	 * @param pId
	 *            identificador del area
	 * @return area
	 */
	Area getById(final Long pId);

	/**
	 * Obtiene el valor de todas las areas de una entidad.
	 *
	 * @param pIdEntidad
	 *            identificador de la entidad
	 *
	 * @return todas las areas de una entidad
	 */
	List<Area> getAll(final Long pIdEntidad);

	/**
	 * Obtiene el valor de todas las areas.
	 *
	 * @param pIdEntidad
	 *            identificador de la entidad
	 * @param pFiltro
	 *            filtro
	 * @return todas las areas de una entidad
	 */
	List<Area> getAllByFiltro(final Long pIdEntidad, final String pFiltro);

	/**
	 * Añade un area.
	 *
	 * @param pIdEntidad
	 *            identificador de la entidad
	 * @param pArea
	 *            area
	 */
	Long add(Long pIdEntidad, final Area pArea);

	/**
	 * Elimina un area.
	 *
	 * @param pId
	 *            identificador del area
	 */
	void remove(final Long pId);

	/**
	 * Actualiza un area.
	 *
	 * @param pArea
	 *            area
	 */
	void update(final Area pArea);

	/**
	 * Obtiene un area por identificador.
	 *
	 * @param identificador
	 * @return
	 */
	Area getAreaByIdentificador(String identificador);

	/**
	 * Comprueba si el identificador está repetido.
	 *
	 * @param pIdentificador
	 *            identificador de area
	 * @param pCodigo
	 *            codigo del area
	 * @return true, si existe
	 */
	boolean checkIdentificadorRepetido(String pIdentificador, Long pCodigo);

	/**
	 * Importa un area. Hay 4 acciones posibles (TypeImportarAccion):
	 * <ul>
	 * <li>Crear un área (el trámite se tendrá que asociar a este área)</li>
	 * <li>Seleccionar y Nada, implica que se selecciona el área que ya existe</li>
	 * <li>Reemplazar el trámite con la nueva descripción</li>
	 * </ul>
	 *
	 * @param filaArea
	 * @return
	 */
	Long importar(FilaImportarArea filaArea, final Long idEntidad);
}