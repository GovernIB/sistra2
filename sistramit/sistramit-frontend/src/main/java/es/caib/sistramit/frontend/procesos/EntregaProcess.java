package es.caib.sistramit.frontend.procesos;

import es.caib.sistramit.core.api.service.EntregaService;
import es.caib.sistramit.core.api.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * Proceso que realiza entrega trámites finalizados. Debe ejecutarse en nodo maestro.
 */
@Component
public final class EntregaProcess {

	/** Log. */
	private static Logger log = LoggerFactory.getLogger(EntregaProcess.class);

	/** Entrega service. */
	@Autowired
	private EntregaService entregaService;

	/** Servlet context. */
	@Autowired
	private ServletContext servletContext;

	/** System service. */
	@Autowired
	private SystemService systemService;

	/**
	 * Envío remoto: avisos inmediantos. Cada 1 minutos
	 */
	@Scheduled(cron = "${procesos.entregaTramitesFinalizados.inmediatos.cron}")
	public void processInmediatos() {
		log.debug("Proceso entrega finalizados [inmediato]- inicio");
		if (UtilProcess.checkMaestro("entrega finalizados [inmediato]", servletContext, systemService)) {
			entregaService.procesarEnvioRemotoFinalizadosInmediatos();
		}
		log.debug("Proceso entrega finalizados [inmediato] - fin");
	}

	/**
	 * Envío remoto: avisos periódicos. Aprox cada 60 min
	 */
	@Scheduled(cron = "${procesos.entregaTramitesFinalizados.periodicos.cron}")
	public void processPeriodicos() {
		log.debug("Proceso entrega finalizados [inmediato] - inicio");
		if (UtilProcess.checkMaestro("entrega finalizados [inmediato]", servletContext, systemService)) {
			entregaService.procesarEnvioRemotoFinalizadosPeriodicos();
		}
		log.debug("Proceso entrega finalizados [inmediato] - fin");
	}

	/**
	 * Funcionario habilitado: aviso trámites finalizados. Cada 5 minutos
	 */
	@Scheduled(cron = "${procesos.funcionarioHabilitadoTramitesFinalizados.cron}")
	public void processFuncionarioHabilitado() {
		log.debug("Proceso funcionario habilitado finalizados - inicio");
		if (UtilProcess.checkMaestro("funcionario habilitado finalizados", servletContext, systemService)) {
			entregaService.procesarFuncionarioHabilitadoFinalizados();
		}
		log.debug("Proceso funcionario habilitado finalizados - fin");
	}

}