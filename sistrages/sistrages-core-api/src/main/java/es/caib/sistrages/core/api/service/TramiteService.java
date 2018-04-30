package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramiteTipo;
import es.caib.sistrages.core.api.model.TramiteVersion;

/**
 * La interface TramiteService.
 */
public interface TramiteService {

	/**
	 * Lista de areas.
	 *
	 * @param idEntidad
	 *            idEntidad
	 * @param filtro
	 *            filtro
	 * @return lista de areas
	 */
	public List<Area> listArea(final Long idEntidad, String filtro);

	/**
	 * Obtiene el area.
	 *
	 * @param id
	 *            identificador
	 * @return area
	 */
	public Area getArea(Long id);

	/**
	 * Obtiene el area de un tramite.
	 *
	 * @param id
	 *            identificador tramite
	 * @return area
	 */
	public Area getAreaTramite(Long idTramite);

	/**
	 * Añade un area.
	 *
	 * @param idEntidad
	 *            idEntidad
	 * @param area
	 *            area
	 */
	public void addArea(Long idEntidad, Area area);

	/**
	 * Elimina un area.
	 *
	 * @param id
	 *            identificador
	 * @return true, si se realiza con exito
	 */
	public boolean removeArea(Long id);

	/**
	 * Actualiza un area.
	 *
	 * @param area
	 *            area
	 */
	public void updateArea(Area area);

	/**
	 * Lista de tramites.
	 *
	 * @param idArea
	 *            idArea
	 * @param pFiltro
	 *            filtro
	 * @return lista de tramites
	 */
	public List<Tramite> listTramite(final Long idArea, String pFiltro);

	/**
	 * Obtiene el tramite.
	 *
	 * @param id
	 *            identificador
	 * @return tramite
	 */
	public Tramite getTramite(Long id);

	/**
	 * Añade un Tramite.
	 *
	 * @param idArea
	 *            identificador de area
	 * @param pTramite
	 *            tramite
	 */
	public void addTramite(Long idArea, Tramite pTramite);

	/**
	 * Cambia un trámite de área.
	 *
	 * @param idArea
	 *            id área
	 * @param idTramite
	 *            id trámite
	 */
	public void changeAreaTramite(Long idArea, Long idTramite);

	/**
	 * Elimina un Tramite.
	 *
	 * @param id
	 *            identificador
	 * @return true, si se realiza con exito
	 */
	public boolean removeTramite(Long id);

	/**
	 * Actualiza un Tramite.
	 *
	 * @param pTramite
	 *            tramite
	 */
	public void updateTramite(Tramite pTramite);

	/**
	 * Lista de las versiones de un trámite.
	 *
	 * @param idTramite
	 * @return
	 */
	public List<TramiteVersion> listTramiteVersion(Long idTramite, String filtro);

	/**
	 * Crea una versión de trámite.
	 *
	 * @param tramiteVersion
	 * @param id
	 */
	public void addTramiteVersion(TramiteVersion tramiteVersion, String id);

	/**
	 * Actualiza una versión de trámite.
	 *
	 * @param tramiteVersion
	 * @param borrarScriptPI
	 *            En caso de estar a true, lo marca como nulo el script de params
	 *            iniciales para que lo borre.
	 * @param scriptParamsIniciales
	 * @param borrarScriptPersonalizacion
	 *            En caso de estar a true, lo marca como nulo el script de
	 *            personalizacion para que lo borre.
	 * @param scriptPersonalizacion
	 */
	public void updateTramiteVersion(TramiteVersion tramiteVersion, boolean borrarScriptPI,
			Script scriptParamsIniciales, boolean borrarScriptPersonalizacion, Script scriptPersonalizacion);

	/**
	 * Borra una versión de tramite.
	 *
	 * @param id
	 */
	public void removeTramiteVersion(Long id);

	/**
	 * Obtiene la versión de trámite.
	 *
	 * @param id
	 * @return
	 */
	public TramiteVersion getTramiteVersion(Long idTramiteVersion);

	/**
	 * Devuelve los tipo de paso de trámite.
	 *
	 * @return
	 */
	public List<TramiteTipo> listTipoTramitePaso();

	/**
	 * Devuelve los pasos de una versión de trámite
	 *
	 * @param id
	 * @return
	 */
	public List<TramitePaso> getTramitePasos(Long idTramiteVersion);

	/**
	 * Devuelve los dominios de un trámite.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	public List<Dominio> getTramiteDominios(Long idTramiteVersion);

	/**
	 * Actualiza un paso de trámite.
	 *
	 * @param tramitePaso
	 */
	public void updateTramitePaso(TramitePaso tramitePaso);

	/**
	 * Borra un formulario de un paso.
	 *
	 * @param idTramitePaso
	 * @param idFormulario
	 */
	public void removeFormulario(Long idTramitePaso, Long idFormulario);

	/**
	 * Obtiene el tramitePaso.
	 *
	 * @param id
	 * @return
	 */
	public TramitePaso getTramitePaso(Long id);

	/**
	 * Devuelve un formulario trámite.
	 *
	 * @param idFormularioTramite
	 * @return
	 */
	public FormularioTramite getFormulario(Long idFormularioTramite);

}
