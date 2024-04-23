package es.caib.sistrahelp.frontend.procesos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.caib.sistrahelp.core.api.service.SystemService;
import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.service.ProcesoAlertaService;

import java.util.List;

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

	@Autowired
	private ProcesoAlertaService procesoAlertaService;

	/**
	 * Process. Cada 10 minutos
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void procesar() {
		log.debug("Proceso envio avisos - inicio");
		if (UtilProcess.checkMaestro("envio avisos", servletContext, systemService)) {
			List<Alerta> alertas = systemService.calcularAlertasEjecucion();
			for (final Alerta a : alertas) {
                try {
                	log.debug("Lanza alerta " + a.toString());
                	procesoAlertaService.procesarAlertas(a);
                } catch (final Exception ex) {
                	log.error("Error al lanzar alerta " + a.toString() + ": " + ex.getMessage());
                }
            }
		}
		log.debug("Proceso envio avisos - fin");
	}

}