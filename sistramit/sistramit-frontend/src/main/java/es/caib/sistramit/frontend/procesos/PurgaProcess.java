package es.caib.sistramit.frontend.procesos;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistramit.core.api.model.comun.Constantes;
import es.caib.sistramit.core.api.service.SystemService;

/**
 * Proceso que arranca proceso purga ficheros. Debe ejecutarse en el nodo
 * maestro.
 */
@Component
public final class PurgaProcess {

    /** Log. */
    private static Logger log = LoggerFactory.getLogger(PurgaProcess.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private ServletContext servletContext;

    /**
     * Process.
     */
    @Scheduled(cron = "${procesos.purga.cron}")
    public void process() {
        log.debug("Proceso purgarFicheros");
        final String instancia = getIdServletContext();
        if (StringUtils.isNotBlank(instancia)) {
            if (systemService.verificarMaestro(instancia)) {
                log.debug("Es maestro, lanza proceso purga");
                systemService.purgar();
            } else {
                log.debug("No es maestro, no lanza proceso purga");
            }
        } else {
            log.warn("No se ha podido obtener id instancia.");
        }
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