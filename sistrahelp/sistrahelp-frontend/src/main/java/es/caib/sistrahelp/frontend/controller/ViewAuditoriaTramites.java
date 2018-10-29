package es.caib.sistrahelp.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrahelp.core.api.service.HelpDeskService;
import es.caib.sistrahelp.frontend.model.DialogResult;
import es.caib.sistrahelp.frontend.model.types.TypeModoAcceso;
import es.caib.sistrahelp.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrahelp.frontend.model.types.TypeParametroVentana;
import es.caib.sistrahelp.frontend.util.UtilJSF;
import es.caib.sistramit.rest.api.interna.REventoAuditoria;
import es.caib.sistramit.rest.api.interna.RFiltrosAuditoria;

/**
 * Mantenimiento de entidades.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewAuditoriaTramites extends ViewControllerBase {

	@Inject
	private HelpDeskService helpDeskService;

	/**
	 * Filtros (puede venir por parametro).
	 */

	private String filtroTramite;
	private String filtroVersion;
	private String filtroProcedimiento;

	/**
	 * Lista de datos.
	 */
	private List<REventoAuditoria> listaDatos;

	/**
	 * Dato seleccionado en la lista.
	 */
	private REventoAuditoria datoSeleccionado;

	private RFiltrosAuditoria filtros;

	/**
	 * Inicializacion.
	 */
	public void init() {
		// Control acceso
		// UtilJSF.verificarAccesoSuperAdministrador();
		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		filtros = new RFiltrosAuditoria();
	}

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {
		// Normaliza filtro
		// filtro = normalizarFiltro(filtro);
		// Buscar
		this.buscar();
	}

	/**
	 * Método final que se encarga de realizar la búsqueda
	 */
	private void buscar() {
		// Filtra
		// listaDatos = entidadService.listEntidad(UtilJSF.getIdioma(), filtro);
		// Quitamos seleccion de dato
		datoSeleccionado = null;

		listaDatos = helpDeskService.obtenerAuditoriaEvento(filtros);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void consultar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getId()));
		// UtilJSF.openDialog(DialogEntidad.class, TypeModoAcceso.EDICION, params, true,
		// 570, 190);
	}

	/**
	 * Dbl click.
	 */
	public void rcDobleClick() {
		consultar();
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
	 *            respuesta dialogo
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

	/**
	 * @return the listaDatos
	 */
	public List<REventoAuditoria> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<REventoAuditoria> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public REventoAuditoria getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final REventoAuditoria datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	public String getFiltroTramite() {
		return filtroTramite;
	}

	public void setFiltroTramite(final String filtroTramite) {
		this.filtroTramite = filtroTramite;
	}

	public String getFiltroProcedimiento() {
		return filtroProcedimiento;
	}

	public void setFiltroProcedimiento(final String filtroProcedimiento) {
		this.filtroProcedimiento = filtroProcedimiento;
	}

	public String getFiltroVersion() {
		return filtroVersion;
	}

	public void setFiltroVersion(final String filtroVersion) {
		this.filtroVersion = filtroVersion;
	}

	public RFiltrosAuditoria getFiltros() {
		return filtros;
	}

	public void setFiltros(final RFiltrosAuditoria filtros) {
		this.filtros = filtros;
	}

}
