package es.caib.sistramit.core.api.service;

import es.caib.sistramit.core.api.model.comun.ResultadoProcesoProgramado;

/**
 * Servicio entrega (CES2).
 *
 * @author Indra
 *
 */
public interface EntregaService {

	/**
	 * Procesa la entrega de trámites finalizados (procesado inmediato).
	 */
	ResultadoProcesoProgramado procesarEntregaFinalizadosInmediatos();

	/**
	 * Procesa la entrega de trámites finalizados (procesado periódico).
	 */
	ResultadoProcesoProgramado procesarEntregaFinalizadosPeriodicos();

}
