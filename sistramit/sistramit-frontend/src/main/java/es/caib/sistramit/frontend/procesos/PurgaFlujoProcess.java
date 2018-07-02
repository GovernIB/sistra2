package es.caib.sistramit.frontend.procesos;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistramit.core.api.model.comun.Constantes;
import es.caib.sistramit.core.api.service.FlujoTramitacionService;

/**
 * Proceso que arranca proceso purga flujos tramitación.
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

    /**
     * Obtiene id instancia.
     *
     * @return id instancia
     */
    private String getIdServletContext() {
        String id = null;
        if (servletContext != null) {
            id = (String) servletContext
                    .getAttribute(Constantes.SERVLET_CONTEXT_ID);
            if (id == null) {
                id = GeneradorId.generarId();
                servletContext.setAttribute(Constantes.SERVLET_CONTEXT_ID, id);
            }
        }
        return id;
    }

}