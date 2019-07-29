package es.caib.sistrages.core.api.service.migracion;

import java.util.List;
import java.util.Map;

import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.migracion.ErrorMigracion;

/**
 * La interface MigracionService.
 */
public interface MigracionService {

	/**
	 * Obtiene la lista de tramites de Sistra
	 *
	 * @return lista de tramites
	 */
	public List<Tramite> getTramiteSistra();

	/**
	 * Obtiene la lista de versiones de Sistra
	 *
	 * @param pIdTramite
	 *            id. tramite
	 * @return lista de versiones de Sistra
	 */
	public List<TramiteVersion> getTramiteVersionSistra(Long pIdTramite);

	/**
	 * Migrar tramite version de Sistra a Sistra2.
	 *
	 * @param pIdTramiteSistra
	 *            id. tramite Sistra
	 * @param pNumVersionSistra
	 *            num. version Sistra
	 * @param pIdTramite
	 *            id. tramite Sistra2
	 * @param pNumVersion
	 *            num. version Sistra2
	 * @param pParams
	 *            parametros
	 * @return lista de errores si los hay, en caso contrario nulo
	 */
	public List<ErrorMigracion> migrarTramiteVersion(Long pIdTramiteSistra, int pNumVersionSistra, Long pIdTramite,
			int pNumVersion, Map<String, Object> pParams);
}
