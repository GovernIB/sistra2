package es.caib.sistramit.frontend.procesos;

import es.caib.sistra2.commons.utils.GeneradorId;
import es.caib.sistramit.core.api.model.comun.Constantes;
import es.caib.sistramit.core.api.service.SystemService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;

/**
 * Utilidades para procesos.
 */
public class UtilProcess {

    /** Log. */
    private static Logger log = LoggerFactory.getLogger(UtilProcess.class);

    public static boolean checkMaestro(String proceso, ServletContext servletContext, SystemService systemService) {
        boolean res = false;
        String instancia = getIdInstancia(servletContext);
        if (StringUtils.isNotBlank(instancia)) {
            if (systemService.verificarMaestro(instancia)) {
                log.debug("Es maestro, lanza proceso " + proceso);
                res = true;
            } else {
                log.debug("No es maestro, no lanza proceso " + proceso);
            }
        } else {
            log.warn("No se ha podido obtener id instancia. No se lanza proceso " + proceso);
        }
        return res;
    }

    /**
     * Obtiene id instancia.
     *
     * @return id instancia
     */
    private static String getIdInstancia(ServletContext servletContext) {
        String id = null;
        if (servletContext != null) {
            id = (String) servletContext.getAttribute(Constantes.SERVLET_CONTEXT_ID);
            if (id == null) {
                id = GeneradorId.generarId();
                servletContext.setAttribute(Constantes.SERVLET_CONTEXT_ID, id);
            }
        }
        return id;
    }


}
