package es.caib.sistrahelp.frontend.procesos;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.commons.digester.plugins.PluginException;
import org.primefaces.PrimeFaces;

import es.caib.sistra2.commons.plugins.email.api.EmailPluginException;
import es.caib.sistra2.commons.plugins.email.api.IEmailPlugin;
import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.ErroresCuadroMando;
import es.caib.sistrahelp.core.api.model.ErroresTramites;
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

public class TimerReinicioDiario implements Runnable {

	private AlertaService aService;
	private HelpDeskService hService;
	private ConfiguracionService confService;
	private HistorialAlertaService historialService;

	private int tramIni;

	private int tramFin;

	private int errTot;

	private int pagIni;

	private int pagFin;

	private int regIni;

	private int regFin;

	private int formIni;

	private int formFin;

	private int firmaIni;

	private int firmaFin;

	private Alerta alert;

	private List<ErroresCuadroMando> listaErrores;

	private FiltroAuditoriaTramitacion filtros;

	private List<EventoAuditoriaTramitacion> listaDatos;

	private List<EventoAuditoriaTramitacion> listaDatosEventosPlataforma;

	private FiltroAuditoriaTramitacion filtrosPlataforma;

	public TimerReinicioDiario(AlertaService aService, HelpDeskService hService, ConfiguracionService confService,
			HistorialAlertaService historialService) {
		this.aService = aService;
		this.hService = hService;
		this.confService = confService;
		this.historialService = historialService;
	}

