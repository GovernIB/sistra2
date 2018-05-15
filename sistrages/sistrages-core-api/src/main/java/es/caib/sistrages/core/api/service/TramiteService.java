package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Tasa;
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
	 */
	public void updateTramiteVersion(TramiteVersion tramiteVersion);

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

	/**
	 * Añade un tramite a un paso.
	 *
	 * @param formularioTramite
	 * @param idTramitePaso
	 */
	public void addFormularioTramite(FormularioTramite formularioTramite, Long idTramitePaso);

	/**
	 * Actualiza formulario.
	 *
	 * @param formularioTramite
	 */
	public void updateFormularioTramite(FormularioTramite formularioTramite);

	/**
	 * Obtiene un documento.
	 *
	 * @param idDocumento
	 * @return
	 */
	public Documento getDocumento(Long idDocumento);

	/**
	 * Añade un documento a un paso.
	 *
	 * @param documento
	 * @param idTramitePaso
	 */
	public void addDocumentoTramite(Documento documento, Long idTramitePaso);

	/**
	 * Actualiza un documento.
	 *
	 * @param documento
	 */
	public void updateDocumentoTramite(Documento documento);

	/**
	 * Borrar documento.
	 *
	 * @param idTramitePaso
	 * @param idDocumento
	 */
	public void removeDocumento(Long idTramitePaso, Long idDocumento);

	/**
	 * Obtiene una tasa.
	 *
	 * @param idTasa
	 * @return
	 */
	public Tasa getTasa(Long idTasa);

	/**
	 * Añade una tasa a un paso.
	 *
	 * @param tasa
	 * @param idTramitePaso
	 */
	public void addTasaTramite(Tasa tasa, Long idTramitePaso);

	/**
	 * Actualiza una tasa.
	 *
	 * @param tasa
	 */
	public void updateTasaTramite(Tasa tasa);

	/**
	 * Borra una tasa.
	 *
	 * @param idTramitePaso
	 * @param idTasa
	 */
	public void removeTasa(Long idTramitePaso, Long idTasa);

	/**
	 * Sube un documento asociado a un anexo.
	 *
	 * @param idDocumento
	 * @param fichero
	 * @param contents
	 */
	public void uploadDocAnexo(Long idDocumento, Fichero fichero, byte[] contents);

	/**
	 * Borra el doc de un anexo.
	 *
	 * @param idDocumento
	 */
	public void removeDocAnexo(Long idDocumento);

}
