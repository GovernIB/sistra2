package es.caib.sistrahelp.core.service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.model.Sesion;
import es.caib.sistrahelp.core.api.service.AlertaService;
import es.caib.sistrahelp.core.api.service.SystemService;
import es.caib.sistrahelp.core.interceptor.NegocioInterceptor;
import es.caib.sistrahelp.core.service.repository.dao.SesionDao;
import es.caib.sistrahelp.core.service.repository.dao.AlertaDao;
import es.caib.sistrahelp.core.service.repository.dao.ProcesoDao;

@Service
@Transactional
public class SystemServiceImpl implements SystemService {

	/** Log. */
	private final Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);

	/** Sesion DAO. */
	@Autowired
	SesionDao sesionDao;

	/** Procesos DAO. */
	@Autowired
	private ProcesoDao procesosDao;

	/** Alertas DAO. */
	@Autowired
	private AlertaDao alertaDao;

	@Autowired
	private AlertaService aService;

	ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine interprete = manager.getEngineByName("js");

	@PostConstruct
	public void init() {

	}

	@Override
	@NegocioInterceptor
	public Sesion getSesion(final String pUserName) {
		return sesionDao.getByUser(pUserName);
	}

	@Override
	@NegocioInterceptor
	public void updateSesionPropiedades(final String pUserName, final String pPropiedades) {
		sesionDao.updatePropiedades(pUserName, pPropiedades);
	}

	@Override
	@PermitAll
	@NegocioInterceptor
	public boolean verificarMaestro(final String instancia) {
		return procesosDao.verificarMaestro(instancia);
	}

	@Override
	@NegocioInterceptor
    public List<Alerta> calcularAlertasEjecucion() {
		final List<Alerta> alertasActivas = aService.listAlertaActivo(null, true);
		final List<Alerta> result = new ArrayList<>();

		for (final Alerta a : alertasActivas) {
			Date ahora = new Date();
			long ahoraMinutos = ahora.getTime() / 60000;
			long ultimaVerificacionMinutos = 0;
			if (a.getFecha() == null) {
				ultimaVerificacionMinutos = ahoraMinutos;
			} else {
				ultimaVerificacionMinutos = a.getFecha().getTime() / 60000;
			}
			long distanciaFechas = ahoraMinutos - ultimaVerificacionMinutos;
			if (!a.getNombre().equals("RESUMEN_DIARIO") && !a.getNombre().equals("RESUM_DIARI")) {
				Long intervaloAlertaMinutos = a.getPeriodoEvaluacion().longValue() / 60;
				boolean tocaEjecutar = distanciaFechas >= intervaloAlertaMinutos;
				log.debug("ALERTAS STH: tocaEjecutar? La condición " + (ahoraMinutos - ultimaVerificacionMinutos) + " >= " + intervaloAlertaMinutos + " es " + tocaEjecutar);
				if (!a.isEliminar() && a.isActivo() && tocaEjecutar
						&& LocalTime.now().until(
								LocalTime.parse(a.getIntervaloEvaluacion().split("-")[1] + ":59.999999999"),
								ChronoUnit.SECONDS) >= 0
						&& LocalTime.now().until(
								LocalTime.parse(a.getIntervaloEvaluacion().split("-")[0] + ":00.000000000"),
								ChronoUnit.SECONDS) < 0) {

					result.add(a);
				}
			} else {
				LocalTime horaEnvio = null;
				if (a.getHoraResumen().equals("00:00")) {
					horaEnvio = LocalTime.parse("00:01");
				} else {
					horaEnvio = LocalTime.parse(a.getHoraResumen());
				}
				Date ahoraEnvio = ahora;
				ahoraEnvio.setHours(horaEnvio.getHour());
				ahoraEnvio.setMinutes(horaEnvio.getMinute());
				long ahoraEnvioMinutos = ahoraEnvio.getTime() / 60000;
				long distanciaAhoraEnvio = ahoraMinutos - ahoraEnvioMinutos;
				if (a.isActivo() && distanciaAhoraEnvio >= 0 && (a.getFecha() == null ? distanciaFechas == 0 : ahora.getDate() != a.getFecha().getDate())) {
					result.add(a);
				}
			}
		}

		return result;
	}

	@Override
	@NegocioInterceptor
	public void actualizarFechaAcceso(final String pUserName) {
		sesionDao.updateFechaAcceso(pUserName);
	}
}
