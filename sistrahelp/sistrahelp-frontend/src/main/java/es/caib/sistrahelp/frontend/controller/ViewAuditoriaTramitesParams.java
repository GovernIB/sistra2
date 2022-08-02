package es.caib.sistrahelp.frontend.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;

import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.EventoAuditoriaTramitacionLazyDataModel;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

/**
 * La clase ViewAuditoriaTramites.
 */
@ManagedBean
@ViewScoped
public class ViewAuditoriaTramitesParams extends ViewControllerBase {

	/**
	 * helpdesk service.
	 */
	@Inject
	private HelpDeskService helpDeskService;

	/** Paginacion */
	private Integer paginacion;

	/**
	 * lista datos.
	 */
	private LazyDataModel<EventoAuditoriaTramitacion> listaDatos;

	/**
	 * dato seleccionado.
	 */
	private EventoAuditoriaTramitacion datoSeleccionado;

	/**
	 * filtros.
	 */
	private FiltroAuditoriaTramitacion filtros;

	private List<TypeEvento> tiposEventos;

	private String filtroArea;

	private String tramite;

	private String version;

	/**
	 * Inicializa.
	 */
	public void init() {
		UtilJSF.verificarAcceso();

		paginacion = UtilJSF.getPaginacion("viewAuditoriaTramites");

		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(ViewAuditoriaTramites.class));

		filtros = new FiltroAuditoriaTramitacion(convierteListaAreas(), false);

		String sDate1 = "03/10/2000";
		Date date1;
		try {
			date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
			filtros.setFechaDesde(date1);
		} catch (ParseException e) {

		}

		if (tramite != null && !tramite.isEmpty()) {
			filtros.setIdTramite(tramite);
		}

		if (version != null && !version.isEmpty()) {
			filtros.setVersionTramite(Integer.parseInt(version));
		}

		// cargamos los eventos quitando el de purga
		tiposEventos = new ArrayList<>();
		for (final TypeEvento ev : TypeEvento.values()) {
			if (!TypeEvento.PROCESO_PURGA.equals(ev) && !TypeEvento.VALORACION_TRAMITE.equals(ev)) {
				tiposEventos.add(ev);
			}
		}

		this.buscar();
	}

	/**
	 * Filtrar.
	 */
	public void filtrar() {
		// Normaliza filtro
		normalizarFiltro();

		// Reinicia la fila del dataTable
		final DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("form:dataTable");
		dataTable.setFirst(0);

		// Buscar
		this.buscar();
	}

	private void normalizarFiltro() {
		filtros.setIdSesionTramitacion(StringUtils.trim(filtros.getIdSesionTramitacion()));
		filtros.setNif(StringUtils.trim(filtros.getNif()));
		filtros.setNombre(StringUtils.trim(filtros.getNombre()));
		filtros.setIdTramite(StringUtils.trim(filtros.getIdTramite()));
		filtros.setIdProcedimientoCP(StringUtils.trim(filtros.getIdProcedimientoCP()));
		filtros.setCodSia(filtros.getCodSia());
	}

	/**
	 * Buscar.
	 */
	private void buscar() {
		// Filtra
		Long rowCount = (long) 0;
		try {
			rowCount = helpDeskService.countAuditoriaEvento(filtros);
		} catch (Exception e) {

		}

		listaDatos = new EventoAuditoriaTramitacionLazyDataModel(helpDeskService, rowCount, filtros);
		// Quitamos seleccion de dato
		datoSeleccionado = null;

	}

	/**
	 * Consultar.
	 */
	public void consultar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_EVENTO, datoSeleccionado);

		// Muestra dialogo
		UtilJSF.openDialog(DialogAuditoriaTramites.class, TypeModoAcceso.CONSULTA, null, true, 950, 750);
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
		UtilJSF.openHelp("auditoriaTramites");
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
	public LazyDataModel<EventoAuditoriaTramitacion> getListaDatos() {
		return listaDatos;
	}

	/**
	 * Establece el valor de listaDatos.
	 *
	 * @param listaDatos el nuevo valor de listaDatos
	 */
	public void setListaDatos(final LazyDataModel<EventoAuditoriaTramitacion> listaDatos) {
		this.listaDatos = listaDatos;
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

	/**
	 * @return the tramite
	 */
	public final String getTramite() {
		return tramite;
	}

	/**
	 * @param tramite the tramite to set
	 */
	public final void setTramite(String tramite) {
		this.tramite = tramite;
	}

	/**
	 * @return the version
	 */
	public final String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public final void setVersion(String version) {
		this.version = version;
	}

}
