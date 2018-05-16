package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Fichero;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.TramitePaso;

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
	 * @param idFormularioInterno
	 */
	void addFormularioTramite(FormularioTramite formularioTramite, Long idTramitePaso, Long idFormularioInterno);

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
	void addDocumentoTramite(Documento documento, Long idTramitePaso);

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
	 * @return
	 */
	void addTasaTramite(Tasa tasa, Long idTramitePaso);

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

}