package es.caib.sistrahelp.frontend.controller;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.mail.iap.Literal;

import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.DisparadorAlerta;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrahelp.core.api.service.AlertaService;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.core.api.service.HistorialAlertaService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.procesos.TimerEvaluarAlertas;
import es.caib.sistrahelp.frontend.util.UtilJSF;

@ManagedBean
@ViewScoped
public class DialogConfiguracionAlertas extends DialogControllerBase {

	/**
	 * helpdesk service.
	 */
	@Inject
	private AlertaService alertaService;

	/**
	 * Id elemento a tratar.
	 */
	private String id;

	private String tipo;

	/**
	 * lista datos.
	 */
	private List<Alerta> listaDatos;

	/**
	 * dato seleccionado.
	 */
	private Alerta data;

	private DisparadorAlerta disparadorSeleccionado;

	private List<String> listaEntidad;

	private List<String> listaArea;

	private List<String> listaTramite;

	private List<Integer> listaVersion;

	private List<DisparadorAlerta> disparadores;

	private String portapapeles;

	private TypeEvento evSeleccionado;

	private String operadorSeleccionado;

	private Integer concurrenciasSeleccionado;

	private List<TypeEvento> eventos;

	private List<String> operadores;

	private String errorCopiar;

	private String emails;

	private boolean check;

	private Date fDesde;

	private Date fHasta;

	private Date periodo;

	private String entidad;

	private String area;

	private String tramite;

	private Integer version;

	@Inject
	private AlertaService aService;

	@Inject
	private HelpDeskService hService;

	@Inject
	private ConfiguracionService confService;

	@Inject
	private HistorialAlertaService histAvis;

