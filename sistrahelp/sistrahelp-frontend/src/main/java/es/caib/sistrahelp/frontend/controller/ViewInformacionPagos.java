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
import es.caib.sistrahelp.core.api.model.FiltroAuditoriaPago;
import es.caib.sistrahelp.core.api.model.PagoAuditoria;
import es.caib.sistrahelp.core.api.model.comun.Constantes;
import es.caib.sistrahelp.core.api.model.types.TypeEstadoPago;
import es.caib.sistrahelp.core.api.model.types.TypePresentacion;
import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.PagoAuditoriaLazyDataModel;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.util.UtilJSF;

/**
 * La clase ViewAuditoriaTramites.
 */
@ManagedBean
@ViewScoped
public class ViewInformacionPagos extends ViewControllerBase {

	/**
	 * helpdesk service.
	 */
	@Inject
	private HelpDeskService helpDeskService;

	/**
	 * lista datos.
	 */
	private LazyDataModel<PagoAuditoria> listaDatos;

	/**
	 * dato seleccionado.
	 */
	private PagoAuditoria datoSeleccionado;

	/** Paginacion */
	private Integer paginacion;

	/**
	 * filtros.
	 */
	private FiltroAuditoriaPago filtros;

	/**
	 * Inicializa.
	 */
	public void init() {
		UtilJSF.verificarAcceso();

		paginacion = UtilJSF.getPaginacion("viewInformacionPagos");

		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		filtros = new FiltroAuditoriaPago(convierteListaAreas());
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
	}

	/**
	 * Buscar.
	 */
	private void buscar() {
		// Filtra

		final Long rowCount = helpDeskService.countAuditoriaPago(filtros);
		listaDatos = new PagoAuditoriaLazyDataModel(helpDeskService, rowCount, filtros);

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

		int alto = 350;
		UtilJSF.getSessionBean().limpiaMochilaDatos();
		final Map<String, Object> mochila = UtilJSF.getSessionBean().getMochilaDatos();
		mochila.put(Constantes.CLAVE_MOCHILA_PAGO, datoSeleccionado);

		if (TypeEstadoPago.EN_CURSO.equals(datoSeleccionado.getEstadoPago()) && TypePresentacion.ELECTRONICA
				.equals(TypePresentacion.fromString(datoSeleccionado.getPresentacion()))) {
			alto = 500;
		}
		// Muestra dialogo
		UtilJSF.openDialog(DialogInformacionPagos.class, TypeModoAcceso.CONSULTA, null, true, 900, alto);
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
		UtilJSF.openHelp("informacionPagos");
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
	public LazyDataModel<PagoAuditoria> getListaDatos() {
		return listaDatos;
	}

	/**
	 * Establece el valor de listaDatos.
	 *
	 * @param listaDatos el nuevo valor de listaDatos
	 */
	public void setListaDatos(final LazyDataModel<PagoAuditoria> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * Obtiene el valor de datoSeleccionado.
	 *
	 * @return el valor de datoSeleccionado
	 */
	public PagoAuditoria getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * Establece el valor de datoSeleccionado.
	 *
	 * @param datoSeleccionado el nuevo valor de datoSeleccionado
	 */
	public void setDatoSeleccionado(final PagoAuditoria datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * Obtiene el valor de filtros.
	 *
	 * @return el valor de filtros
	 */
	public FiltroAuditoriaPago getFiltros() {
		return filtros;
	}

	/**
	 * Establece el valor de filtros.
	 *
	 * @param filtros el nuevo valor de filtros
	 */
	public void setFiltros(final FiltroAuditoriaPago filtros) {
		this.filtros = filtros;
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
		UtilJSF.setPaginacion(paginacion, "viewInformacionPagos");
	}
}
