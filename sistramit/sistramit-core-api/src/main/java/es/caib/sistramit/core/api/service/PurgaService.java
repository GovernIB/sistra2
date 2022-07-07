package es.caib.sistramit.core.api.service;

import es.caib.sistramit.core.api.model.comun.ResultadoProcesoProgramado;

/**
 * Servicio purga.
 *
 * @author Indra
 *
 */
public interface PurgaService {

	/** Realiza proceso programado de purga. */
	ResultadoProcesoProgramado purgarPersistencia();

	/** Realiza purga flujos tramitación. */
	void purgarFlujosTramitacion();

}