	/**
	 * Inicializacion.
	 */
	public void init() {

		// Modo acceso
		listaEntidad = new ArrayList<String>();
		listaArea = new ArrayList<String>();
		listaTramite = new ArrayList<String>();
		listaVersion = new ArrayList<Integer>();
		operadores = new ArrayList<String>();
		disparadores = new ArrayList<DisparadorAlerta>();
		final TypeModoAcceso modo = TypeModoAcceso.valueOf(modoAcceso);
		UtilJSF.checkSecOpenDialog(modo, id);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdfS = new SimpleDateFormat("HH:mm:ss");

		for (Area area : UtilJSF.getSessionBean().getListaAreasEntidad()) {
			if(!listaEntidad.contains(area.getIdentificador().split("\\.")[0])) {
				listaEntidad.add(area.getIdentificador().split("\\.")[0]);
			}
		}

		if (modo == TypeModoAcceso.ALTA) {
			data = new Alerta();

			try {
				fDesde = sdf.parse("00:00");
				fHasta = sdf.parse("00:00");
				periodo = sdfS.parse("00:00:01");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			if (id != null) {
				data = alertaService.loadAlerta(Long.valueOf(id));

				if (isResumenDiario()) {
					check = true;
					data.setIntervaloEvaluacion("");
					data.setPeriodoEvaluacion(null);
					data.setEventos(null);
					fDesde = null;
					fHasta = null;
					periodo = null;
				} else {
					check = false;
					if (!isResumenDiario()) {
						tipo = data.getTipo();
						switch(tipo) {
						case "E":
							entidad = data.getListaAreas().get(0).split("\\.")[0];
							break;

						case "A":
							entidad = data.getListaAreas().get(0).split("\\.")[0];
							area = data.getListaAreas().get(0).split("\\.")[1];
							break;

						case "T":
							entidad = data.getListaAreas().get(0).split("\\.")[0];
							area = data.getListaAreas().get(0).split("\\.")[1];
							tramite = data.getTramite();
							break;

						case "V":
							entidad = data.getListaAreas().get(0).split("\\.")[0];
							area = data.getListaAreas().get(0).split("\\.")[1];
							tramite = data.getTramite();
							version = data.getVersion();
							break;

						}

						valoresArea();
						valoresTramite();
						valoresVersion();

					}
					for (String evento : data.getEventos()) {
						String[] partes = evento.split(":");
						DisparadorAlerta disparador = new DisparadorAlerta();
						disparador.setEv(TypeEvento.fromString(partes[0]));
						disparador.setOperador(partes[1]);
						disparador.setConcurrencias(Integer.parseInt(partes[2]));
						disparadores.add(disparador);
					}
					try {
						fDesde = sdf.parse(data.getIntervaloEvaluacion().split("-")[0]);
						fHasta = sdf.parse(data.getIntervaloEvaluacion().split("-")[1]);
						periodo = sdfS.parse(formatSeconds(data.getPeriodoEvaluacion()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (!isResumenDiario()) {
			concurrenciasSeleccionado = 0;
		}
		eventos = Arrays.asList(TypeEvento.values());
		operadores.add("<");
		operadores.add(">");
		operadores.add("=");
		emails = emailString(data.getEmail());


	}

	public void valoresArea() {
		if(this.entidad != null) {
			listaArea.clear();
			for (Area area : UtilJSF.getSessionBean().getListaAreasEntidad()) {
				if(area.getIdentificador().split("\\.")[0].equals(this.entidad)) {
					listaArea.add(area.getIdentificador().split("\\.")[1]);
				}
			}
		}else {
			this.area=null;
			this.tramite=null;
			this.version = null;
		}
	}

	public void valoresTramite() {
		if(this.area != null) {
			listaTramite.clear();
			for (Area area : UtilJSF.getSessionBean().getListaAreasEntidad()) {
				if(area.getIdentificador().split("\\.")[0].equals(this.entidad) && area.getIdentificador().split("\\.")[1].equals(this.area)) {
					listaTramite.addAll(confService.obtenerTramitesPorArea(area.getIdentificador()));
					break;
				}
			}
		}else {
			this.tramite=null;
			this.version = null;
		}
	}

	public void valoresVersion() {
		if(this.tramite != null) {
			listaVersion.clear();
			for (Area area : UtilJSF.getSessionBean().getListaAreasEntidad()) {
				if(area.getIdentificador().split("\\.")[0].equals(this.entidad) && area.getIdentificador().split("\\.")[1].equals(this.area)) {					listaVersion.clear();
					listaVersion.addAll(confService.obtenerVersionTramite(area.getIdentificador(),tramite));
					break;
				}
			}
		}else {
			this.version = null;
		}
	}

	private static String formatSeconds(int timeInSeconds) {
		int hours = timeInSeconds / 3600;
		int secondsLeft = timeInSeconds - hours * 3600;
		int minutes = secondsLeft / 60;
		int seconds = secondsLeft - minutes * 60;

		String formattedTime = "";
		if (hours < 10)
			formattedTime += "0";
		formattedTime += hours + ":";

		if (minutes < 10)
			formattedTime += "0";
		formattedTime += minutes + ":";

		if (seconds < 10)
			formattedTime += "0";
		formattedTime += seconds;

		return formattedTime;
	}

	public String emailString(List<String> lista) {
		String str = "";
		if (lista != null && !lista.isEmpty()) {
			for (String strAux : lista) {
				if (str.isEmpty()) {
					str += strAux;
				} else {
					str += ";" + strAux;
				}
			}
		}
		return str;
	}

	public void hacerResumen() {
		if (check) {
			data.setIntervaloEvaluacion("");
			data.setPeriodoEvaluacion(null);
			data.setNombre("RESUMEN_DIARIO");
			evSeleccionado = null;
			operadorSeleccionado = null;
			concurrenciasSeleccionado = null;
			disparadores = new ArrayList<DisparadorAlerta>();
			fDesde = null;
			fHasta = null;
			periodo = null;
		} else {
			data.setNombre("");
			concurrenciasSeleccionado = 0;
		}
	}

	public boolean noExisteResumen() {
		Alerta a = aService.loadAlertaByNombre("RESUMEN_DIARIO");
		if (a != null && a.getCodigo() != data.getCodigo()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Añade un disparador a la lista
	 */
	public void anyadirDisparador() {
		if (evSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogConfiguracionAlertas.error.evSeleccionado"));
			return;
		}

		if (operadorSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogConfiguracionAlertas.error.operadorSeleccionado"));
			return;
		}

		if (concurrenciasSeleccionado == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogConfiguracionAlertas.error.concurrenciasSeleccionado"));
			return;
		}

		for (DisparadorAlerta dAl : disparadores) {
			if (dAl.getEv().equals(evSeleccionado) && dAl.getOperador().equals(operadorSeleccionado)) {
				return;
			}
		}
		DisparadorAlerta disp = new DisparadorAlerta();
		disp.setEv(evSeleccionado);
		disp.setOperador(operadorSeleccionado);
		disp.setConcurrencias(concurrenciasSeleccionado);

		String eventos = "";
		for (DisparadorAlerta dA : disparadores) {
			if (eventos.isEmpty()) {
				eventos += dA.getEv().toString() + ":" + dA.getOperador() + ":" + dA.getConcurrencias();
			} else {
				eventos += ";" + dA.getEv().toString() + ":" + dA.getOperador() + ":" + dA.getConcurrencias();
			}
		}
		if (eventos.isEmpty()) {
			eventos += disp.getEv().toString() + ":" + disp.getOperador() + ":" + disp.getConcurrencias();
		} else {
			eventos += ";" + disp.getEv().toString() + ":" + disp.getOperador() + ":" + disp.getConcurrencias();
		}

		if (eventos.length() <= 255) {
			disparadores.add(disp);
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogMensajeAviso.error.seleccioneVersion"));
		}

	}

	/**
	 * Borra un trámite seleccinado.
	 *
	 * @param tram
	 *
	 */
	public void borrarTramite(final DisparadorAlerta disp) {
		disparadores.remove(disp);
	}

	/**
	 * Verificar precondiciones al guardar.
	 *
	 * @return true, si se cumplen las todas la condiciones
	 */
	private boolean verificarGuardar() {

		if (data.getNombre() == null || data.getNombre().isEmpty()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.nombre.vacio"));
			return false;
		}

		if (aService.loadAlertaByNombre(data.getNombre()) != null
				&& !aService.loadAlertaByNombre(data.getNombre()).getCodigo().equals(data.getCodigo())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("dialogConfiguracionAlertas.error.nombreRepetido"));
			return false;
		}

		if (emails == null || emails.isEmpty()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.email.vacio"));
			return false;
		}

		if (!isResumenDiario()) {

			if (periodo == null) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.periodo.vacio"));
				return false;
			}

			SimpleDateFormat sdfS = new SimpleDateFormat("HH:mm");
			Date f0 = null;
			try {
				f0 = sdfS.parse("00:00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if ((((periodo.getTime() - f0.getTime()) / 1000) / 60) < 5) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.periodo.menor"));
				return false;
			}

			if (fDesde == null || fHasta == null) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.intervalo.vacio"));
				return false;
			}

			if (disparadores == null || disparadores.isEmpty()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.disparadores.vacio"));
				return false;
			}

			if(tipo.equals("E") && (entidad == null || entidad.isEmpty())) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.combo.ambito.vacio"));
				return false;
			}

			if(tipo.equals("A") && ((entidad == null || entidad.isEmpty()) || (area == null || area.isEmpty()))) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.combo.ambito.vacio"));
				return false;
			}

			if(tipo.equals("T") && ((entidad == null || entidad.isEmpty()) || (area == null || area.isEmpty()) || (tramite == null || tramite.isEmpty()))) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.combo.ambito.vacio"));
				return false;
			}

			if(tipo.equals("V") && ((entidad == null || entidad.isEmpty()) || (area == null || area.isEmpty()) || (tramite == null || tramite.isEmpty()) || (version == null))) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.combo.ambito.vacio"));
				return false;
			}
		}

		for (final String cadena : Arrays.asList(emails.split(";"))) {
			if (!cadena.matches(
					"^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$")
					&& cadena != null && !cadena.isEmpty()) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.email.formato"));
				return false;
			}
		}

		if (!isResumenDiario()) {

			if (((fHasta.getTime() - fDesde.getTime()) / 1000) <= 0) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.intervalo.antes"));
				return false;
			}
			SimpleDateFormat sdfS = new SimpleDateFormat("HH:mm");
			Date f0 = null;
			try {
				f0 = sdfS.parse("00:00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (((fHasta.getTime() - fDesde.getTime()) / 1000) <= ((periodo.getTime() - f0.getTime()) / 1000)) {
				UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.intervalo.periodo"));
				return false;
			}
		}
		return true;
	}

