package es.caib.sistramit.frontend.procesos;

import javax.servlet.ServletContext;

import es.caib.sistramit.core.api.service.EntregaService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistramit.core.api.model.comun.Constantes;
import es.caib.sistramit.core.api.service.PurgaService;
import es.caib.sistramit.core.api.service.SystemService;

/**
 * Proceso que arranca proceso purga persistencia. Debe ejecutarse en el nodo
 * maestro.
 */
@Component
public final class PurgaPersistenciaProcess {

	/** Log. */
	private static Logger log = LoggerFactory.getLogger(PurgaPersistenciaProcess.class);

	@Autowired
	private SystemService systemService;

	@Autowired
	private PurgaService purgaService;

	@Autowired
	private ServletContext servletContext;

	/**
	 * Process.
	 */
	@Scheduled(cron = "${procesos.purga.cron}")
	public void process() {
		log.debug("Proceso purga persistencia - inicio");
		if (UtilProcess.checkMaestro("purga persistencia", servletContext, systemService)) {
			purgaService.purgarPersistencia();
		}
		log.debug("Proceso purga persistencia - inicio");
	}


}