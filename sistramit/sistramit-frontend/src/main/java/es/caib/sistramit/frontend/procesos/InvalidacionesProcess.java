package es.caib.sistramit.frontend.procesos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.service.SystemService;

/**
 * Proceso que revisa invalidaciones.
 */
@Component
public final class InvalidacionesProcess {

    /** Log. */
    private static Logger log = LoggerFactory
            .getLogger(InvalidacionesProcess.class);

    @Autowired
    private SystemService systemService;

    /**
     * Process.
     */
    @Scheduled(cron = "${procesos.invalidaciones.cron}")
    public void process() {
        log.debug("Proceso revision invalidaciones - inicio");
        systemService.revisarInvalidaciones();
        log.debug("Proceso revision invalidaciones - fin");
    }

}