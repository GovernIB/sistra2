package es.caib.sistrahelp.frontend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.LazyDataModel;

import es.caib.sistrahelp.core.api.model.Area;
import es.caib.sistrahelp.core.api.model.FiltroPersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.PersistenciaAuditoria;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.PersistenciaLazyDataModel;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

/**
 * La clase ViewInformacionPersistencia.
 */
@ManagedBean
@ViewScoped
public class ViewInformacionPersistencia extends ViewControllerBase {

	/**
	 * helpdesk service.
	 */
	@Inject
	private HelpDeskService helpDeskService;

	/**
	 * lista datos.
	 */
	private LazyDataModel<PersistenciaAuditoria> listaDatos;

	/**
	 * dato seleccionado.
	 */
	private PersistenciaAuditoria datoSeleccionado;

	/**
	 * filtros.
	 */
	private FiltroPersistenciaAuditoria filtros;

	/**
	 * Inicializa.
	 */
	public void init() {
		UtilJSF.verificarAcceso();

		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		filtros = new FiltroPersistenciaAuditoria(convierteListaAreas());

	}

	/**
	 * Filtrar.
	 */
	public void filtrar() {
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

	/**
	 * Buscar.
	 */
	private void buscar() {
		// Filtra
		final Long rowCount = helpDeskService.countAuditoriaPersistencia(filtros);
		listaDatos = new PersistenciaLazyDataModel(helpDeskService, rowCount, filtros);
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
		mochila.put(Constantes.CLAVE_MOCHILA_PERSISTENCIA, datoSeleccionado);

		// Muestra dialogo
		UtilJSF.openDialog(DialogInformacionPersistencia.class, TypeModoAcceso.CONSULTA, null, true, 900, 600);
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
		UtilJSF.openHelp("informacionPersistencia");
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
	public LazyDataModel<PersistenciaAuditoria> getListaDatos() {
		return listaDatos;
	}

	/**
	 * Establece el valor de listaDatos.
	 *
	 * @param listaDatos
	 *            el nuevo valor de listaDatos
	 */
	public void setListaDatos(final LazyDataModel<PersistenciaAuditoria> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * Obtiene el valor de datoSeleccionado.
	 *
	 * @return el valor de datoSeleccionado
	 */
	public PersistenciaAuditoria getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado
	 *            el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final PersistenciaAuditoria datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * Obtiene el valor de filtros.
	 *
	 * @return el valor de filtros
	 */
	public FiltroPersistenciaAuditoria getFiltros() {
		return filtros;
	}

	/**
	 * Establece el valor de filtros.
	 *
	 * @param filtros
	 *            el nuevo valor de filtros
	 */
	public void setFiltros(final FiltroPersistenciaAuditoria filtros) {
		this.filtros = filtros;
	}

}