	@Override
	@PostConstruct
	public void run() {

		System.out.println("entra en reinicio");
		List<HistorialAlerta> lHal = historialService.listHistorialAlerta(getYesterday(), getNow());
		alert = aService.loadAlertaByNombre("RESUMEN_DIARIO");
		System.out.println("hoy es: " + getYesterday().toString());
		if (alert != null) {
			buscar();
			String formPor;
			if (pagIni != 0) {
				formPor = formatDouble((100 - ((Double.valueOf(formFin) * 100) / Double.valueOf(formIni))));
			} else {
				if (formFin == 0) {
					formPor = "0,00";
				} else {
					formPor = "100,00";
				}
			}
			String firmaPor;
			if (firmaIni != 0) {
				firmaPor = formatDouble((100 - ((Double.valueOf(firmaFin) * 100) / Double.valueOf(firmaIni))));
			} else {
				if (firmaFin == 0) {
					firmaPor = "0,00";
				} else {
					firmaPor = "100,00";
				}
			}
			String pagosPor;
			if (pagIni != 0) {
				pagosPor = formatDouble((100 - ((Double.valueOf(pagFin) * 100) / Double.valueOf(pagIni))));
			} else {
				if (pagFin == 0) {
					pagosPor = "0,00";
				} else {
					pagosPor = "100,00";
				}
			}
			String registrosPor;
			if (regIni != 0) {
				registrosPor = formatDouble((100 - ((Double.valueOf(regFin) * 100) / Double.valueOf(regIni))));
			} else {
				if (regFin == 0) {
					registrosPor = "0,00";
				} else {
					registrosPor = "100,00";
				}
			}
			String tramPor;
			if (tramIni != 0) {
				tramPor = formatDouble(100 - ((Double.valueOf(tramFin) * 100) / Double.valueOf(tramIni)));
			} else {
				tramPor = "100";
			}
			String msg = "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ca\" lang=\"ca\">\r\n" + "\r\n"
					+ "<head>\r\n" + "\r\n"
					+ "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n"
					+ "	<title>GOVERN DE LES ILLES BALEARS</title>\r\n" + "\r\n" + "	<!-- css -->\r\n"
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
					+ "	</style>\r\n" + "	<!-- /css -->\r\n" + "\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n" + "\r\n"
					+ "	<!-- contenidor -->\r\n" + "	<div id=\"contenidor\">\r\n" + "\r\n"
					+ "		<!-- logo illes balears -->\r\n" + "		<div id=\"cap\">\r\n"
					+ "			<img src=\"https://www.caib.es/webcaib/sistra2_logos/goib-05.png\" alt=\"Logo CAIB\" width=\"100\" height=\"100\"/>\r\n"
					+ "			<h1>GOVERN DE LES ILLES BALEARS</h1>\r\n" + "		</div>\r\n"
					+ "		<!-- /logo illes balears -->\r\n" + "\r\n" + "		<!-- continguts -->\r\n"
					+ "	  <div id=\"continguts\">\r\n" + "\r\n" + "			<!-- titol -->\r\n" + "			<h1>\r\n"
					+ "				Aquest missatge ha estat generat pel sistema d'alertes de SISTRAHELP y ofereix un resum dels events recogits al Cuadre de comandament de SISTRAHELP, pel "
					+ parseFecha(getYesterday()) + ":" + "			</h1>";
			msg += "<p>" + "Emplenar Formulari:" + "</p>" + "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Iniciats: " + formIni
					+ "</p>" + "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Finalitzats: " + formFin + "</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Percentatge no finalitzats: " + formPor + "%</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nivell Gravetat: " + nivelGravedad(formPor) + "</p>";
			msg += "<p>" + "Firmar:" + "</p>" + "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Iniciats: " + firmaIni + "</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Finalitzats: " + firmaFin + "</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Percentatge no finalitzats: " + firmaPor + "%</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nivell Gravetat: " + nivelGravedad(firmaPor) + "</p>";
			msg += "<p>" + "Pagaments:" + "</p>" + "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Iniciats: " + pagIni + "</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Finalitzats: " + pagFin + "</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Percentatge no finalitzats: " + pagosPor + "%</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nivell Gravetat: " + nivelGravedad(pagosPor) + "</p>";
			msg += "<p>" + "Registres:" + "</p>" + "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Iniciats: " + regIni + "</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Finalitzats: " + regFin + "</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Percentatge no finalitzats: " + registrosPor + "%</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nivell Gravetat: " + nivelGravedad(registrosPor) + "</p>";
			msg += "<p>" + "Tr&#224;mits:" + "</p>" + "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Iniciades: " + tramIni + "</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Finalitzades: " + tramFin + "</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Percentatge no finalitzades: " + tramPor + "%</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Nivell Gravetat: " + nivelGravedad(100 + tramPor) + "</p>";

			msg += "<p>Nombre total d'Errors: " + errTot + "</p>";

			for (ErroresCuadroMando lerr : listaErrores) {
				if (lerr.getVersion() != null) {
					msg += "<p>*Tr&#224;mit: " + lerr.getIdentificadorTramite() + " versi&#243; " + lerr.getVersion()
							+ "</p>";
				} else {
					msg += "<p>*Tr&#224;mit: " + lerr.getIdentificadorTramite() + "</p>";
				}
				if (lerr.getSesOk() != null) {
					msg += "<p>&nbsp;Sessions OK:  " + lerr.getSesOk() + "</p>" + "<p>&nbsp;Sessions no finalitzades:  "
							+ lerr.getSesInac() + "</p>" + "<p>&nbsp;Porcentatge de sessions no finalitzades:  "
							+ formatDouble(lerr.getSesPor()) + "%</p>" + "<p>&nbsp;Freq&#252;&#232;ncia d&#39;errors: "
							+ lerr.getErrTram();
				} else {
					msg += "<p>&nbsp;Sessions OK: </p>" + "<p>&nbsp;Sessions no finalitzades: </p>"
							+ "<p>&nbsp;Porcentatge de sessions no finalitzades: </p>"
							+ "<p>&nbsp;Porcentatge de sessions no finalitzades: </p>";
				}
				for (ErroresTramites terr : lerr.getListaErr()) {
					msg += "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-" + terr.getIdentificadorError() + "<p/>"
							+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Freq&#252;&#232;ncia: " + terr.getErrNum()
							+ "<p/>" + "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Percentatge d&#39;errors: "
							+ formatDouble((Double.valueOf(terr.getErrNum()) / Double.valueOf(lerr.getErrTram())) * 100)
							+ "%<p/>";

				}
			}
			if (listaErrores.isEmpty()) {
				msg += "</br></br>";
			}
			if (lHal != null && !lHal.isEmpty()) {
				msg += "<p>Historial d&#39;Alertes:</p>";
				for (HistorialAlerta hAl : lHal) {
					msg += "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alerta: " + hAl.getAlerta().getNombre() + " - "
							+ hAl.getEvento().toString() + ". Data : " + parseFechaHistorial(hAl.getFecha()) + "<p/>";
				}
			} else {
				msg += "No s'ha produït cap alerta avui.";
			}
			msg += "</div><p class=\"auto\">MOLT IMPORTANT: Aquest correu s&#39;ha generat de forma autom&#224;tica. Si us plau no s&#39;ha de respondre a aquest correu.</p></html>";
			System.out.println("Resumen diario enviado");
			enviarEmail(msg);
			purgarHistorial(historialService.listHistorialAlerta(null, null));
			purgarAlertas();
			TimerHilosAlertas tAl = new TimerHilosAlertas();
			tAl.run(aService, hService, confService, historialService);
		} else {
			System.out.println("L'alerta RESUMEN_DIARIO no és creada.");
		}
	}

