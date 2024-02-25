package es.caib.sistrahelp.frontend.procesos;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.service.AlertaService;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.core.api.service.HistorialAlertaService;

public class TimerHilosAlertas {

	private static final Logger LOGGER = LoggerFactory.getLogger(TimerHilosAlertas.class);

	public void run(AlertaService aService, HelpDeskService hService, ConfiguracionService confService,
			HistorialAlertaService histAvis) {

		ScheduledExecutorService scheduler;
		if (aService != null) {
			LOGGER.debug("ALERTAS STH: Entra a TimerHilosAlertas.run");
			List<Alerta> alertas = aService.listAlertaActivo(null, true);
			BasicThreadFactory factory = new BasicThreadFactory.Builder().namingPattern("evaluarHilo%d").build();
			scheduler = Executors.newScheduledThreadPool(alertas.size(), factory);
			LocalTime ahora = LocalTime.now();
			for (Alerta al : alertas) {
				if (!al.getNombre().equals("RESUMEN_DIARIO")) {
					LOGGER.debug("ALERTAS STH: Entra a TimerHilosAlertas.run, alerta: " + al.getNombre());
					String[] horas = al.getIntervaloEvaluacion().split("-");
					Boolean targetInZone = (ahora.isAfter(LocalTime.parse(horas[0] + ":00.000000000"))
							&& ahora.isBefore(LocalTime.parse(horas[1] + ":59.999999999")));
					if (targetInZone) {
						final ScheduledFuture<?> promise = scheduler.schedule(
								new TimerEvaluarAlertas(al.getCodigo(), hService, confService, histAvis, aService), 0,
								TimeUnit.SECONDS);
						final ScheduledFuture<Boolean> canceller = scheduler.schedule(() -> promise.cancel(false),
								ahora.until(LocalTime.parse(horas[1] + ":59.999999999"), ChronoUnit.SECONDS),
								TimeUnit.SECONDS);
						if (ahora.until(LocalTime.parse("23:59:59.999999999"), ChronoUnit.SECONDS) > 0) {
							final ScheduledFuture<Boolean> cancellerDia = scheduler.schedule(
									() -> promise.cancel(false),
									ahora.until(LocalTime.parse("23:59:59.999999999"), ChronoUnit.SECONDS),
									TimeUnit.SECONDS);
						} else {
							final ScheduledFuture<Boolean> cancellerDia = scheduler.schedule(
									() -> promise.cancel(false),
									ahora.until(LocalTime.parse("23:59:59.999999999"), ChronoUnit.SECONDS) + 86400,
									TimeUnit.SECONDS);
						}
					} else if (ahora.isBefore(LocalTime.parse(horas[0] + ":00.000000000"))) {
						final ScheduledFuture<?> promise = scheduler.schedule(
								new TimerEvaluarAlertas(al.getCodigo(), hService, confService, histAvis, aService),
								ahora.until(LocalTime.parse(horas[0] + ":00.000000000"), ChronoUnit.SECONDS),
								TimeUnit.SECONDS);
						final ScheduledFuture<Boolean> canceller = scheduler.schedule(() -> promise.cancel(false),
								ahora.until(LocalTime.parse(horas[1] + ":59.999999999"), ChronoUnit.SECONDS),
								TimeUnit.SECONDS);
						if (ahora.until(LocalTime.parse("23:59:59.999999999"), ChronoUnit.SECONDS) > 0) {
							final ScheduledFuture<Boolean> cancellerDia = scheduler.schedule(
									() -> promise.cancel(false),
									ahora.until(LocalTime.parse("23:59:59.999999999"), ChronoUnit.SECONDS),
									TimeUnit.SECONDS);
						} else {
							final ScheduledFuture<Boolean> cancellerDia = scheduler.schedule(
									() -> promise.cancel(false),
									ahora.until(LocalTime.parse("23:59:59.999999999"), ChronoUnit.SECONDS) + 86400,
									TimeUnit.SECONDS);
						}
					}
				}
			}
		}
	}
}