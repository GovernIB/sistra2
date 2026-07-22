package es.caib.sistrahelp.frontend.controller;

import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.caib.sistrahelp.core.api.model.types.TypeEvento;
import es.caib.sistrahelp.core.api.service.EventoService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.NavegacionEventos;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.EventoAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaTramitacion;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.EventoAuditoriaTramitacionLazyDataModel;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

/**
 * La clase ViewEventosPlataforma.
 */
@ManagedBean
@ViewScoped
public class ViewEventosPlataforma extends ViewControllerBase {

	/**
	 * helpdesk service.
	 */
	@Inject
	private HelpDeskService helpDeskService;

	@Inject
	private EventoService eventoService;

	/** Paginacion */
	private Integer paginacion;

	/**
	 * lista datos.
	 */
	private EventoAuditoriaTramitacionLazyDataModel listaDatos;

	/**
	 * dato seleccionado.
	 */
	private EventoAuditoriaTramitacion datoSeleccionado;

	/**
	 * filtros.
	 */
	private FiltroAuditoriaTramitacion filtros;

	// Campos dialogo errores
	private boolean checkTipoErrorDE = true;
	private boolean checkTextoTrazaDE = false;
	private String erroresSeleccionadosDE;
	private String filtroTextoTrazaDE;
	private String filtroTablaErroresDE;

	/**
	 * Inicializa.
	 */
	public void init() {
		paginacion = UtilJSF.getPaginacion("viewEventosPlataforma");

		UtilJSF.verificarAcceso();

		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		filtros = new FiltroAuditoriaTramitacion(convierteListaAreas(), true);

		filtros.setTiposEventos(Arrays.asList(TypeEvento.ERROR, TypeEvento.PROCESO_PURGA, TypeEvento.VALORACION_TRAMITE) );
	}

	/**
	 * Filtrar.
	 */
	public void filtrar() {
		// Resetear filtro errores
		resetFiltroErrores();

		// Normaliza filtro
		normalizarFiltro();

		// Buscar
		this.buscar();
	}

	private void normalizarFiltro() {
		filtros.setIdSesionTramitacion(StringUtils.trim(filtros.getIdSesionTramitacion()));
		filtros.setNif(StringUtils.trim(filtros.getNif()));
		filtros.setIdTramite(StringUtils.trim(filtros.getIdTramite()));
		filtros.setIdProcedimientoCP(StringUtils.trim(filtros.getIdProcedimientoCP()));
	}

	private void resetFiltroErrores() {
		if ( !TypeEvento.ERROR.equals(this.filtros.getEvento()) ) {
			setCheckTipoErrorDE(true);
			setCheckTextoTrazaDE(false);
			filtros.setTiposErrores(null);
			filtros.setTextoTraza(null);
			setErroresSeleccionadosDE(null);
			setFiltroTextoTrazaDE(null);
		}
	}

	/**
	 * Buscar.
	 */
	private void buscar() {
		// Filtra
		final Long rowCount = helpDeskService.countAuditoriaEvento(filtros);
		listaDatos = new EventoAuditoriaTramitacionLazyDataModel(helpDeskService, rowCount, filtros);
		// Quitamos seleccion de dato
		datoSeleccionado = null;
	}

	/**
	 * Consultar.
	 */
	public void consultar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada()) {
			return;
		}
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_EVENTO, datoSeleccionado);

		// Para un futuro si se quiere añadir navegacion entre eventos
