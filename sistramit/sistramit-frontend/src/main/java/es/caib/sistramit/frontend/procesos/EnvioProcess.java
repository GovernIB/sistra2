package es.caib.sistramit.frontend.procesos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.service.SystemService;

/**
 * Proceso que revisa invalidaciones. Debe ejecutarse en todos los nodos.
 */
@Component
public final class EnvioProcess {

	/** Log. */
	private static Logger log = LoggerFactory.getLogger(EnvioProcess.class);

	@Autowired
	private SystemService systemService;

	/**
	 * Process. Cada 1 minutos
	 */
	@Scheduled(cron = "${procesos.envios.inmediatos.cron}")
	public void processInmediatos() {

		log.debug("Proceso envio - inicio");
		systemService.procesarEnviosInmediatos();
		log.debug("Proceso envio - fin");
	}

	/**
	 * Process. Aprox cada 60 min
	 */
	@Scheduled(cron = "${procesos.envios.reintentos.cron}")
	public void process() {

		log.debug("Proceso envio - inicio");
		systemService.procesarEnviosReintentos();
		log.debug("Proceso envio - fin");
	}

}