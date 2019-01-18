package es.caib.sistramit.frontend.procesos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.service.PurgaService;

/**
 * Proceso que arranca proceso purga flujos tramitación. Debe ejecutarse en
 * todos los nodos.
 */
@Component
public final class PurgaFlujoProcess {

	/** Log. */
	private static Logger log = LoggerFactory.getLogger(PurgaFlujoProcess.class);

	@Autowired
	private PurgaService purgaService;

	/**
	 * Process (cada 5 min).
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void process() {
		log.debug("Proceso purgar flujos tramitación");
		purgaService.purgarFlujosTramitacion();
	}

}