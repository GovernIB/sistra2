package es.caib.sistramit.core.api.service;

import es.caib.sistramit.core.api.model.comun.ResultadoProcesoProgramado;

/**
 * Servicio entrega tras finalizar trámites: envío remoto (CES2), funcianario habilitado (RFHAB)...
 *
 * @author Indra
 *
 */
public interface EntregaService {

	/**
	 * Envío remoto: procesa la entrega de trámites finalizados (procesado inmediato).
	 */
	ResultadoProcesoProgramado procesarEnvioRemotoFinalizadosInmediatos();

	/**
	 * Envío remoto: procesa la entrega de trámites finalizados (procesado periódico).
	 */
	ResultadoProcesoProgramado procesarEnvioRemotoFinalizadosPeriodicos();

	/**
	 * Funcionario habilitado: procesa la entrega de trámites finalizados.
	 */
	ResultadoProcesoProgramado procesarFuncionarioHabilitadoFinalizados();

}
