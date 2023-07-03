package es.caib.sistrahelp.frontend.procesos;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.digester.plugins.PluginException;

import es.caib.sistra2.commons.plugins.email.api.EmailPluginException;
import es.caib.sistra2.commons.plugins.email.api.IEmailPlugin;
import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.model.Entidad;
import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.HistorialAlerta;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypePluginGlobal;
import es.caib.sistrahelp.core.api.service.AlertaService;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.core.api.service.HistorialAlertaService;
import es.caib.sistrahelp.frontend.util.UtilJSF;

public class TimerEvaluarAlertas implements Runnable {

	private Long alCod;
	private HelpDeskService hService;
	private ConfiguracionService confServ;
	private HistorialAlertaService historialService;
	private AlertaService aService;
	private boolean parar;

	public TimerEvaluarAlertas(Long alCod, HelpDeskService hService, ConfiguracionService confServ,
			HistorialAlertaService historialService, AlertaService aService) {
		this.alCod = alCod;
		this.hService = hService;
		this.confServ = confServ;
		this.historialService = historialService;
		this.aService = aService;
		this.parar = false;
	}

	@Override
	public void run() {
		while (!parar) {
			Alerta al = aService.loadAlerta(alCod);
			if (al != null && !al.isEliminar()
					&& LocalTime.now().until(
							LocalTime.parse(al.getIntervaloEvaluacion().split("-")[1] + ":59.999999999"),
							ChronoUnit.SECONDS) >= 0
					&& LocalTime.now().until(
							LocalTime.parse(al.getIntervaloEvaluacion().split("-")[0] + ":00.000000000"),
							ChronoUnit.SECONDS) < 0) {

				List<String> eventos = al.getEventos();
				String[] partes;
				FiltroAuditoriaTramitacion faut = new FiltroAuditoriaTramitacion(al.getListaAreas(), false, false);
				for (String evento : eventos) {
					partes = evento.split(":");
					switch (partes[1]) {
					case "<":
						faut.setEvento(TypeEvento.fromString(partes[0]));
						if (hService.countAuditoriaEvento(faut) < Long.parseLong(partes[2])) {
							System.out
									.println("Evento: " + partes[0] + " Cont: " + hService.countAuditoriaEvento(faut));
							enviarEmail(partes[0], partes[1], partes[2], faut);
							anadirHistorial(partes[0] + partes[1] + partes[2]);
						}
						break;
					case ">":
						faut.setEvento(TypeEvento.fromString(partes[0]));
						if (hService.countAuditoriaEvento(faut) > Long.parseLong(partes[2])) {
							System.out
									.println("Evento: " + partes[0] + " Cont: " + hService.countAuditoriaEvento(faut));
							enviarEmail(partes[0], partes[1], partes[2], faut);
							anadirHistorial(partes[0] + partes[1] + partes[2]);
						}
						break;
					case "=":
						faut.setEvento(TypeEvento.fromString(partes[0]));
						if (hService.countAuditoriaEvento(faut) == Long.parseLong(partes[2])) {
							System.out
									.println("Evento: " + partes[0] + " Cont: " + hService.countAuditoriaEvento(faut));
							enviarEmail(partes[0], partes[1], partes[2], faut);
							anadirHistorial(partes[0] + partes[1] + partes[2]);
						}
						break;
					}
				}
				try {
					TimeUnit.SECONDS.sleep(al.getPeriodoEvaluacion());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (LocalTime.now().until(
					LocalTime.parse(al.getIntervaloEvaluacion().split("-")[0] + ":00.000000000"),
					ChronoUnit.SECONDS) >= 0) {
				try {
					TimeUnit.SECONDS.sleep(LocalTime.now().until(
							LocalTime.parse(al.getIntervaloEvaluacion().split("-")[0] + ":00.000000000"),
							ChronoUnit.SECONDS));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				stop();
			}
		}
	}

	public void stop() {
		System.out.println("PARA");
		this.parar = true;
	}

	private void anadirHistorial(String evento) {
		HistorialAlerta hA = new HistorialAlerta();
		hA.setAlerta(aService.loadAlerta(alCod));
		hA.setEvento(evento);
		hA.setFecha(new Date());
		historialService.addHistorialAlerta(hA);
	}

	private void enviarEmail(String ev, String simbolo, String veces, FiltroAuditoriaTramitacion faut) {

		try {
			Alerta alert = aService.loadAlerta(alCod);
			String nombre = "";
			String logo = "";
			if (alert.getListaAreas() != null && !alert.getListaAreas().isEmpty()
					&& alert.getListaAreas().get(0) != null) {
				Entidad entidad = confServ.obtenerDatosEntidadByArea(alert.getListaAreas().get(0));
				logo = hService.urlLogoEntidad(entidad.getCodigoDIR3());
				if (entidad.getNombre() != null) {
					nombre = entidad.getNombre().getTraduccion("ca");
				}
			}
			final IEmailPlugin plgEmail = (IEmailPlugin) confServ.obtenerPluginGlobal(TypePluginGlobal.EMAIL);
			plgEmail.envioEmail(aService.loadAlerta(alCod).getEmail(),
					"SISTRAHELP: AVÍS - " + aService.loadAlerta(alCod).getNombre() + " - "
							+ UtilJSF.getEntorno().toUpperCase(),
					"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ca\" lang=\"ca\">\r\n" + "\r\n"
							+ "<head>\r\n" + "\r\n"
							+ "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n"
							+ "	<title>" + nombre + "</title>\r\n" + "\r\n" + "	<!-- css -->\r\n"
							+ "	<style type=\"text/css\">\r\n"
							+ "		#contenidor { width:90%; font:normal 80% 'TrebuchetMS', 'Trebuchet MS', Arial, Helvetica, sans-serif; color:#000; margin:1em auto; background-color:#fff; }\r\n"
							+ "		#cap { font-size:1.2em; font-weight:bold; text-align:center; margin-bottom:1em; }\r\n"
							+ "		#continguts { padding:1em 2em; border:1em solid #f2f2f2; }\r\n"
							+ "		#continguts h1 { font-size:1.4em; margin-top:0; margin-bottom:1em; }\r\n"
							+ "		#continguts table { margin-bottom:1.5em; border:0; empty-cells:hide; border-collapse:collapse; }\r\n"
							+ "		#continguts table th { float:left; width:10em; font-size:1.1em; font-variant:small-caps; font-weight:normal; text-align:right; padding-right:.8em; }\r\n"
							+ "		#continguts table td { font-weight:bold; padding-bottom:.5em; }\r\n"
							+ "		#continguts h2 { font-size:1.1em; margin:.8em 0; }\r\n"
							+ "		#continguts p { margin:.8em 0; }\r\n"
							+ "		#contenidor p.peu { margin:1.5em 0; padding:1em; border:1px solid #ccc; }\r\n"
							+ "		#contenidor div.accedir { padding:1em; background-color:#f7f7f7; }\r\n"
							+ "		#contenidor a.accedirCertificado { display:block; font-size:1.5em; text-align:center; padding:1em; background-color:#f7f7f7; }\r\n"
							+ "		#contenidor a.accedirClave { display:block; font-size:1.5em; text-align:center; padding:1em; background-color:#f7f7f7; }\r\n"
							+ "		#contenidor p.auto { margin:1.5em 0; padding:1em;  font-size:0.9em; font-style: italic;}\r\n"
							+ "	</style>\r\n" + "	<!-- /css -->\r\n" + "\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n"
							+ "\r\n" + "	<!-- contenidor -->\r\n" + "	<div id=\"contenidor\">\r\n" + "\r\n"
							+ "		<!-- logo illes balears -->\r\n" + "		<div id=\"cap\">\r\n"
							+ "			<img src=\"" + logo + "\" alt=\"Logo CAIB\" width=\"100\" height=\"100\"/>\r\n"
							+ "			<h1>" + nombre.toUpperCase() + "</h1>\r\n" + "		</div>\r\n"
							+ "		<!-- /logo illes balears -->\r\n" + "\r\n" + "		<!-- continguts -->\r\n"
							+ "	  <div id=\"continguts\">\r\n" + "\r\n" + "			<!-- titol -->\r\n"
							+ "			<h1>\r\n"
							+ "				Aquest missatge ha estat generat pel sistema d'alertes de SISTRAHELP"
							+ "</h1>" + "<h2>L&#39;event: \"" + ev + simbolo + veces + "\" de l&#39;Alerta: \""
							+ aService.loadAlerta(alCod).getNombre() + "\" s&#39;ha produ&iuml;t "
							+ hService.countAuditoriaEvento(faut) + " vegades." + "			</h2>\r\n" + "\r\n"
							+ "		<!-- /continguts -->\r\n" + "\r\n" + "\r\n" + "</div>"
							+ "	<p class=\"auto\">MOLT IMPORTANT: Aquest correu s&#39;ha generat de forma autom&#224;tica. Si us plau no s&#39;ha de respondre a aquest correu.</p>\r\n"
							+ "\r\n" + "	</div>\r\n" + "	<!-- /contenidor -->\r\n" + "\r\n" + "</body>\r\n"
							+ "</html>",
					null);
		} catch (EmailPluginException e) {
			e.printStackTrace();
		} catch (PluginException e) {
			e.printStackTrace();
		}
	}

}