package es.caib.sistrages.frontend.procesos;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.frontend.model.comun.Constantes;

/**
 * Proceso que arranca proceso purga ficheros.
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
     * Process (cada 5 min).
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void process() {
        log.debug("Proceso purgarFicheros");
        final String instancia = getIdServletContext();
        if (StringUtils.isNotBlank(instancia)) {
            // Control maestro/esclavo procesos
            if (systemService.verificarMaestro(instancia)) {
                log.debug("Es maestro. Lanza purga ficheros");
                systemService.purgarFicheros();
            } else {
                log.debug("No es maestro. No lanza purga ficheros");
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