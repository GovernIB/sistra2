package es.caib.sistramit.frontend.procesos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.service.SystemService;

import javax.servlet.ServletContext;

/**
 * Proceso que realiza envios avisos. Debe ejecutarse en nodo maestro.
 */
@Component
public final class EnvioAvisoProcess {

	/** Log. */
	private static Logger log = LoggerFactory.getLogger(EnvioAvisoProcess.class);

	@Autowired
	private SystemService systemService;

	@Autowired
	private ServletContext servletContext;

	/**
	 * Process. Cada 1 minutos
	 */
	@Scheduled(cron = "${procesos.envios.inmediatos.cron}")
	public void processInmediatos() {
		log.debug("Proceso envio avisos - inicio");
		if (UtilProcess.checkMaestro("envio avisos", servletContext, systemService)) {
			systemService.procesarEnviosInmediatos();
		}
		log.debug("Proceso envio avisos - fin");
	}

	/**
	 * Process. Aprox cada 60 min
	 */
	@Scheduled(cron = "${procesos.envios.reintentos.cron}")
	public void process() {
		log.debug("Proceso envio avisos  reintento - inicio");
		if (UtilProcess.checkMaestro("envio avisos reintento", servletContext, systemService)) {
			systemService.procesarEnviosReintentos();
		}
		log.debug("Proceso envio avisos reintento - fin");
	}

}