	private Date getYesterday() {
		return new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
	}

	private Date getNow() {
		Date date = new Date();
		return date;
	}

	private void purgarAlertas() {
		List<Alerta> alertasEliminar = aService.listAlertaActivo(null, false);
		for (Alerta a : alertasEliminar) {
			aService.removeAlerta(a.getCodigo());
		}
	}

	private String nivelGravedad(String porcentageStr) {
		String texto = "";
		String porcentageParsed = porcentageStr.replace(",", ".");
		Double porcentage = 100 - Double.parseDouble(porcentageParsed);
		if (porcentage <= 15) {
			texto = "Revisar";
		} else if (porcentage > 15 && porcentage <= 50) {
			texto = "Atenció";
		} else if (porcentage > 50) {
			texto = "Normal";
		}
		return texto;
	}

	public String formatDouble(Double dbl) {
		return new DecimalFormat("0.00").format(dbl);
	}

	/**
	 * Buscar.
	 */
	private void buscar() {
		tramIni = 0;
		tramFin = 0;
		pagIni = 0;
		pagFin = 0;
		regIni = 0;
		regFin = 0;
		errTot = 0;
		formIni = 0;
		formFin = 0;
		firmaIni = 0;
		firmaFin = 0;
		listaErrores = new ArrayList<ErroresCuadroMando>();
		List<String> listaAux = new ArrayList<String>();
		filtros = new FiltroAuditoriaTramitacion(alert.getListaAreas(), false, false);
		filtrosPlataforma = new FiltroAuditoriaTramitacion(alert.getListaAreas(), true, false);

		filtros.setFechaDesde(getYesterday());
		filtrosPlataforma.setFechaDesde(getYesterday());

		listaDatos = hService.obtenerAuditoriaEvento(filtros, null);
		// Quitamos seleccion de dato
		if (listaDatos != null && !listaDatos.isEmpty()) {
			for (EventoAuditoriaTramitacion ea : listaDatos) {
				if (ea.getTipoEvento().equals(TypeEvento.REGISTRAR_TRAMITE_INICIO)) {
					regIni++;
				} else if (ea.getTipoEvento().equals(TypeEvento.REGISTRAR_TRAMITE)) {
					regFin++;
				} else if (ea.getTipoEvento().equals(TypeEvento.FORMULARIO_INICIO)) {
					formIni++;
				} else if (ea.getTipoEvento().equals(TypeEvento.FORMULARIO_FIN)) {
					formFin++;
				} else if (ea.getTipoEvento().equals(TypeEvento.FIRMA_INICIO)) {
					firmaIni++;
				} else if (ea.getTipoEvento().equals(TypeEvento.FIRMA_FIN) && ea.getResultado() != null
						&& ea.getResultado().equals("Ok")) {
					firmaFin++;
				} else if (ea.getTipoEvento().equals(TypeEvento.PAGO_ELECTRONICO_INICIO)) {
					pagIni++;
				} else if (ea.getTipoEvento().equals(TypeEvento.PAGO_ELECTRONICO_VERIFICADO)
						|| ea.getTipoEvento().equals(TypeEvento.PAGO_PRESENCIAL)) {
					pagFin++;
				} else if (ea.getTipoEvento().equals(TypeEvento.INICIAR_TRAMITE)) {
					tramIni++;
				} else if (ea.getTipoEvento().equals(TypeEvento.FIN_TRAMITE)) {
					tramFin++;
				} else if (ea.getTipoEvento().equals(TypeEvento.ERROR)) {
					errTot++;
					if (listaAux.contains(ea.getIdTramite() + ea.getVersionTramite())) {
						for (ErroresCuadroMando erCmando : listaErrores) {
							if (erCmando.getIdentificadorTramite().equals(ea.getIdTramite())
									&& erCmando.getVersion() == ea.getVersionTramite()) {
								erCmando.setErrTram(erCmando.getErrTram() + 1);
								boolean encontrado = false;
								for (ErroresTramites er : erCmando.getListaErr()) {
									if (er.getIdentificadorError().equals(ea.getCodigoError())) {
										encontrado = true;
										er.setErrNum(er.getErrNum() + 1);
										break;
									}
								}
								if (!encontrado) {
									ErroresTramites err = new ErroresTramites();
									err.setErrNum(1);
									err.setIdentificadorError(ea.getCodigoError());
									erCmando.getListaErr().add(err);
								}
								break;
							}
						}
					} else {
						listaAux.add(ea.getIdTramite() + ea.getVersionTramite());
						ErroresCuadroMando erC = new ErroresCuadroMando();
						erC.setIdentificadorTramite(ea.getIdTramite());
						erC.setVersion(ea.getVersionTramite());
						List<ErroresTramites> listaErroresTram = new ArrayList<ErroresTramites>();
						ErroresTramites err = new ErroresTramites();
						err.setErrNum(1);
						err.setIdentificadorError(ea.getCodigoError());
						listaErroresTram.add(err);
						erC.setListaErr(listaErroresTram);
						erC.setErrTram(1);
						listaErrores.add(erC);
					}
				}

			}
		}
		listaDatosEventosPlataforma = hService.obtenerAuditoriaEvento(filtrosPlataforma, null);
		if (listaDatosEventosPlataforma != null && !listaDatosEventosPlataforma.isEmpty()) {
			for (EventoAuditoriaTramitacion ea : listaDatosEventosPlataforma) {
				if (ea.getTipoEvento().equals(TypeEvento.ERROR)) {
					errTot++;
					if (listaAux.contains("Errors Plataforma")) {
						for (ErroresCuadroMando erCmando : listaErrores) {
							if (erCmando.getIdentificadorTramite().equals("Errors Plataforma")) {
								erCmando.setErrTram(erCmando.getErrTram() + 1);
								boolean encontrado = false;
								for (ErroresTramites er : erCmando.getListaErr()) {
									if (er.getIdentificadorError().equals(ea.getCodigoError())) {
										encontrado = true;
										er.setErrNum(er.getErrNum() + 1);
										break;
									}
								}
								if (!encontrado) {
									ErroresTramites err = new ErroresTramites();
									err.setErrNum(1);
									err.setIdentificadorError(ea.getCodigoError());
									erCmando.getListaErr().add(err);
								}
								break;
							}
						}
					} else {
						listaAux.add("Errors Plataforma");
						ErroresCuadroMando erC = new ErroresCuadroMando();
						erC.setIdentificadorTramite("Errors Plataforma");
						List<ErroresTramites> listaErroresTram = new ArrayList<ErroresTramites>();
						ErroresTramites err = new ErroresTramites();
						err.setErrNum(1);
						err.setIdentificadorError(ea.getCodigoError());
						listaErroresTram.add(err);
						erC.setListaErr(listaErroresTram);
						erC.setErrTram(1);
						listaErrores.add(erC);
					}
				}
			}
		}
		List<String> listaIniciados;
		List<String> listaFinalizados;
		List<String> listaErrados;
		for (ErroresCuadroMando ec : listaErrores) {
			if (!ec.getIdentificadorTramite().equals("Errors Plataforma")) {
				listaIniciados = new ArrayList<String>();
				listaFinalizados = new ArrayList<String>();
				listaErrados = new ArrayList<String>();
				for (EventoAuditoriaTramitacion eAut : listaDatos) {
					if (eAut.getIdTramite().equals(ec.getIdentificadorTramite())
							&& eAut.getVersionTramite() == ec.getVersion()) {
						if (eAut.getTipoEvento().equals(TypeEvento.INICIAR_TRAMITE)) {
							listaIniciados.add(eAut.getIdSesionTramitacion());
						} else if (eAut.getTipoEvento().equals(TypeEvento.FIN_TRAMITE)) {
							listaFinalizados.add(eAut.getIdSesionTramitacion());
						} else if (eAut.getTipoEvento().equals(TypeEvento.ERROR)
								&& !listaErrados.contains(eAut.getIdSesionTramitacion())) {
							listaErrados.add(eAut.getIdSesionTramitacion());
						}
					}
				}
				int ko = 0;
				int ok = 0;
				int inac = 0;
				for (String sesion : listaIniciados) {
					if (listaFinalizados.contains(sesion)) {
						ok++;
					} else if (!listaFinalizados.contains(sesion) && listaErrados.contains(sesion)) {
						ko++;
					} else if (!listaFinalizados.contains(sesion) && !listaErrados.contains(sesion)) {
						inac++;
					}
				}
				ec.setSesOk(ok);
				ec.setSesKo(ko);
				ec.setSesInac(inac + ko);
				int iniciados = listaIniciados.size();
				if (iniciados != 0) {
					ec.setSesPor(Math.floor(((Double.valueOf(ko + inac) / Double.valueOf(iniciados)) * 100) * 10) / 10);
				} else {
					ec.setSesPor(Double.valueOf(0));
				}
			} else {
				ec.setSesInac(null);
				ec.setSesOk(null);
				ec.setSesKo(null);
				ec.setSesPor(null);
			}
		}
	}

