package es.caib.sistrahelp.frontend.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.ErroresCuadroMando;
import es.caib.sistrahelp.core.api.model.ErroresPorTramiteCM;
import es.caib.sistrahelp.core.api.model.ErroresPorTramiteCMRe;
import es.caib.sistrahelp.core.api.model.ErroresTramites;
import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.EventoCM;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.HistorialAlerta;
import es.caib.sistrahelp.core.api.model.ResultadoErroresPorTramiteCM;
import es.caib.sistrahelp.core.api.model.ResultadoEventoCM;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrahelp.core.api.service.ConfiguracionService;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.core.api.service.HistorialAlertaService;
import es.caib.sistrahelp.frontend.model.ErroresPorTramiteCMExpansionLazyDataModel;
import es.caib.sistrahelp.frontend.model.ErroresPorTramiteCMLazyDataModel;
import es.caib.sistrahelp.frontend.model.ErroresPorTramiteCMPlataformaLazyDataModel;
import es.caib.sistrahelp.frontend.model.EventoAuditoriaTramitacionLazyDataModel;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

/**
 * La clase ViewAuditoriaTramites.
 */
@ManagedBean
@ViewScoped
public class ViewCuadroMando extends ViewControllerBase {

	/**
	 * helpdesk service.
	 */
	@Inject
	private HelpDeskService helpDeskService;

	/**
	 * configuracion service.
	 */
	@Inject
	private HistorialAlertaService historialAlertaService;

	/** Acceso configuración. */
	@Inject
	private ConfiguracionService config;

	/** Paginacion */
	private Integer paginacion;

	/** Paginacion */
	private Integer paginacionErrores;

	/** Paginacion */
	private Integer paginacionErroresPlat;

	/** Paginacion */
	private Integer paginacionAlertas;

	/**
	 * lista datos.
	 */
	private List<EventoAuditoriaTramitacion> listaDatos;

	/**
	 * lista datos plataforma.
	 */
	private List<EventoAuditoriaTramitacion> listaDatosPlataforma;

	/**
	 * lista datos.
	 */
	private List<EventoAuditoriaTramitacion> listaDatosEventosPlataforma;
	/**
	 * lista alertas.
	 */
	private List<HistorialAlerta> listaAlertas;

	/**
	 * dato seleccionado.
	 */
	private EventoAuditoriaTramitacion datoSeleccionado;

	/**
	 * filtros.
	 */
	private FiltroAuditoriaTramitacion filtros;

	private FiltroAuditoriaTramitacion filtrosPlataforma;

	private List<TypeEvento> tiposEventos;

	private String filtroArea;

	private String portapapeles;

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

	private LazyDataModel<ErroresPorTramiteCM> listaErrores;

	private LazyDataModel<EventoCM> listaErr;

	private LazyDataModel<EventoCM> listaErrPlat;

	private Integer minutosRefresco;

	private Date horaDesde;

	private Date fechaHasta;

	private Date fechaDesde;

	private String tipoFecha;

	private ErroresCuadroMando seleccionado;

	private Date fechaMin;

