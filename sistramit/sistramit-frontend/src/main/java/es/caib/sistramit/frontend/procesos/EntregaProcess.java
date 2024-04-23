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
	 * Process. Cada 1 minutos
	 */
	@Scheduled(cron = "${procesos.entregaTramitesFinalizados.inmediatos.cron}")
	public void processInmediatos() {
		log.debug("Proceso entrega finalizados [inmediato]- inicio");
		if (UtilProcess.checkMaestro("entrega finalizados [inmediato]", servletContext, systemService)) {
			entregaService.procesarEntregaFinalizadosInmediatos();
		}
		log.debug("Proceso entrega finalizados [inmediato] - fin");
	}

	/**
	 * Process. Aprox cada 60 min
	 */
	@Scheduled(cron = "${procesos.entregaTramitesFinalizados.periodicos.cron}")
	public void process() {
		log.debug("Proceso entrega finalizados [inmediato] - inicio");
		if (UtilProcess.checkMaestro("entrega finalizados [inmediato]", servletContext, systemService)) {
			entregaService.procesarEntregaFinalizadosPeriodicos();
		}
		log.debug("Proceso entrega finalizados [inmediato] - fin");
	}

}