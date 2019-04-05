package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.DominioTramite;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramite;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteVersion;
import es.caib.sistrages.core.api.model.comun.TramiteSimple;

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
	 * @return
	 */
	Long add(Long idArea, final Tramite pTramite);

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
	Long addTramiteVersion(TramiteVersion tramiteVersion, String idTramite);

	/**
	 * Actualiza un tramite versión.
	 *
	 * @param tramiteVersion
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
	 * Devuelve los códigos de dominios de una versión de trámite.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	List<Dominio> getDominioSimpleByTramiteId(Long idTramiteVersion);

	/**
	 * Devuelve los identificadores de dominio de una versión de trámite.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	List<String> getTramiteDominiosIdentificador(Long idTramiteVersion);

	/**
	 * Bloquea un tramite.
	 *
	 * @param idTramiteVersion
	 * @param username
	 */
	void bloquearTramiteVersion(Long idTramiteVersion, String username);

	/**
	 * Desbloquea un tramite.
	 *
	 * @param idTramiteVersion
	 * @param username
	 */
	void desbloquearTramiteVersion(Long idTramiteVersion);

	/**
	 * Comprueba si tiene tramite version.
	 *
	 * @param idTramite
	 * @return
	 */
	boolean tieneTramiteVersion(Long idTramite);

	/**
	 * Tiene release repetida.
	 *
	 * @param idTramite
	 * @param release
	 * @return
	 */
	boolean tieneTramiteNumVersionRepetido(Long idTramite, int release);

	/**
	 * Obtiene la release máxima del tramite (por defecto, es 0).
	 *
	 * @param idTramite
	 * @return
	 */
	int getTramiteNumVersionMaximo(Long idTramite);

	/**
	 * Clona tramite version.
	 *
	 * @param idTramiteVersion
	 * @param idArea
	 * @param idTramite
	 * @return
	 */
	Long clonarTramiteVersion(Long idTramiteVersion, Long idArea, Long idTramite);

	/**
	 * Obtiene el id de los tramites de un area que tienen una versión activa.
	 *
	 * @param idArea
	 * @return
	 */
	List<Long> listTramiteVersionActiva(Long idArea);

	/**
	 * Obtiene tramite a partir de identificador.
	 *
	 * @param identificador
	 * @return
	 */
	Tramite getTramiteByIdentificador(String identificador);

	/**
	 * Obtiene tramite version a partir del num version y su id tramite.
	 *
	 * @param numVersion
	 * @param idTramite
	 * @return
	 */
	TramiteVersion getTramiteVersionByNumVersion(int numVersion, Long idTramite);

	/**
	 * Devuelve el tramite versión con el num version mas alto.
	 *
	 * @param idTramite
	 * @return
	 */
	TramiteVersion getTramiteVersionMaxNumVersion(Long idTramite);

	/**
	 * Lista de tramite versión que tengan un dominio.
	 *
	 * @param idDominio
	 * @return
	 */
	List<DominioTramite> getTramiteVersionByDominio(Long idDominio);

	/**
	 * Comprueba si el identificador está repetido.
	 *
	 * @param identificador
	 * @param codigo
	 * @return
	 */
	boolean checkIdentificadorRepetido(String identificador, Long codigo);

	/**
	 * Recupera el tramite Version dado el identificador logico
	 *
	 * @param idTramite
	 * @param numeroVersion
	 * @return
	 */
	TramiteVersion getTramiteVersionByNumVersion(String idTramite, int numeroVersion);

	/**
	 * Importa un trámite.
	 *
	 * @param filaTramite
	 * @param idArea
	 * @return
	 */
	Long importar(final FilaImportarTramite filaTramite, final Long idArea);

	/**
	 * Importa una versión de trámite.
	 *
	 * @param filaTramiteVersion
	 * @param idTramite
	 * @param idDominios
	 * @param usuario
	 * @return
	 */
	Long importar(FilaImportarTramiteVersion filaTramiteVersion, Long idTramite, List<Long> idDominios, String usuario);

	/**
	 * Obtiene el tramite simplificado.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	TramiteSimple getTramiteSimple(String idTramiteVersion);

	/**
	 * Obtiene los idiomas disponibles de la versión del trámite.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	String getIdiomasDisponibles(String idTramiteVersion);

	/**
	 * Obtiene todos los trámites de una entidad.
	 *
	 * @param idEntidad
	 * @return
	 */
	List<Tramite> getAllByEntidad(Long idEntidad);

	/**
	 * Obtiene todos los trámites de una entidad con filtro.
	 *
	 * @param idEntidad
	 * @param filtro
	 * @return
	 */
	List<Tramite> getAllByEntidad(Long idEntidad, String filtro);

	/**
	 * Obtiene el identificador a partir de un código de una versión.
	 *
	 * @param codigo
	 * @return
	 */
	String getIdentificadorByCodigoVersion(Long codigo);

}