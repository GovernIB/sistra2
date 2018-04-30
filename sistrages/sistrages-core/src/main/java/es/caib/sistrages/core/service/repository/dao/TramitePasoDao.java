package es.caib.sistrages.core.service.repository.dao;

import java.util.List;

import es.caib.sistrages.core.api.model.FormularioTramite;
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

}