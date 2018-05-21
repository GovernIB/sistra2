package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.HistorialVersion;
import es.caib.sistrages.core.api.model.types.TypeAccion;

/**
 * La interface HistorialVersionDao.
 */
public interface HistorialVersionDao {

	/**
	 * Obtiene el historial version.
	 *
	 * @param id
	 *            identificador
	 * @return el valor del tramite
	 */
	HistorialVersion getById(final Long id);

	/**
	 * Obtiene el valor de todos los historial .
	 *
	 * @param idTramiteVersion
	 *            Id tramite version
	 *
	 * @return el valor de todos los historiales
	 */
	List<HistorialVersion> getAll(final Long idTramiteVersion);

	/**
	 * Obtiene todos los historial version con filtro.
	 *
	 * @param idTramiteVersion
	 * @param pFiltro
	 * @return
	 */
	List<HistorialVersion> getAllByFiltro(Long idTramiteVersion, String pFiltro);

	/**
	 * Añade tramite.
	 *
	 * @param idTramiteVersion
	 *            idTramiteVersion
	 * @param username
	 *            Username
	 * @param accion
	 *            Accion
	 * @param detalleCambio
	 *            Detalle del cambio
	 */
	void add(Long idTramiteVersion, final String username, final TypeAccion accion, final String detalleCambio);

}