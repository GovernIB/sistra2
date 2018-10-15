package es.caib.sistramit.frontend.procesos;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.service.FlujoTramitacionService;

/**
 * Proceso que arranca proceso purga flujos tramitación. Debe ejecutarse en
 * todos los nodos.
 */
@Component
public final class PurgaFlujoProcess {

    /** Log. */
    private static Logger log = LoggerFactory
            .getLogger(PurgaFlujoProcess.class);

    @Autowired
    private FlujoTramitacionService flujoTramitacionService;

    @Autowired
    private ServletContext servletContext;

    /**
     * Process (cada 5 min).
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void process() {
        log.debug("Proceso purgar flujos tramitación");
        flujoTramitacionService.purgar();
    }

}