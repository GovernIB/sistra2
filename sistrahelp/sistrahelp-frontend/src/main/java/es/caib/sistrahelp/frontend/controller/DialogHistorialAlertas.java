package es.caib.sistrahelp.frontend.controller;

import es.caib.sistrahelp.core.api.model.Alerta;
import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.DisparadorAlerta;
import es.caib.sistrahelp.core.api.model.HistorialAlerta;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypeIdioma;
import es.caib.sistrahelp.core.api.model.types.TypeModoEvaluacionAlerta;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrahelp.core.api.service.*;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean
@ViewScoped
public class DialogHistorialAlertas extends DialogControllerBase {

    @Inject
    private HistorialAlertaService histAvis;

    @Inject
    private ConfiguracionService confService;

    private HistorialAlerta historialAlerta;

	private String id;

    private boolean check;

	private String entidad;

	private String area;

	private String tramite;

	private Integer version;

	private Date fDesde;

	private Date fHasta;

	private Date periodo;

    private String emails;

	private List<String> listaEventos;

	private String portapapeles;

	private Long codigoAlertaMail;

	private String fechaAntAlertaMail;

	private String idiomaMail;

	private String layout;

	/**
	 * Log.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DialogHistorialAlertas.class);

	/**
	 * Inicializacion.
	 * @throws ParseException
	 */
	public void init() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdfS = new SimpleDateFormat("HH:mm:ss");

		if(codigoAlertaMail == null) {
			historialAlerta = histAvis.loadHistorialAlerta(Long.valueOf(id));
			layout = "../layout/dialogViewLayout.xhtml";
		} else {
			layout = "../layout/mainLayout.xhtml";
			UtilJSF.getSessionBean().setLang(idiomaMail);
			SimpleDateFormat fechaStr = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			try {
				Date fecha = fechaStr.parse(fechaAntAlertaMail);
				historialAlerta = histAvis.getSiguienteHistorialAlerta(codigoAlertaMail, fecha);
			} catch (ParseException e) {
				LOGGER.error("Error parsing fechaAntAlertaMail: {}", fechaAntAlertaMail, e);
			}
		}

		UtilJSF.getSessionBean().setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

        check = isResumenDiario();

		if (!isResumenDiario()) {
			switch(historialAlerta.getTipo()) {
				case "E":
					entidad = historialAlerta.getListaAreas().get(0).split("\\.")[0];
					break;

				case "A":
					entidad = historialAlerta.getListaAreas().get(0).split("\\.")[0];
					area = historialAlerta.getListaAreas().get(0).split("\\.")[1];
					break;

				case "T":
					entidad = historialAlerta.getListaAreas().get(0).split("\\.")[0];
					area = historialAlerta.getListaAreas().get(0).split("\\.")[1];
					tramite = historialAlerta.getTramite();
					break;

				case "V":
					entidad = historialAlerta.getListaAreas().get(0).split("\\.")[0];
					area = historialAlerta.getListaAreas().get(0).split("\\.")[1];
					tramite = historialAlerta.getTramite();
					version = historialAlerta.getVersion();
					break;

			}
			try {
				fDesde = sdf.parse(historialAlerta.getPeriodoEvaluacion().split("-")[0]);
				fHasta = sdf.parse(historialAlerta.getPeriodoEvaluacion().split("-")[1]);
				periodo = sdfS.parse(formatSeconds(historialAlerta.getIntervaloEvaluacion()));
			} catch (ParseException e) {
                LOGGER.error("Error parsing dates for HistorialAlerta id {}", id, e);
			}
		} else {
			fDesde = null;
			fHasta = null;
			periodo = null;
		}

        emails = emailString(historialAlerta.getEmail());

