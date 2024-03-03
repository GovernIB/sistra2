package es.caib.sistrahelp.frontend.procesos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

		//Se calcula la distancia hasta las 4 de la madrugada (1440 es 24 horas = 60 minutos x 24)
		Long madrugada=LocalDateTime.now().until(LocalDate.now().plusDays(1).atStartOfDay().plusHours(4), ChronoUnit.MINUTES);
		LOGGER.debug("ALERTAS STH: Entra en TimerIniciar. Tiempo hasta las 4 de la madrugada " + madrugada);

		scheduler2.scheduleAtFixedRate(new TimerReinicioDiario(aService, hService, confService, historialService),
				madrugada, 1440, TimeUnit.MINUTES);

		LOGGER.debug("ALERTAS STH: TimerIniciar postConstruct");
	}

}