	private String txtHacer(EventoAuditoriaTramitacion terr, String tipo) {
		if (tipo.equals("n")) {
			if (terr.getNombre() != null && terr.getApellido1() != null && terr.getApellido2() != null) {
				return terr.getNombre().concat(" ").concat(terr.getApellido1()).concat(" ").concat(terr.getApellido2());
			} else if (terr.getNombre() != null && terr.getApellido1() != null) {
				return terr.getNombre().concat(" ").concat(terr.getApellido1());
			} else if (terr.getNombre() != null) {
				return terr.getNombre();
			} else {
				return "";
			}
		} else {
			if (terr.getNif() != null) {
				return terr.getNif();
			} else {
				return "";
			}
		}

	}

	private void purgarHistorial(List<HistorialAlerta> lHal) {
		if (lHal != null && !lHal.isEmpty()) {
			for (HistorialAlerta h : lHal) {
				historialService.removeHistorialAlerta(h.getCodigo());
			}
		}
	}

	private void enviarEmail(String msg) {
		try {
			final IEmailPlugin plgEmail = (IEmailPlugin) confService.obtenerPluginGlobal(TypePluginGlobal.EMAIL);
			plgEmail.envioEmail(alert.getEmail(), "SISTRAHELP: Resumen Diario", msg, null);
		} catch (EmailPluginException e) {
			e.printStackTrace();
		} catch (PluginException e) {
			e.printStackTrace();
		}
	}

