package es.caib.sistrahelp.core.api.service;

import es.caib.sistrahelp.core.api.model.Alerta;

/**
 * Dominio service.
 *
 * @author Indra.
 *
 */
public interface ProcesoAlertaService {

	/**
	 * Procesa las alertas
	 */
	boolean procesarAlertas(Alerta al);

}