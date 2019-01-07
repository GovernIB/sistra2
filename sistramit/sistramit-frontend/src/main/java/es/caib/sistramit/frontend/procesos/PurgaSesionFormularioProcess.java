package es.caib.sistramit.frontend.procesos;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.caib.sistramit.core.api.service.GestorFormulariosInternoService;

/**
 * Proceso que arranca proceso purga flujos tramitación. Debe ejecutarse en
 * todos los nodos.
 */
@Component
public final class PurgaSesionFormularioProcess {

    /** Log. */
    private static Logger log = LoggerFactory
            .getLogger(PurgaSesionFormularioProcess.class);

    @Autowired
    private GestorFormulariosInternoService gestorFormulariosInternoService;

    @Autowired
    private ServletContext servletContext;

    /**
     * Process (cada 5 min).
     */
    // TODO PENDIENTE ACTIVAR
    // @Scheduled(cron = "0 0/5 * * * ?")
    public void process() {
        log.debug("Proceso purgar sesiones formulario");
        gestorFormulariosInternoService.purgar();
    }

}