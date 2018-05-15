package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteTipo;
import es.caib.sistrages.core.api.model.TramiteVersion;

/**
 * La interface TramiteDao.
 */
public interface TramiteDao {

	/**
	 * Obtiene el tramite.
	 *
	 * @param id
	 *            identificador
	 * @return el valor del tramite
	 */
	Tramite getById(final Long id);

	/**
	 * Obtiene el valor de todos los tramites.
	 *
	 * @param idArea
	 *            Id entidad
	 *
	 * @return el valor de todos los tramites
	 */
	List<Tramite> getAll(final Long idArea);

	/**
	 * Obtiene el valor de todos los tramites.
	 *
	 * @param idArea
	 *            Id entidad
	 * @param pFiltro
	 *            filtro
	 * @return el valor de todos los tramites
	 */
	List<Tramite> getAllByFiltro(final Long idArea, final String pFiltro);

	/**
	 * Añade tramite.
	 *
	 * @param idArea
	 *            idArea
	 * @param pTramite
	 *            Tramite
	 */
	void add(Long idArea, final Tramite pTramite);

	/**
	 * Elimina tramite.
	 *
	 * @param id
	 *            id
	 */
	void remove(final Long id);

	/**
	 * Actualiza tramite.
	 *
	 * @param pTramite
	 *            Tramite
	 */
	void update(final Tramite pTramite);

	/**
	 * Devuelve las versiones de un trámite.
	 *
	 * @param idTramite
	 * @param filtro
	 * @return
	 */
	List<TramiteVersion> getTramitesVersion(Long idTramite, String filtro);

	/**
	 * Crea un tramite version.
	 *
	 * @param tramiteVersion
	 * @param idTramite
	 * @return
	 */
	void addTramiteVersion(TramiteVersion tramiteVersion, String idTramite);

	/**
	 * Actualiza un tramite versión.
	 *
	 * @param tramiteVersion
	 * @param borrarScriptPI
	 *            Si borra el script
	 * @param scriptParamsIniciales
	 * @param borrarScriptPersonalizacion
	 *            Si borra el script
	 * @param scriptPersonalizacion
	 */
	void updateTramiteVersion(TramiteVersion tramiteVersion);

	/**
	 * Borra un trámite versión.
	 *
	 * @param idTramiteVersion
	 */
	void removeTramiteVersion(Long idTramiteVersion);

	/**
	 * Devuelve versión de trámite.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	TramiteVersion getTramiteVersion(Long idTramiteVersion);

	/**
	 * Devuelve la lista de tiops de trámite.
	 *
	 * @return
	 */
	List<TramiteTipo> listTipoTramitePaso();

	/**
	 * Obtiene area tramite.
	 *
	 * @param idTramite
	 *            id tramite
	 * @return
	 */
	Area getAreaTramite(Long idTramite);

	/**
	 * Cambia trámite de área.
	 *
	 * @param idArea
	 *            id área
	 * @param idTramite
	 *            id trámite
	 */
	void changeAreaTramite(Long idArea, Long idTramite);

	/**
	 * Devuelve los dominios de una versión de trámite.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	List<Dominio> getTramiteDominios(Long idTramiteVersion);

}