	/**
	 * Aceptar.
	 */
	public void aceptar() {

		// verificamos precondiciones
		if (!verificarGuardar()) {
			return;
		}

		// Realizamos alta o update
		final TypeModoAcceso acceso = TypeModoAcceso.valueOf(modoAcceso);
		switch (acceso) {
		case ALTA:
			List<String> eventos = new ArrayList<String>();
			for (DisparadorAlerta dA : disparadores) {
				eventos.add(dA.getEv().toString() + ":" + dA.getOperador() + ":" + dA.getConcurrencias());
			}

			data.setEventos(eventos);
			data.setEmail(Arrays.asList(emails.split(";")));
			data.setTipo(tipo);
			data.setIdEntidad(UtilJSF.getSessionBean().getEntidad().getCodigoDIR3());
			if (!isResumenDiario()) {
				switch(tipo) {
				case "E":
					if(this.entidad!=null) {
						List<String> lisAreasAux = new ArrayList<String>();
						for (Area area : UtilJSF.getSessionBean().getListaAreasEntidad()) {
							if(area.getIdentificador().split("\\.")[0].equals(this.entidad)) {
								lisAreasAux.add(area.getIdentificador());
							}
						}
						data.setListaAreas(lisAreasAux);
						data.setTramite(null);
						data.setVersion(null);
					}
					break;

				case "A":
					if(this.entidad!=null && this.area!=null) {
						List<String> lisAreasAux = new ArrayList<String>();
						lisAreasAux.add(this.entidad+"."+this.area);
						data.setListaAreas(lisAreasAux);
						data.setTramite(null);
						data.setVersion(null);
					}
					break;

				case "T":
					if(this.entidad!=null && this.area!=null && this.tramite!=null) {
						List<String> lisAreasAux = new ArrayList<String>();
						lisAreasAux.add(this.entidad+"."+this.area);
						data.setListaAreas(lisAreasAux);
						data.setTramite(this.tramite);
						data.setVersion(null);
					}
					break;

				case "V":
					if(this.entidad!=null && this.area!=null && this.tramite!=null && this.version!=null) {
						List<String> lisAreasAux = new ArrayList<String>();
						lisAreasAux.add(this.entidad+"."+this.area);
						data.setListaAreas(lisAreasAux);
						data.setTramite(this.tramite);
						data.setVersion(this.version);
					}
					break;

				}

				SimpleDateFormat sdfS = new SimpleDateFormat("HH:mm");
				Date f0 = null;
				try {
					f0 = sdfS.parse("00:00");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				DateFormat dateFormat = new SimpleDateFormat("HH:mm");
				String strDateDesd = dateFormat.format(fDesde);
				String strDateHasta = dateFormat.format(fHasta);
				data.setIntervaloEvaluacion(strDateDesd + "-" + strDateHasta);
				data.setPeriodoEvaluacion((int) ((periodo.getTime() - f0.getTime()) / 1000));
			} else {
				data.setIntervaloEvaluacion(null);
				data.setPeriodoEvaluacion(null);
				data.setTipo("E");
				List<String> lisAreasAux = new ArrayList<String>();
				for (Area area : UtilJSF.getSessionBean().getListaAreasEntidad()) {
						lisAreasAux.add(area.getIdentificador());

				}
				data.setListaAreas(lisAreasAux);
				data.setTramite(null);
				data.setVersion(null);
			}
			Long codigo = alertaService.addAlerta(data);
			if (codigo != null) {
				data.setCodigo(codigo);
			}
			if (!isResumenDiario()) {
				inicializarHilo(data);
			}
			break;
		case EDICION:
			List<String> eventosE = new ArrayList<String>();
			for (DisparadorAlerta dA : disparadores) {
				eventosE.add(dA.getEv().toString() + ":" + dA.getOperador() + ":" + dA.getConcurrencias());
			}

			data.setEventos(eventosE);
			data.setEmail(Arrays.asList(emails.split(";")));
			data.setTipo(tipo);
			data.setIdEntidad(UtilJSF.getSessionBean().getEntidad().getCodigoDIR3());

			if (!isResumenDiario()) {
				switch(tipo) {
				case "E":
					if(this.entidad!=null) {
						List<String> lisAreasAux = new ArrayList<String>();
						for (Area area : UtilJSF.getSessionBean().getListaAreasEntidad()) {
							if(area.getIdentificador().split("\\.")[0].equals(this.entidad)) {
								lisAreasAux.add(area.getIdentificador());
							}
						}
						data.setListaAreas(lisAreasAux);
						data.setTramite(null);
						data.setVersion(null);
					}
					break;

				case "A":
					if(this.entidad!=null && this.area!=null) {
						List<String> lisAreasAux = new ArrayList<String>();
						lisAreasAux.add(this.entidad+"."+this.area);
						data.setListaAreas(lisAreasAux);
						data.setTramite(null);
						data.setVersion(null);
					}
					break;

				case "T":
					if(this.entidad!=null && this.area!=null && this.tramite!=null) {
						List<String> lisAreasAux = new ArrayList<String>();
						lisAreasAux.add(this.entidad+"."+this.area);
						data.setListaAreas(lisAreasAux);
						data.setTramite(this.tramite);
						data.setVersion(null);
					}
					break;

				case "V":
					if(this.entidad!=null && this.area!=null && this.tramite!=null && this.version!=null) {
						List<String> lisAreasAux = new ArrayList<String>();
						lisAreasAux.add(this.entidad+"."+this.area);
						data.setListaAreas(lisAreasAux);
						data.setTramite(this.tramite);
						data.setVersion(this.version);
					}
					break;
				}
				SimpleDateFormat sdfS = new SimpleDateFormat("HH:mm");
				Date f0 = null;
				try {
					f0 = sdfS.parse("00:00");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				DateFormat dateFormat = new SimpleDateFormat("HH:mm");
				String strDateDesd = dateFormat.format(fDesde);
				String strDateHasta = dateFormat.format(fHasta);
				data.setIntervaloEvaluacion(strDateDesd + "-" + strDateHasta);
				data.setPeriodoEvaluacion((int) ((periodo.getTime() - f0.getTime()) / 1000));
			} else {
				data.setIntervaloEvaluacion(null);
				data.setPeriodoEvaluacion(null);
				data.setTipo("E");
				List<String> lisAreasAux = new ArrayList<String>();
				for (Area area : UtilJSF.getSessionBean().getListaAreasEntidad()) {
					if(area.getIdentificador().split("\\.")[0].equals(this.entidad)) {
						lisAreasAux.add(area.getIdentificador());
					}
				}
				data.setListaAreas(lisAreasAux);
				data.setTramite(null);
				data.setVersion(null);
			}
			alertaService.updateAlerta(data);

			break;
		case CONSULTA:
			// No hay que hacer nada
			break;
		}
		// Retornamos resultado
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setResult(data.getCodigo());
		UtilJSF.closeDialog(result);
	}

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setModoAcceso(TypeModoAcceso.valueOf(modoAcceso));
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("dialogoAlertas");
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {

		if (portapapeles.equals("") || portapapeles.equals(null)) {
			copiadoErr();
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	private void inicializarHilo(Alerta al) {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		String[] horas = al.getIntervaloEvaluacion().split("-");
		LocalTime ahora = LocalTime.now();
		Boolean targetInZone = (ahora.isAfter(LocalTime.parse(horas[0] + ":00.000000000"))
				&& ahora.isBefore(LocalTime.parse(horas[1] + ":59.999999999")));
		if (targetInZone) {
			final ScheduledFuture<?> promise = scheduler.schedule(
					new TimerEvaluarAlertas(al.getCodigo(), hService, confService, histAvis, aService), 0,
					TimeUnit.SECONDS);
			final ScheduledFuture<Boolean> canceller = scheduler.schedule(() -> promise.cancel(false),
					ahora.until(LocalTime.parse(horas[1] + ":59.999999999"), ChronoUnit.SECONDS), TimeUnit.SECONDS);
			if (ahora.until(LocalTime.parse("23:59:59.999999999"), ChronoUnit.SECONDS) > 0) {
				final ScheduledFuture<Boolean> cancellerDia = scheduler.schedule(() -> promise.cancel(false),
						ahora.until(LocalTime.parse("23:59:59.999999999"), ChronoUnit.SECONDS), TimeUnit.SECONDS);
			} else {
				final ScheduledFuture<Boolean> cancellerDia = scheduler.schedule(() -> promise.cancel(false),
						ahora.until(LocalTime.parse("23:59:59.999999999"), ChronoUnit.SECONDS) + 86400,
						TimeUnit.SECONDS);
			}
		} else if (ahora.isBefore(LocalTime.parse(horas[0] + ":00.000000000"))) {
			final ScheduledFuture<?> promise = scheduler.schedule(
					new TimerEvaluarAlertas(al.getCodigo(), hService, confService, histAvis, aService),
					ahora.until(LocalTime.parse(horas[0] + ":00.000000000"), ChronoUnit.SECONDS), TimeUnit.SECONDS);
			final ScheduledFuture<Boolean> canceller = scheduler.schedule(() -> promise.cancel(false),
					ahora.until(LocalTime.parse(horas[1] + ":59.999999999"), ChronoUnit.SECONDS), TimeUnit.SECONDS);
			if (ahora.until(LocalTime.parse("23:59:59.999999999"), ChronoUnit.SECONDS) > 0) {
				final ScheduledFuture<Boolean> cancellerDia = scheduler.schedule(() -> promise.cancel(false),
						ahora.until(LocalTime.parse("23:59:59.999999999"), ChronoUnit.SECONDS), TimeUnit.SECONDS);
			} else {
				final ScheduledFuture<Boolean> cancellerDia = scheduler.schedule(() -> promise.cancel(false),
						ahora.until(LocalTime.parse("23:59:59.999999999"), ChronoUnit.SECONDS) + 86400,
						TimeUnit.SECONDS);
			}
		}
	}

	public boolean isResumenDiario() {
		if (data.getNombre() != null) {
			return data.getNombre().equals("RESUMEN_DIARIO");
		} else {
			return false;

		}
	}

	/**
	 * @return the errorCopiar
	 */
	public final String getErrorCopiar() {
		return errorCopiar;
	}

	/**
	 * @param errorCopiar the errorCopiar to set
	 */
	public final void setErrorCopiar(String errorCopiar) {
		this.errorCopiar = errorCopiar;
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewTramites.copiar"));
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the listaDatos
	 */
	public final List<Alerta> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos the listaDatos to set
	 */
	public final void setListaDatos(List<Alerta> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the data
	 */
	public final Alerta getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public final void setData(Alerta data) {
		this.data = data;
	}

	/**
	 * @return the disparadorSeleccionado
	 */
	public final DisparadorAlerta getDisparadorSeleccionado() {
		return disparadorSeleccionado;
	}

	/**
	 * @param disparadorSeleccionado the disparadorSeleccionado to set
	 */
	public final void setDisparadorSeleccionado(DisparadorAlerta disparadorSeleccionado) {
		this.disparadorSeleccionado = disparadorSeleccionado;
	}

	/**
	 * @return the disparadores
	 */
	public final List<DisparadorAlerta> getDisparadores() {
		return disparadores;
	}

	/**
	 * @param disparadores the disparadores to set
	 */
	public final void setDisparadores(List<DisparadorAlerta> disparadores) {
		this.disparadores = disparadores;
	}

	/**
	 * @return the evSeleccionado
	 */
	public final TypeEvento getEvSeleccionado() {
		return evSeleccionado;
	}

	/**
	 * @param evSeleccionado the evSeleccionado to set
	 */
	public final void setEvSeleccionado(TypeEvento evSeleccionado) {
		this.evSeleccionado = evSeleccionado;
	}

	/**
	 * @return the operadorSeleccionado
	 */
	public final String getOperadorSeleccionado() {
		return operadorSeleccionado;
	}

	/**
	 * @param operadorSeleccionado the operadorSeleccionado to set
	 */
	public final void setOperadorSeleccionado(String operadorSeleccionado) {
		this.operadorSeleccionado = operadorSeleccionado;
	}

	/**
	 * @return the concurrenciasSeleccionado
	 */
	public final Integer getConcurrenciasSeleccionado() {
		return concurrenciasSeleccionado;
	}

	/**
	 * @param concurrenciasSeleccionado the concurrenciasSeleccionado to set
	 */
	public final void setConcurrenciasSeleccionado(Integer concurrenciasSeleccionado) {
		this.concurrenciasSeleccionado = concurrenciasSeleccionado;
	}

	/**
	 * @return the eventos
	 */
	public final List<TypeEvento> getEventos() {
		return eventos;
	}

	/**
	 * @param eventos the eventos to set
	 */
	public final void setEventos(List<TypeEvento> eventos) {
		this.eventos = eventos;
	}

	/**
	 * @return the operadores
	 */
	public final List<String> getOperadores() {
		return operadores;
	}

	/**
	 * @param operadores the operadores to set
	 */
	public final void setOperadores(List<String> operadores) {
		this.operadores = operadores;
	}

	/**
	 * @return the emails
	 */
	public final String getEmails() {
		return emails;
	}

	/**
	 * @param emails the emails to set
	 */
	public final void setEmails(String emails) {
		this.emails = emails;
	}

	/**
	 * @return the check
	 */
	public final boolean isCheck() {
		return check;
	}

	/**
	 * @param check the check to set
	 */
	public final void setCheck(boolean check) {
		this.check = check;
	}

	/**
	 * @return the fDesde
	 */
	public final Date getfDesde() {
		return fDesde;
	}

	/**
	 * @param fDesde the fDesde to set
	 */
	public final void setfDesde(Date fDesde) {
		this.fDesde = fDesde;
	}

	/**
	 * @return the fHasta
	 */
	public final Date getfHasta() {
		return fHasta;
	}

	/**
	 * @param fHasta the fHasta to set
	 */
	public final void setfHasta(Date fHasta) {
		this.fHasta = fHasta;
	}

	/**
	 * @return the periodo
	 */
	public final Date getPeriodo() {
		return periodo;
	}

	/**
	 * @param periodo the periodo to set
	 */
	public final void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}

	public List<String> getListaEntidad() {
		return listaEntidad;
	}

	public void setListaEntidad(List<String> listaEntidad) {
		this.listaEntidad = listaEntidad;
	}

	public List<String> getListaArea() {
		return listaArea;
	}

	public void setListaArea(List<String> listaArea) {
		this.listaArea = listaArea;
	}

	public List<String> getListaTramite() {
		return listaTramite;
	}

	public void setListaTramite(List<String> listaTramite) {
		this.listaTramite = listaTramite;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTramite() {
		return tramite;
	}

	public void setTramite(String tramite) {
		this.tramite = tramite;
	}

	public List<Integer> getListaVersion() {
		return listaVersion;
	}

	public void setListaVersion(List<Integer> listaVersion) {
		this.listaVersion = listaVersion;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
