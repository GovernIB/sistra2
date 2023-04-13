package es.caib.sistrahelp.frontend.procesos;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.caib.sistrahelp.core.api.service.AlertaService;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.core.api.service.HistorialAlertaService;

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

	@PostConstruct
	public void postConstruct() {
		TimerHilosAlertas tAl = new TimerHilosAlertas();
		tAl.run(aService, hService, confService, historialService);
		LocalTime ahora = LocalTime.now();
		ScheduledExecutorService scheduler2 = Executors.newSingleThreadScheduledExecutor();
		scheduler2.scheduleAtFixedRate(new TimerReinicioDiario(aService, hService, confService, historialService),
				ahora.until(LocalTime.parse("00:00:00.000000000"), ChronoUnit.SECONDS) + 86400, 86400,
				TimeUnit.SECONDS);
	}
}