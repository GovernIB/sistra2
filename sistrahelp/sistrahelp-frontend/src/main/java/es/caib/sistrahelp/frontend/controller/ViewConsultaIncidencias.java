package es.caib.sistrahelp.frontend.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;

import es.caib.sistrahelp.core.api.model.ResultadoSoporte;
import es.caib.sistrahelp.core.api.model.Soporte;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.SoporteLazyDataModel;
import es.caib.sistrahelp.frontend.model.types.TypeEstadoIncidencia;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

/**
 * La clase ViewEventosPlataforma.
 */
@ManagedBean
@ViewScoped
public class ViewConsultaIncidencias extends ViewControllerBase {

	/**
	 * helpdesk service.
	 */
	@Inject
	private HelpDeskService hService;

	/** Paginacion */
	private Integer paginacion;

	/**
	 * lista datos.
	 */
	private LazyDataModel<Soporte> listaDatos;

	/**
	 * dato seleccionado.
	 */
	private Soporte datoSeleccionado;

	/**
	 * filtros.
	 */
	private FiltroAuditoriaTramitacion filtros;

	private List<TypeEstadoIncidencia> tiposEstados;

	private String filtroArea;

	private String filtroTipoProblema;

	private List<String> listaTiposProblema;

	private List<String> listaAreas;

	/**
	 * Inicializa.
	 *
	 */
	public void init() {
		UtilJSF.verificarAcceso();
		paginacion = UtilJSF.getPaginacion("viewConsultaIncidencias");
		filtros = new FiltroAuditoriaTramitacion(convierteListaAreas(), false, true);
		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		tiposEstados = new ArrayList<>();
		for (final TypeEstadoIncidencia ev : TypeEstadoIncidencia.values()) {
			tiposEstados.add(ev);
		}

		listaTiposProblema = hService.obtenerTiposProblemaSoporte();

		listaAreas = new ArrayList<>();

		for (String area : convierteListaAreas()) {
			listaAreas.add(area.split("\\.", 2)[1]);
		}

		if(listaAreas != null) {
			listaAreas.sort(null);
		}

		this.buscar();

	}

	/**
	 * Filtrar.
	 */
	public void filtrar() {
		// normalizarFiltro();
		// Buscar
		buscar();
	}

	/**
	 * Buscar.
	 */
	private void buscar() {
		// Filtra
		Long rowCount = (long) 0;
		filtros.setSoloContar(true);
		ResultadoSoporte result = hService.obtenerFormularioSoporte(filtros, null);
		rowCount = result.getNumElementos();

		filtros.setSoloContar(false);
		listaDatos = new SoporteLazyDataModel(hService, rowCount, filtros);

		// Quitamos seleccion de dato
		datoSeleccionado = null;
	}

