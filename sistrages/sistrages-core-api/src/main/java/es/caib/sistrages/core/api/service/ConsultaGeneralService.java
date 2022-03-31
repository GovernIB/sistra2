package es.caib.sistrages.core.api.service;

import java.util.List;

import es.caib.sistrages.core.api.model.ConsultaGeneral;
import es.caib.sistrages.core.api.model.types.TypeIdioma;

/**
 * La interface ConsultaGeneralService.
 */
public interface ConsultaGeneralService {

	/**
	 * Listar consulta general .
	 * @param filtro
	 * @param typeIdioma
	 * @param idEntidad
	 * @param idArea
	 * @param checkAmbitoGlobal
	 * @param checkAmbitoEntidad
	 * @param checkAmbitoArea
	 * @param checkDominios
	 * @param checkConfAut
	 * @param checkGFE
	 * @return
	 */
	public List<ConsultaGeneral> listar(String filtro, TypeIdioma typeIdioma, Long idEntidad, Long idArea, boolean checkAmbitoGlobal, boolean checkAmbitoEntidad, boolean checkAmbitoArea, boolean checkDominios, boolean checkConfAut, boolean checkGFE );

}
