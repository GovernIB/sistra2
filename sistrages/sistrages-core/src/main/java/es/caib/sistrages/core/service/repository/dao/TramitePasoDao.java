package es.caib.sistrages.core.service.repository.dao;

import java.util.List;
import java.util.Map;

import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormateadorFormulario;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.comun.FilaImportarTramiteVersion;

/**
 * La interface TramitePasoDao.
 */
public interface TramitePasoDao {

	/**
	 * Devuelve los pasos de una versión de trámite.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	List<TramitePaso> getTramitePasos(Long idTramiteVersion);

	/**
	 * Actualiza un paso de trámite.
	 *
	 * @param tramitePaso
	 */
	public void updateTramitePaso(final TramitePaso tramitePaso);

	/**
	 * Borra un formulario de un paso de trámite.
	 *
	 * @param idTramitePaso
	 * @param idFormulario
	 */
	public void removeFormulario(Long idTramitePaso, Long idFormulario);

	/**
	 * Devuelve el paso.
	 *
	 * @param idTramitePaso
	 * @return
	 */
	TramitePaso getTramitePaso(Long idTramitePaso);

	/**
	 * Devuelve el formulario tramite.
	 *
	 * @param idFormularioTramite
	 * @return
	 */
	FormularioTramite getFormulario(Long idFormularioTramite);

	/**
	 * Añade un formulario a trámite paso.
	 *
	 * @param formularioTramite
	 * @param idTramitePaso
	 * @param FormularioInterno
	 */
	FormularioTramite addFormularioTramite(FormularioTramite formularioTramite, Long idTramitePaso,
			Long idFormularioInterno);

	/**
	 * Actualiza un formulario a trámite paso.
	 *
	 * @param formularioTramite
	 */
	void updateFormularioTramite(FormularioTramite formularioTramite);

	/**
	 * Devuelve un documento.
	 *
	 * @param idDocumento
	 * @return
	 */
	Documento getDocumento(Long idDocumento);

	/**
	 * Añade un documento a trámite paso.
	 *
	 * @param documento
	 * @param idTramitePaso
	 */
	Documento addDocumentoTramite(Documento documento, Long idTramitePaso);

	/**
	 * Actualiza un documento.
	 *
	 * @param documento
	 */
	void updateDocumentoTramite(Documento documento);

	/**
	 * Borrar un documento.
	 *
	 * @param idTramitePaso
	 * @param idDocumento
	 */
	void removeDocumento(Long idTramitePaso, Long idDocumento);

	/**
	 * Obtiene una tasa.
	 *
	 * @param idTasa
	 * @return
	 */
	Tasa getTasa(Long idTasa);

	/**
	 * Añade una tasa a un tramite.
	 *
	 * @param tasa
	 * @param idTramitePaso
	 * @return Tasa
	 */
	Tasa addTasaTramite(Tasa tasa, Long idTramitePaso);

	/**
	 * Actualiza una tasa.
	 *
	 * @param tasa
	 */
	void updateTasaTramite(Tasa tasa);

	/**
	 * Borra una tasa.
	 *
	 * @param idTramitePaso
	 * @param idTasa
	 */
	void removeTasa(Long idTramitePaso, Long idTasa);

	/**
	 * Sube un documento asociado a un anexo.
	 *
	 * @param idDocumento
	 * @param fichero
	 * @param contents
	 */
	Fichero uploadDocAnexo(Long idDocumento, Fichero fichero);

	/**
	 * Borrar un doc asociado a un anexo.
	 *
	 * @param idDocumento
	 */
	void removeDocAnexo(Long idDocumento);

	/**
	 * Devuelve la lista de formateadores de un tramite version.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	List<FormateadorFormulario> getFormateadoresTramiteVersion(Long idTramiteVersion);

	/**
	 * Devuelve la lista de formularios de un tramite version.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	List<DisenyoFormulario> getFormulariosTramiteVersion(Long idTramiteVersion);

	/**
	 * Devuelve la lista de ficheros de un tramite version.
	 *
	 * @param idTramiteVersion
	 * @return
	 */
	List<Fichero> getFicherosTramiteVersion(Long idTramiteVersion);

	/**
	 * Intercambiar formularios.
	 *
	 * @param idFormulario1
	 * @param idFormulario2
	 */
	void intercambiarFormularios(Long idFormulario1, Long idFormulario2);

	/**
	 * Comprueba si una tasa se repite.
	 *
	 * @param idTramiteVersion
	 * @param identificador
	 * @param idTasa
	 * @return
	 */
	boolean checkTasaRepetida(Long idTramiteVersion, String identificador, Long idTasa);

	/**
	 * Comprueba si un anexo se repite.
	 *
	 * @param idTramiteVersion
	 * @param identificador
	 * @param idAnexo
	 * @return
	 */
	boolean checkAnexoRepetido(Long idTramiteVersion, String identificador, Long idAnexo);

	/**
	 * Importar un paso.
	 *
	 * @param filaTramiteVersion
	 * @param tramitePaso
	 * @param idTramite
	 * @param formularios
	 * @param ficheros
	 * @param ficherosContent
	 * @return
	 */
	Long importar(final FilaImportarTramiteVersion filaTramiteVersion, TramitePaso tramitePaso, Long idTramite,
			final Long idEntidad, final Map<Long, DisenyoFormulario> formularios, final Map<Long, Fichero> ficheros,
			final Map<Long, byte[]> ficherosContent, final Map<Long, FormateadorFormulario> idFormateadores);

}