	/**
	 * Abre dialogo.
	 *
	 * @param modoAccesoDlg Modo acceso
	 */
	private void abrirDlg(final TypeModoAcceso modoAccesoDlg) {

		// Verifica si no hay fila seleccionada
		if (modoAccesoDlg != TypeModoAcceso.ALTA && !verificarFilaSeleccionada()) {
			return;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		if (modoAccesoDlg != TypeModoAcceso.ALTA) {
			UtilJSF.getSessionBean().limpiaMochilaDatos();
			final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
			mochila.put(Constantes.CLAVE_MOCHILA_SOPORTE, datoSeleccionado);
		}

		UtilJSF.openDialog(DialogConsultaIncidencias.class, modoAccesoDlg, null, true, 620, 725);

	}

	/**
	 * Abre dialogo.
	 *
	 * @param modoAccesoDlg Modo acceso
	 */
	public void abrirDlgSesion() {

		final Map<String, String> params = new HashMap<>();

		String idSesion = datoSeleccionado.getSesionTramitacion();

		params.put("idSesionParam", idSesion);
		params.put("esDialogParams", "true");

		UtilJSF.openDialog(ViewAuditoriaTramites.class, TypeModoAcceso.CONSULTA, params, true, 1500, 717);

	}

	/**
	 * Abre dialogo.
	 *
	 * @param modoAccesoDlg Modo acceso
	 */
	public void abrirDlgNif() {

		final Map<String, String> params = new HashMap<>();

		String nif = datoSeleccionado.getNif();

		params.put("nifParam", nif);
		params.put("esDialogParams", "true");

		UtilJSF.openDialog(ViewAuditoriaTramites.class, TypeModoAcceso.CONSULTA, params, true, 1500, 717);

	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void consultar() {

		abrirDlg(TypeModoAcceso.EDICION);
	}

	private void normalizarFiltro() {
		filtros.setIdSesionTramitacion(StringUtils.trim(filtros.getIdSesionTramitacion()));
		filtros.setNif(StringUtils.trim(filtros.getNif()));
		filtros.setNombre(StringUtils.trim(filtros.getNombre()));
		filtros.setIdTramite(StringUtils.trim(filtros.getIdTramite()));
		filtros.setIdProcedimientoCP(StringUtils.trim(filtros.getIdProcedimientoCP()));
		filtros.setEmail(StringUtils.trim(filtros.getEmail()));
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
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada()) {
			return;
		}

		// Inactivamos
		// hService.updateAlerta(this.datoSeleccionado);
		// Refrescamos datos
		filtrar();

	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				/*
				 * if (re.getCodigo() != 1) { message = UtilJSF.getLiteral("info.alta.ok") +
				 * ". " + UtilJSF.getLiteral("error.refrescarCache") + ": " + re.getMensaje(); }
				 * else { message = UtilJSF.getLiteral("info.alta.ok") + ". " +
				 * UtilJSF.getLiteral("info.cache.ok"); }
				 */
				// UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
				// UtilJSF.getLiteral("info.alta.ok"));
			} else {
				String message = null;
				if (respuesta != null && respuesta.getResult() != null) {
					UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.modificado.ok"));
				}
			}

			// Refrescamos datos
			buscar();
		}

	}

	public String parseSecs(Integer segundos) {
		String aux = "";
		if (segundos != null) {
			aux = formatSeconds(segundos);
		}
		return aux;
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

	/**
	 * Rc doble click.
	 */
	public void rcDobleClick() {
		consultar();
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("viewIncidencias");
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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Obtiene el valor de listaDatos.
	 *
	 * @return el valor de listaDatos
	 */
	public LazyDataModel<Soporte> getListaDatos() {
		return listaDatos;
	}

	/**
	 * Establece el valor de listaDatos.
	 *
	 * @param listaDatos el nuevo valor de listaDatos
	 */
	public void setListaDatos(final LazyDataModel<Soporte> listaDatos) {
		this.listaDatos = listaDatos;
	}

	public List<TypeEstadoIncidencia> getTiposEstados() {
		return tiposEstados;
	}

	public void setTiposEstados(List<TypeEstadoIncidencia> tiposEstados) {
		this.tiposEstados = tiposEstados;
	}

	/**
	 * Obtiene el valor de datoSeleccionado.
	 *
	 * @return el valor de datoSeleccionado
	 */
	public Soporte getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final Soporte datoSeleccionado) {
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
	public void setFiltros(FiltroAuditoriaTramitacion filtros) {
		this.filtros = filtros;
	}

	/**
	 * @return the paginacion
	 */
	public final Integer getPaginacion() {
		return paginacion;
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

	/**
	 * @param paginacion the paginacion to set
	 */
	public final void setPaginacion(Integer paginacion) {
		this.paginacion = paginacion;
		UtilJSF.setPaginacion(paginacion, "viewEventosPlataforma");
	}

	private String portapapeles;

	/**
	 * @return the portapapeles
	 */
	public final String getPortapapeles() {
		return portapapeles;
	}

	/**
	 * @param portapapeles the portapapeles to set
	 */
	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	/**
	 * @return the filtroArea
	 */
	public String getFiltroArea() {
		return filtroArea;
	}

	/**
	 * @param filtroArea the filtroArea to set
	 */
	public void setFiltroArea(String filtroArea) {
		List<String> areasEnt = convierteListaAreas();
		if (filtroArea != null && !filtroArea.isEmpty() && areasEnt != null) {
			filtros.getListaAreas().clear();
			for (String ar : areasEnt) {
//				Pattern pattern = Pattern.compile(filtroArea, Pattern.CASE_INSENSITIVE);
//				Matcher matcher = pattern.matcher(ar.split("\\.")[1]);
//				boolean matchFound = matcher.find();
				boolean matchFound = ar.split("\\.")[1].equals(filtroArea);
				if (matchFound) {
					filtros.getListaAreas().add(ar);
				}
			}
		} else {
			filtros.setListaAreas(areasEnt);
		}
	}

	/**
	 * @return the filtroTipoProblema
	 */
	public String getFiltroTipoProblema() {
		return filtroTipoProblema;
	}

	/**
	 * @param filtroTipoProblema the filtroTipoProblema to set
	 */
	public void setFiltroTipoProblema(String filtroTipoProblema) {
		this.filtroTipoProblema = filtroTipoProblema;
	}

	/**
	 * @return the listaTiposProblema
	 */
	public List<String> getListaTiposProblema() {
		return listaTiposProblema;
	}

	/**
	 * @param listaTiposProblema the listaTiposProblema to set
	 */
	public void setListaTiposProblema(List<String> listaTiposProblema) {
		this.listaTiposProblema = listaTiposProblema;
	}

	/**
	 * @return the listaAreas
	 */
	public List<String> getListaAreas() {
		return listaAreas;
	}

	/**
	 * @param listaAreas the listaAreas to set
	 */
	public void setListaAreas(List<String> listaAreas) {
		this.listaAreas = listaAreas;
	}
}
