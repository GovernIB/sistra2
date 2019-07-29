package es.caib.sistrages.core.service.repository.dao.migracion;

import java.util.List;

import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.migracion.DocumSistra;
import es.caib.sistrages.core.api.model.migracion.FormulSistra;
import es.caib.sistrages.core.api.model.migracion.TraverSistra;

/**
 * La interface del dao de Migracion.
 */
public interface MigracionDao {

	/**
	 * Obtiene la lista de todos los tramites de Sistra.
	 *
	 * @return lista de tramites
	 */
	public List<Tramite> getTramiteSistra();

	/**
	 * Obtiene la lista de las versiones de tramites de Sistra.
	 *
	 * @param pIdTramite
	 *            id. tramite
	 * @return lista de versiones de tramites
	 */
	public List<TramiteVersion> getTramiteVersionSistra(Long pIdTramite);

	/**
	 * Obtiene el codigo de la version del tramite
	 *
	 * @param pIdTramite
	 *            id. tramite
	 * @param pNumVersion
	 *            num. version
	 * @return codigo de la version del tramite
	 */
	public Long getCodigoTramiteVersionSistra(Long pIdTramite, int pNumVersion);

	/**
	 * Obtiene la lista de documentos de Sistra
	 *
	 * @param pIdTramiteVersion
	 *            id. tramiteversion
	 * @return lista de documentos
	 */
	List<DocumSistra> getDocumSistra(Long pIdTramiteVersion);

	/**
	 * Obtiene el formulario de un documento
	 *
	 * @param pDocSistra
	 *            doc. sistra
	 * @return formulario
	 */
	FormulSistra getFormSistra(DocumSistra pDocSistra);

	/**
	 * Obtiene el tramiteVersion de Sistra.
	 *
	 * @param pIdTramite
	 *            id. tramite
	 * @param pNumVersion
	 *            num version
	 * @return tramiteVersion
	 */
	TraverSistra getTramiteVersionSistra(Long pIdTramite, int pNumVersion);
}