//		NavegacionEventos navegacionEventos = new NavegacionEventos();
//
//		navegacionEventos.setEventos(listaDatos.getWrappedData());
//		navegacionEventos.setPageSize(listaDatos.getPageSize());
//		navegacionEventos.setFirst(listaDatos.getFirst());
//		navegacionEventos.setTotal(listaDatos.getRowCount());
//
//		mochila.put(Constantes.CLAVE_MOCHILA_EVENTO_NAVEGACION, navegacionEventos);

		// Muestra dialogo
		UtilJSF.openDialog(DialogAuditoriaTramites.class, TypeModoAcceso.CONSULTA, null, true, 910, 700);
	}

	/**
	 * Rc doble click.
	 */
	public void rcDobleClick() {
		consultar();
	}

	/**
	 * Abre dialogo errores
	 */
	public void abrirDialogErrores() {
		Map<String, String> params = new HashMap<>();
		if (this.filtros.getIdSesionTramitacion() != null) params.put("fSesion", this.filtros.getIdSesionTramitacion());
		if (this.filtros.getNif() != null) params.put("fNif", this.filtros.getNif());
		if (this.filtros.getNombre() != null) params.put("fNombre", this.filtros.getNombre());
		if (this.filtros.getIdTramite() != null) params.put("fIdTra", this.filtros.getIdTramite());
		if (this.filtros.getVersionTramite() != null) params.put("fVer", String.valueOf(this.filtros.getVersionTramite()));
		if (this.filtros.getCodSia() != null) params.put("fSia", this.filtros.getCodSia());
		if (this.filtros.getIdProcedimientoCP() != null) params.put("fProc", this.filtros.getIdProcedimientoCP());
		if (this.filtros.getFechaDesde() != null) params.put("fDesde", String.valueOf(this.filtros.getFechaDesde().getTime()));
		if (this.filtros.getFechaHasta() != null) params.put("fHasta", String.valueOf(this.filtros.getFechaHasta().getTime()));
		params.put("tipoEvento", TypeEvento.ERROR.name());
		params.put("eventoPlataforma", Boolean.TRUE.toString());
		params.put("checkTipoError", String.valueOf(checkTipoErrorDE));
		params.put("checkTextoTraza", String.valueOf(checkTextoTrazaDE));
		if (erroresSeleccionadosDE != null) {
			params.put("erroresSeleccionadosInit", erroresSeleccionadosDE);
		}
		if (filtroTextoTrazaDE != null) {
			params.put("filtroTextoTraza", filtroTextoTrazaDE);
		}
		if (filtroTablaErroresDE != null) {
			params.put("filtroTablaErrores", filtroTablaErroresDE);
		}
		UtilJSF.openDialog(DialogFiltroErrores.class, TypeModoAcceso.CONSULTA, params.isEmpty() ? null : params, true, 650, 662);
	}

	public void onDialogErroresReturn(final SelectEvent event) {
		if (event == null || event.getObject() == null) {
			return;
		}
		DialogResult result = (DialogResult) event.getObject();
		if (result.isCanceled()) {
			return;
		}
		Map<String, Object> valores = (Map<String, Object>) result.getResult();
		if (valores != null) {
			setCheckTipoErrorDE((boolean) valores.get("checkTipoError"));
			setCheckTextoTrazaDE((boolean) valores.get("checkTextoTraza"));
			setFiltroTablaErroresDE((String) valores.get("filtroTablaErrores"));

			List<String> tiposErrores = (List<String>) valores.get("tiposErrorSeleccionados");
			List<String> tiposErroresRaw = tiposErrores != null ? new ArrayList<>(tiposErrores) : null;
			if (tiposErrores != null) {
				List<String> tiposErroresNormalizados = new ArrayList<>();
				for (String tipoError : tiposErrores) {
					if (tipoError != null && !tipoError.isEmpty()) {
						tiposErroresNormalizados.add(tipoError);
					}
				}
				tiposErrores = tiposErroresNormalizados;
			}

			if (tiposErrores == null || tiposErrores.isEmpty()) {
				setErroresSeleccionadosDE(null);
				filtros.setTiposErrores(null);
			} else {
				setErroresSeleccionadosDE(String.join(";", tiposErrores));
				if (checkTipoErrorDE) {
					filtros.setTiposErrores(tiposErrores);
				} else {
					filtros.setTiposErrores(null);
				}
			}

			String filtroTextoTraza = (String) valores.get("filtroTextoTraza");
			if (filtroTextoTraza != null) {
				setFiltroTextoTrazaDE(filtroTextoTraza);
				if (filtroTextoTraza.isEmpty() || !checkTextoTrazaDE) {
					filtros.setTextoTraza(null);
				} else {
					filtros.setTextoTraza(filtroTextoTraza);
				}
			}
		}
		PrimeFaces.current().executeScript("document.getElementById('form:btnBuscar').click()");
	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("eventosPlataforma");
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
	public EventoAuditoriaTramitacionLazyDataModel getListaDatos() {
		return listaDatos;
	}

	/**
	 * Establece el valor de listaDatos.
	 *
	 * @param listaDatos el nuevo valor de listaDatos
	 */
	public void setListaDatos(final EventoAuditoriaTramitacionLazyDataModel listaDatos) {
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

		if (portapapeles.equals("") || portapapeles.equals(null)) {
			copiadoErr();
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.copiado.ok"));
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
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewAuditoriaTramites.copiar"));
	}

	/**
	 * @param paginacion the paginacion to set
	 */
	public final void setPaginacion(Integer paginacion) {
		this.paginacion = paginacion;
		UtilJSF.setPaginacion(paginacion, "viewEventosPlataforma");
	}

	private String portapapeles;

	private String errorCopiar;

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

	public Integer getTamanyoTabla() {
		if (this.listaDatos != null) {
			return this.listaDatos.getRowCount();
		} else {
			return null;
		}
	}

	public boolean isCheckTipoErrorDE() {
		return checkTipoErrorDE;
	}

	public void setCheckTipoErrorDE(boolean checkTipoErrorDE) {
		this.checkTipoErrorDE = checkTipoErrorDE;
	}

	public boolean isCheckTextoTrazaDE() {
		return checkTextoTrazaDE;
	}

	public void setCheckTextoTrazaDE(boolean checkTextoTrazaDE) {
		this.checkTextoTrazaDE = checkTextoTrazaDE;
	}

	public String getErroresSeleccionadosDE() {
		return erroresSeleccionadosDE;
	}

	public void setErroresSeleccionadosDE(String erroresSeleccionadosDE) {
		this.erroresSeleccionadosDE = erroresSeleccionadosDE;
	}

	public String getFiltroTextoTrazaDE() {
		return filtroTextoTrazaDE;
	}

	public void setFiltroTextoTrazaDE(String filtroTextoTrazaDE) {
		this.filtroTextoTrazaDE = filtroTextoTrazaDE;
	}

	public String getFiltroTablaErroresDE() {
		return filtroTablaErroresDE;
	}

	public void setFiltroTablaErroresDE(String filtroTablaErroresDE) {
		this.filtroTablaErroresDE = filtroTablaErroresDE;
	}
}