	/**
	 * Inicializa.
	 */
	public void init() {
		Integer dias = Integer
				.parseInt(config.obtenerPropiedadConfiguracionSistrages(TypePropiedadConfiguracion.PURGA_PURGADOS));
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date()); // Configuramos la fecha que se recibe
			calendar.add(Calendar.DAY_OF_YEAR, -dias); // numero de días a añadir, o restar en caso de días<0
			fechaMin = calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
		} catch (NumberFormatException e) {
			fechaMin = new Date();
		}

		minutosRefresco = 1;

		UtilJSF.verificarAcceso();

		paginacion = UtilJSF.getPaginacion("viewCuadroMando");
		paginacionErroresPlat = UtilJSF.getPaginacion("viewCuadroMando");
		paginacionErrores = UtilJSF.getPaginacion("viewCuadroMando");
		paginacionAlertas = UtilJSF.getPaginacion("viewCuadroMando");

		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		filtros = new FiltroAuditoriaTramitacion(convierteListaAreas(), false, false);
		filtrosPlataforma = new FiltroAuditoriaTramitacion(convierteListaAreas(), true, false);

		tipoFecha = "tr";
		this.horaDesde = filtros.getToday();
		filtrar();
	}

	/**
	 * Filtrar.
	 */
	public void filtrar() {

		// Normaliza filtro
		if (fechaDesde != null && fechaMin != null && fechaDesde.before(DateUtils.addSeconds(fechaMin, -1))) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("viewCuadroMando.fechaMin", new Object[] { convertirFecha(fechaMin) }));
			return;
		}

		normalizarFiltro();

		// Buscar
		this.buscar();
	}

	public void loadExpansion(ErroresPorTramiteCM errTram) {

		filtros.setIdTramite(errTram.getIdTramite());
		filtros.setVersionTramite(errTram.getVersion());
		Long rowCount = (long) 0;
		filtros.setSoloContar(true);
		ResultadoEventoCM result = helpDeskService.obtenerErroresPorTramiteCMExpansion(filtros, null);
		rowCount = result.getNumElementos();

		filtros.setSoloContar(false);
		listaErr = new ErroresPorTramiteCMExpansionLazyDataModel(helpDeskService, rowCount, filtros);

	}

	private void normalizarFiltro() {
		filtros.setIdSesionTramitacion(StringUtils.trim(filtros.getIdSesionTramitacion()));
		filtros.setNif(StringUtils.trim(filtros.getNif()));
		filtros.setNombre(StringUtils.trim(filtros.getNombre()));
		filtros.setIdTramite(StringUtils.trim(filtros.getIdTramite()));
		filtros.setIdProcedimientoCP(StringUtils.trim(filtros.getIdProcedimientoCP()));
		filtros.setCodSia(filtros.getCodSia());
		filtrosPlataforma.setIdSesionTramitacion(StringUtils.trim(filtros.getIdSesionTramitacion()));
		filtrosPlataforma.setNif(StringUtils.trim(filtros.getNif()));
		filtrosPlataforma.setIdTramite(StringUtils.trim(filtros.getIdTramite()));
		filtrosPlataforma.setIdProcedimientoCP(StringUtils.trim(filtros.getIdProcedimientoCP()));
	}

	public void erroresAuditoriaTramites() {
		final Map<String,List<String>> params = new HashMap<>();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		String idTramite = listaErrores.getRowData().getIdTramite().split("\\.", 3)[2];
		String versionTramite = listaErrores.getRowData().getVersion().toString();

		List<String> idTramiteString = new ArrayList<>();
		List<String> versionTramiteString = new ArrayList<>();
		idTramiteString.add(idTramite);
		versionTramiteString.add(versionTramite);
		params.put("idTramite", idTramiteString);
		params.put("versionTramite", versionTramiteString);

		if(horaDesde != null) {
			List<String> horaDesdeString = new ArrayList<>();
			horaDesdeString.add(dateFormat.format(horaDesde));
			params.put("horaDesde", horaDesdeString);
		}else {
			if(fechaDesde != null) {
				List<String> fechaDesdeString = new ArrayList<>();
				fechaDesdeString.add(dateFormat.format(fechaDesde));
				params.put("fechaDesde", fechaDesdeString);
			}
			if(fechaHasta != null) {
				List<String> fechaHastaString = new ArrayList<>();
				fechaHastaString.add(dateFormat.format(fechaHasta));
				params.put("fechaHasta", fechaHastaString);
			}
		}

		UtilJSF.redirectJsfPage("/secure/app/viewAuditoriaTramites.xhtml", params);
	}

	/** Genera texto a copiar **/
	public void generarTxt() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Copiado correctamente
	 */
	public void copiadoCorr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
	}

	/**
	 * Copiado error
	 */
	public void copiadoErr() {
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
				UtilJSF.getLiteral("viewAuditoriaTramites.headError") + ' ' + UtilJSF.getLiteral("botones.copiar"));
	}

	public void cambiar() {
		if (tipoFecha.equals("tr")) {
			fechaDesde = null;
			fechaHasta = null;
			minutosRefresco = 1;
			horaDesde = filtros.getToday();
			PrimeFaces.current()
					.executeScript("var currentTimePoller = PF('currentTimePoller'); currentTimePoller.start();");
		} else {
			fechaDesde = filtros.calcularFecha();
			fechaHasta = null;
			minutosRefresco = null;
			horaDesde = null;
			PrimeFaces.current()
					.executeScript("var currentTimePoller = PF('currentTimePoller'); currentTimePoller.stop();");
		}
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
		List<String> listaAux = new ArrayList<String>();
		if (tipoFecha.equals("tr")) {
			filtros.setFechaDesde(filtros.getToday());
			filtros.getFechaDesde().setHours(horaDesde.getHours());
			filtros.getFechaDesde().setMinutes(horaDesde.getMinutes());
			filtrosPlataforma.setFechaDesde(filtros.getToday());
			filtrosPlataforma.getFechaDesde().setHours(horaDesde.getHours());
			filtrosPlataforma.getFechaDesde().setMinutes(horaDesde.getMinutes());
		} else if (tipoFecha.equals("iv")) {
			filtros.setFechaDesde(fechaDesde);
			filtros.setFechaHasta(fechaHasta);
			filtrosPlataforma.setFechaDesde(fechaDesde);
			filtrosPlataforma.setFechaHasta(fechaHasta);
		}
		filtros.setIdTramite(null);
		filtros.setVersionTramite(null);
		ResultadoEventoCM resEventoCM = helpDeskService.obtenerCountEventoCM(filtros);

		if (resEventoCM != null && resEventoCM.getListaEventosCM() != null
				&& !resEventoCM.getListaEventosCM().isEmpty()) {
			ponerEventos(resEventoCM.getListaEventosCM());
		}
		// Filtra
		Long rowCount = (long) 0;
		filtros.setSoloContar(true);
		ResultadoErroresPorTramiteCM result = helpDeskService.obtenerErroresPorTramiteCM(filtros, null);
		rowCount = result.getNumElementos();

		filtros.setSoloContar(false);
		listaErrores = new ErroresPorTramiteCMLazyDataModel(helpDeskService, rowCount, filtros);

		Long rowCountPlat = (long) 0;
		filtros.setSoloContar(true);
		ResultadoEventoCM resultPlat = helpDeskService.obtenerErroresPlataformaCM(filtros, null);
		rowCountPlat = resultPlat.getNumElementos();

		filtros.setSoloContar(false);
		listaErrPlat = new ErroresPorTramiteCMPlataformaLazyDataModel(helpDeskService, rowCountPlat, filtros);

		datoSeleccionado = null;

		listaAlertas = historialAlertaService.listHistorialAlerta(filtros.getToday(), null);
	}

	public String calcularEtiquetaTram(Long porcentage, String id) {
		String texto = "";
		if (porcentage <= 15) {
			texto = "Revisar";
			PrimeFaces.current()
					.executeScript("document.getElementById('form:" + id
							+ "').style.backgroundColor='red'; document.getElementById('form:" + id
							+ "').parentNode.style.backgroundColor='red'; document.getElementById('form:" + id
							+ "').style.color='white';");
		} else if (porcentage > 15 && porcentage <= 50) {
			texto = "Atención";
			PrimeFaces.current()
					.executeScript("document.getElementById('form:" + id
							+ "').style.backgroundColor='yellow'; document.getElementById('form:" + id
							+ "').parentNode.style.backgroundColor='yellow';");

		} else if (porcentage > 50) {
			texto = "Normal";
			PrimeFaces.current()
					.executeScript("document.getElementById('form:" + id
							+ "').style.backgroundColor='#40D95E';  document.getElementById('form:" + id
							+ "').parentNode.style.backgroundColor='#40D95E'; document.getElementById('form:" + id
							+ "').style.color='white';");
		}
		return texto;
	}

	private void ponerEventos(List<EventoCM> listaEventos) {
		for (EventoCM ev : listaEventos) {
			switch (TypeEvento.fromString(ev.getTipoEvento())) {
			case REGISTRAR_TRAMITE_INICIO:
				regIni = ev.getConcurrencias().intValue();
				break;
			case REGISTRAR_TRAMITE:
				regFin = ev.getConcurrencias().intValue();
				break;
			case FORMULARIO_INICIO:
				formIni = ev.getConcurrencias().intValue();
				break;
			case FORMULARIO_FIN:
				formFin = ev.getConcurrencias().intValue();
				break;
			case FIRMA_INICIO:
				firmaIni = ev.getConcurrencias().intValue();
				break;
			case FIRMA_FIN:
				firmaFin = ev.getConcurrencias().intValue();
				break;
			case PAGO_ELECTRONICO_INICIO:
				pagIni = ev.getConcurrencias().intValue();
				break;
			case PAGO_ELECTRONICO_VERIFICADO:
				pagFin += ev.getConcurrencias();
				break;
			case PAGO_PRESENCIAL:
				pagFin += ev.getConcurrencias();
				break;
			case INICIAR_TRAMITE:
				tramIni = ev.getConcurrencias().intValue();
				break;
			case FIN_TRAMITE:
				tramFin = ev.getConcurrencias().intValue();
				break;
			case ERROR:
				errTot = ev.getConcurrencias().intValue();
				break;
			default:
				break;
			}
		}
	}

	public String convertirFecha(Date fecha) {
		return new SimpleDateFormat("dd-MM-YYYY HH:mm:ss").format(fecha);
	}

	public String formatDouble(Double dbl) {
		return new DecimalFormat("0.00").format(dbl);
	}

	/**
	 *
	 */
	public String split(String idComp) {
		return idComp.split("\\.")[2];
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("cuadroMando");
	}

	/**
	 * Obtiene el valor de filaSeleccionada.
	 *
	 * @return el valor de filaSeleccionada
	 */
	public boolean getFilaSeleccionada() {
		return verificarFilaSeleccionada();
	}

	/**
	 * Verificar fila seleccionada.
	 *
	 * @return true, if successful
	 */
	private boolean verificarFilaSeleccionada() {
		boolean filaSeleccionada = true;
		if (this.datoSeleccionado == null) {
			UtilJSF.showMessageDialog(TypeNivelGravedad.WARNING, "", UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Convierte lista areas.
	 *
	 * @return the lista de
	 */
	private List<String> convierteListaAreas() {
		List<String> resultado = null;

		final List<Area> lista = UtilJSF.getSessionBean().getListaAreasEntidad();

		if (lista != null && !lista.isEmpty()) {
			resultado = new ArrayList<>();
			for (final Area area : lista) {
				resultado.add(area.getIdentificador());
			}
		}

		return resultado;

	}

	/**
	 * Obtiene el valor de listaDatos.
	 *
	 * @return el valor de listaDatos
	 */
	public List<EventoAuditoriaTramitacion> getListaDatos() {
		return listaDatos;
	}

	/**
	 * Establece el valor de listaDatos.
	 *
	 * @param listaDatos el nuevo valor de listaDatos
	 */
	public void setListaDatos(final List<EventoAuditoriaTramitacion> listaDatos) {
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
	 * Obtiene el valor de datoSeleccionado.
	 *
	 * @return el valor de datoSeleccionado
	 */
	public EventoAuditoriaTramitacion getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final EventoAuditoriaTramitacion datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * Obtiene el valor de filtros.
	 *
	 * @return el valor de filtros
	 */
	public FiltroAuditoriaTramitacion getFiltros() {
		return filtros;
	}

	/**
	 * Establece el valor de filtros.
	 *
	 * @param filtros el nuevo valor de filtros
	 */
	public void setFiltros(final FiltroAuditoriaTramitacion filtros) {
		this.filtros = filtros;
	}

	public List<TypeEvento> getTiposEventos() {
		return tiposEventos;
	}

	public void setTiposEventos(final List<TypeEvento> tiposEventos) {
		this.tiposEventos = tiposEventos;
	}

	/**
	 * @return the paginacion
	 */
	public final Integer getPaginacion() {
		return paginacion;
	}

	/**
	 * @param paginacion the paginacion to set
	 */
	public final void setPaginacion(Integer paginacion) {
		this.paginacion = paginacion;
		UtilJSF.setPaginacion(paginacion, "viewAuditoriaTramites");
	}

	/**
	 * @return the filtroArea
	 */
	public final String getFiltroArea() {
		return filtroArea;
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
	 * @param filtroArea the filtroArea to set
	 */
	public final void setFiltroArea(String filtroArea) {
		List<String> areasEnt = convierteListaAreas();
		if (filtroArea != null && !filtroArea.isEmpty() && areasEnt != null) {
			filtros.getListaAreas().clear();
			for (String ar : areasEnt) {
				Pattern pattern = Pattern.compile(filtroArea, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(ar.split("\\.")[1]);
				boolean matchFound = matcher.find();
				if (matchFound) {
					filtros.getListaAreas().add(ar);
				}
			}
		} else {
			filtros.setListaAreas(areasEnt);
		}
	}

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	public final int getTramIni() {
		return tramIni;
	}

	public final void setTramIni(int tramIni) {
		this.tramIni = tramIni;
	}

	public final int getTramFin() {
		return tramFin;
	}

	public final void setTramFin(int tramFin) {
		this.tramFin = tramFin;
	}

	public final int getErrTot() {
		return errTot;
	}

	public final void setErrTot(int errTot) {
		this.errTot = errTot;
	}

	public final Integer getMinutosRefresco() {
		return minutosRefresco;
	}

	public final void setMinutosRefresco(Integer minutosRefresco) {
		if (minutosRefresco > 0) {
			this.minutosRefresco = minutosRefresco;
		} else if (minutosRefresco == 0) {
			this.minutosRefresco = minutosRefresco;
			PrimeFaces.current()
					.executeScript("var currentTimePoller = PF('currentTimePoller'); currentTimePoller.stop();");
		}
	}

	public final Date getHoraDesde() {
		return horaDesde;
	}

	public final void setHoraDesde(Date horaDesde) {
		this.horaDesde = horaDesde;
	}

	public final List<HistorialAlerta> getListaAlertas() {
		return listaAlertas;
	}

	public final void setListaAlertas(List<HistorialAlerta> listaAlertas) {
		this.listaAlertas = listaAlertas;
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

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(final Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(final Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public final String getTipoFecha() {
		return tipoFecha;
	}

	public final void setTipoFecha(String tipoFecha) {
		this.tipoFecha = tipoFecha;
	}

	public final ErroresCuadroMando getSeleccionado() {
		return seleccionado;
	}

	public final void setSeleccionado(ErroresCuadroMando seleccionado) {
		this.seleccionado = seleccionado;
	}

	public final Date getFechaMin() {
		return fechaMin;
	}

	public final void setFechaMin(Date fechaMin) {
		this.fechaMin = fechaMin;
	}

	public final List<EventoAuditoriaTramitacion> getListaDatosPlataforma() {
		return listaDatosPlataforma;
	}

	public final void setListaDatosPlataforma(List<EventoAuditoriaTramitacion> listaDatosPlataforma) {
		this.listaDatosPlataforma = listaDatosPlataforma;
	}

	public final LazyDataModel<ErroresPorTramiteCM> getListaErrores() {
		return listaErrores;
	}

	public final void setListaErrores(LazyDataModel<ErroresPorTramiteCM> listaErrores) {
		this.listaErrores = listaErrores;
	}

	public final Integer getPaginacionErrores() {
		return paginacionErrores;
	}

	public final void setPaginacionErrores(Integer paginacionErrores) {
		this.paginacionErrores = paginacionErrores;
	}

	public final Integer getPaginacionAlertas() {
		return paginacionAlertas;
	}

	public final void setPaginacionAlertas(Integer paginacionAlertas) {
		this.paginacionAlertas = paginacionAlertas;
	}

	public final LazyDataModel<EventoCM> getListaErr() {
		return listaErr;
	}

	public final void setListaErr(LazyDataModel<EventoCM> listaErr) {
		this.listaErr = listaErr;
	}

	public final Integer getPaginacionErroresPlat() {
		return paginacionErroresPlat;
	}

	public final void setPaginacionErroresPlat(Integer paginacionErroresPlat) {
		this.paginacionErroresPlat = paginacionErroresPlat;
	}

	public final LazyDataModel<EventoCM> getListaErrPlat() {
		return listaErrPlat;
	}

	public final void setListaErrPlat(LazyDataModel<EventoCM> listaErrPlat) {
		this.listaErrPlat = listaErrPlat;
	}

}
