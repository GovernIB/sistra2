package es.caib.sistrahelp.frontend.procesos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrahelp.core.api.service.AlertaService;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.core.api.service.HistorialAlertaService;
import es.caib.sistrahelp.frontend.util.UtilJSF;

@Component
public class TimerIniciar {

	@Autowired
	private AlertaService aService;

	@Autowired
	private HelpDeskService hService;

	@Autowired
	private ConfiguracionService confService;

	@Autowired
	private HistorialAlertaService historialService;

	/**
	 * Log.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TimerIniciar.class);


	@PostConstruct
	public void postConstruct() {
		TimerHilosAlertas tAl = new TimerHilosAlertas();
		tAl.run(aService, hService, confService, historialService);
		ScheduledExecutorService scheduler2 = Executors.newSingleThreadScheduledExecutor();

		//Se calcula la distancia hasta la media noche (1440 es 24 horas = 60 minutos x 24)
		Long medianoche=LocalDateTime.now().until(LocalDate.now().plusDays(1).atStartOfDay(), ChronoUnit.MINUTES);

		LOGGER.debug("Tiempo hasta media noche " + medianoche);

		/*scheduler2.scheduleAtFixedRate(new TimerReinicioDiario(aService, hService, confService, historialService),
				1, 60, TimeUnit.MINUTES);*/
		scheduler2.scheduleAtFixedRate(new TimerReinicioDiario(aService, hService, confService, historialService),
				medianoche, 1440, TimeUnit.MINUTES);

		LOGGER.debug("TimerIniciar postConstruct");
	}

}