	private String parseFecha(Date fecha) {
		Locale spanishLocale = new Locale("es", "ES");
		LocalDateTime fechaL = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		String dateInSpanish = fechaL
				.format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy", spanishLocale));
		return dateInSpanish;
	}

	private String parseFechaHistorial(Date fecha) {
		DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sourceFormat.format(fecha);
	}

	/**
	 * @return the tramIni
	 */
	public final int getTramIni() {
		return tramIni;
	}

	/**
	 * @param tramIni the tramIni to set
	 */
	public final void setTramIni(int tramIni) {
		this.tramIni = tramIni;
	}

	/**
	 * @return the tramFin
	 */
	public final int getTramFin() {
		return tramFin;
	}

	/**
	 * @param tramFin the tramFin to set
	 */
	public final void setTramFin(int tramFin) {
		this.tramFin = tramFin;
	}

	/**
	 * @return the errTot
	 */
	public final int getErrTot() {
		return errTot;
	}

	/**
	 * @param errTot the errTot to set
	 */
	public final void setErrTot(int errTot) {
		this.errTot = errTot;
	}

	/**
	 * @return the pagIni
	 */
	public final int getPagIni() {
		return pagIni;
	}

	/**
	 * @param pagIni the pagIni to set
	 */
	public final void setPagIni(int pagIni) {
		this.pagIni = pagIni;
	}

