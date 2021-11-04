package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.ConfiguracionGlobal;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.service.ConfiguracionGlobalService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.ResultadoError;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilRest;

/**
 * Mantenimiento de propiedades de configuracion.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewPropiedadesConfiguracion extends ViewControllerBase {

	@Inject
	private ConfiguracionGlobalService configuracionGlobalService;

	@Inject
	private SystemService systemService;

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Lista de datos.
	 */
	private List<ConfiguracionGlobal> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private ConfiguracionGlobal datoSeleccionado;

	/**
	 * Inicializacion.
	 */
	public void init() {

		// Verificamos acceso
		UtilJSF.verificarAccesoSuperAdministrador();

		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		buscar();
	}

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {
		// Normaliza filtro
		filtro = normalizarFiltro(filtro);
		// Buscar
		buscar();
	}

	/**
	 * El autentico buscador.
	 */
	private void buscar() {
		// Filtra
		listaDatos = configuracionGlobalService.listConfiguracionGlobal(filtro);
		// Quitamos seleccion de dato
		datoSeleccionado = null;
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {
		UtilJSF.openDialog(DialogPropiedadConfiguracion.class, TypeModoAcceso.ALTA, null, true, 550, 380);
	}

	/**
	 * Refrescar.
	 */
	public void refrescar() {
		final String urlBase = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_URL.toString());
		final String usuario = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_USER.toString());
		final String pwd = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_PWD.toString());

		final ResultadoError resultado = UtilRest.refrescar(urlBase, usuario, pwd, "C", null);
		if (resultado.getCodigo() == 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.refrescar"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("error.refrescar") + ": " + resultado.getMensaje());
		}

	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));
		UtilJSF.openDialog(DialogPropiedadConfiguracion.class, TypeModoAcceso.EDICION, params, true, 550, 380);
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;
		// Eliminamos
		configuracionGlobalService.removeConfiguracionGlobal(datoSeleccionado.getCodigo());
		// Refrescamos datos
		buscar();
		// Mostramos mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
	}

	/**
	 * Verifica si hay fila seleccionada.
	 *
	 * @return
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
	 * Retorno dialogo.
	 *
	 * @param event
	 *                  respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			String message = null;
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral("info.alta.ok");
			} else {
				message = UtilJSF.getLiteral("info.modificado.ok");
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
			// Refrescamos datos
			buscar();
		}

	}

	public void ayuda() {
		UtilJSF.openHelp("propiedadesConfiguracion");
	}

	/**
	 * Dbl click.
	 */
	public void rcDobleClick() {
		editar();
	}

	/**
	 * @return the filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro
	 *                   the filtro to set
	 */
	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	/**
	 * @return the listaDatos
	 */
	public List<ConfiguracionGlobal> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *                       the listaDatos to set
	 */
	public void setListaDatos(final List<ConfiguracionGlobal> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public ConfiguracionGlobal getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *                             the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final ConfiguracionGlobal datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

}