		listaEventos = extraerListaEventos(historialAlerta.getEvento());
	}

	public String urlAuditoriaTramites(String tab) {
		String fechaDesde = "";
		String fechaHasta = "";
		ZonedDateTime fechaAlerta = historialAlerta.getFecha().toInstant()
				.atZone(ZoneId.systemDefault());
		fechaHasta = fechaAlerta.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		if(historialAlerta.getModoEvaluacion().equals(TypeModoEvaluacionAlerta.ACUMULADO)){
			fechaDesde = fechaAlerta.toLocalDate().atStartOfDay().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		} else {
			fechaDesde = LocalDateTime.parse(fechaHasta, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
							.minusMinutes(historialAlerta.getIntervaloEvaluacion())
								.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		}

		try {
            return confService.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAHELP_VIEW_URL)
                    + "/viewAuditoriaTramites.xhtml?esDialogHA=true&eventoHA=" + tab
                    + "&fechaDesdeHA=" + java.net.URLEncoder.encode(fechaDesde, "UTF-8")
                    + "&fechaHastaHA=" + java.net.URLEncoder.encode(fechaHasta, "UTF-8");

		} catch (java.io.UnsupportedEncodingException e) {
			LOGGER.error("Error codificando fechas en la URL", e);
		}

		return null;
	}

	public String getUrlActual() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		return request.getRequestURL().toString();
	}

	private static List<String> extraerListaEventos(String evento) {
		Set<String> valores = new LinkedHashSet<>();
		Pattern pattern = Pattern.compile("\\(\\s*([^\\[]+?)\\s*\\["); // Captura entre paréntesis corchete abiertos
		Matcher matcher = pattern.matcher(evento);
		while (matcher.find()) {
			valores.add(matcher.group(1).trim());
		}
		return new ArrayList<>(valores);
	}

	public boolean isResumenDiario() {
		if (historialAlerta.getNombre() != null) {
			return historialAlerta.getNombre().equals("RESUMEN_DIARIO") || historialAlerta.getNombre().equals("RESUM_DIARI");
		} else {
			return false;

		}
	}

	private static String formatSeconds(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

    public String emailString(List<String> lista) {
        return (lista != null && !lista.isEmpty()) ? String.join(";", lista) : "";
    }

    public String generaTituloTab(String tab) {
        return UtilJSF.getLiteral("typeEvento." + TypeEvento.valueOf(tab));
    }

	/**
	 * Cancelar.
	 */
	public void cancelar() {
		final DialogResult result = new DialogResult();
		result.setCanceled(true);
		UtilJSF.closeDialog(result);
	}

	/** Ayuda. */
	public void ayuda() {
		UtilJSF.openHelp("dialogoHistorialAlertas");
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr(AjaxBehaviorEvent event) {
		if (StringUtils.isEmpty(portapapeles)) {
			copiadoErr(event);
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
		}
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr(AjaxBehaviorEvent event) {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewTramites.copiar"));
	}

    public HistorialAlerta getHistorialAlerta() {
        return historialAlerta;
    }

    public void setHistorialAlerta(HistorialAlerta historialAlerta) {
        this.historialAlerta = historialAlerta;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getfDesde() {
		return fDesde;
	}

	public void setfDesde(Date fDesde) {
		this.fDesde = fDesde;
	}

	public Date getfHasta() {
		return fHasta;
	}

	public void setfHasta(Date fHasta) {
		this.fHasta = fHasta;
	}

	public Date getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Date periodo) {
		this.periodo = periodo;
	}

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

	public List<String> getListaEventos() {
		return listaEventos;
	}

	public void setListaEventos(List<String> listaEventos) {
		this.listaEventos = listaEventos;
	}

	public String getPortapapeles() {
		return portapapeles;
	}

	public void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	public Long getCodigoAlertaMail() {
		return codigoAlertaMail;
	}

	public void setCodigoAlertaMail(Long codigoAlertaMail) {
		this.codigoAlertaMail = codigoAlertaMail;
	}

	public String getFechaAntAlertaMail() {
		return fechaAntAlertaMail;
	}

	public void setFechaAntAlertaMail(String fechaAntAlertaMail) {
		this.fechaAntAlertaMail = fechaAntAlertaMail;
	}

	public String getIdiomaMail() {
		return idiomaMail;
	}

	public void setIdiomaMail(String idiomaMail) {
		this.idiomaMail = idiomaMail;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}
}