	/**
	 * @return the pagFin
	 */
	public final int getPagFin() {
		return pagFin;
	}

	/**
	 * @param pagFin the pagFin to set
	 */
	public final void setPagFin(int pagFin) {
		this.pagFin = pagFin;
	}

	/**
	 * @return the regIni
	 */
	public final int getRegIni() {
		return regIni;
	}

	/**
	 * @param regIni the regIni to set
	 */
	public final void setRegIni(int regIni) {
		this.regIni = regIni;
	}

	/**
	 * @return the regFin
	 */
	public final int getRegFin() {
		return regFin;
	}

	/**
	 * @param regFin the regFin to set
	 */
	public final void setRegFin(int regFin) {
		this.regFin = regFin;
	}

	/**
	 * @return the listaErrores
	 */
	public final List<ErroresCuadroMando> getListaErrores() {
		return listaErrores;
	}

	/**
	 * @param listaErrores the listaErrores to set
	 */
	public final void setListaErrores(List<ErroresCuadroMando> listaErrores) {
		this.listaErrores = listaErrores;
	}

	/**
	 * @return the filtros
	 */
	public final FiltroAuditoriaTramitacion getFiltros() {
		return filtros;
	}

	/**
	 * @param filtros the filtros to set
	 */
	public final void setFiltros(FiltroAuditoriaTramitacion filtros) {
		this.filtros = filtros;
	}

	/**
	 * @return the listaDatos
	 */
	public final List<EventoAuditoriaTramitacion> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos the listaDatos to set
	 */
	public final void setListaDatos(List<EventoAuditoriaTramitacion> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the listaDatosEventosPlataforma
	 */
	public final List<EventoAuditoriaTramitacion> getListaDatosEventosPlataforma() {
		return listaDatosEventosPlataforma;
	}

	/**
	 * @param listaDatosEventosPlataforma the listaDatosEventosPlataforma to set
	 */
	public final void setListaDatosEventosPlataforma(List<EventoAuditoriaTramitacion> listaDatosEventosPlataforma) {
		this.listaDatosEventosPlataforma = listaDatosEventosPlataforma;
	}

	/**
	 * @return the formIni
	 */
	public final int getFormIni() {
		return formIni;
	}

	/**
	 * @param formIni the formIni to set
	 */
	public final void setFormIni(int formIni) {
		this.formIni = formIni;
	}

	/**
	 * @return the formFin
	 */
	public final int getFormFin() {
		return formFin;
	}

	/**
	 * @param formFin the formFin to set
	 */
	public final void setFormFin(int formFin) {
		this.formFin = formFin;
	}

	/**
	 * @return the firmaIni
	 */
	public final int getFirmaIni() {
		return firmaIni;
	}

	/**
	 * @param firmaIni the firmaIni to set
	 */
	public final void setFirmaIni(int firmaIni) {
		this.firmaIni = firmaIni;
	}

	/**
	 * @return the firmaFin
	 */
	public final int getFirmaFin() {
		return firmaFin;
	}

	/**
	 * @param firmaFin the firmaFin to set
	 */
	public final void setFirmaFin(int firmaFin) {
		this.firmaFin = firmaFin;
	}
}