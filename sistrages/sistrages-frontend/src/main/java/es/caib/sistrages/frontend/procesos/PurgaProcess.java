package es.caib.sistrages.frontend.procesos;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Proceso que arranca proceso purga ficheros.
 */
@Component
public final class PurgaProcess {

	/** Log. */
	private static Logger log = LoggerFactory.getLogger(PurgaProcess.class);

	@Autowired
	private SystemService systemService;

	/**
	 * Process (cada 5 min).
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void process() {
		log.debug("Proceso purgarFicheros");
		final String instancia = UtilJSF.getIdServletContext();
		if (StringUtils.isNotBlank(instancia)) {
			log.debug("Lanza purgarFicheros con id instancia: " + instancia);
			systemService.purgarFicheros(instancia);
		} else {
			log.warn("No se ha podido obtener id instancia. Puede ser porque este arrancando servidor");
		}
	}

}