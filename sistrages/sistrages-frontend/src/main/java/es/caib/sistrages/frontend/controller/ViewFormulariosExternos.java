package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.GestorExternoFormularios;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.FormularioExternoService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de mensajes de aviso entidad.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewFormulariosExternos extends ViewControllerBase {

	@Inject
	private FormularioExternoService formularioExternoService;

	@Inject
	private EntidadService entidadService;

	@Inject
	private SystemService systemService;

	/** Id entidad. */
	private Long idEntidad;

	/** Filtro (puede venir por parametro). */
	private String filtro;

	/** Lista de datos. */
	private List<GestorExternoFormularios> listaDatos;

	/** Dato seleccionado en la lista. */
	private GestorExternoFormularios datoSeleccionado;

	/**
	 * Inicializacion.
	 */
	public void init() {
		// Id entidad
		idEntidad = UtilJSF.getIdEntidad();
		// Control acceso
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidadByEntidad(idEntidad);
		// Titulo
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		// Recupera datos
		buscar();
	}

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {
		// Normaliza filtro
		filtro = normalizarFiltro(filtro);
		// Busca
		buscar();
	}

	/**
	 * Buscar datos.
	 */
	private void buscar() {
		// Filtra
		listaDatos = formularioExternoService.listFormularioExterno(idEntidad, UtilJSF.getIdioma(), filtro);
		// Quitamos seleccion de dato
		datoSeleccionado = null;
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {
		abrirDlg(TypeModoAcceso.ALTA);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {
		abrirDlg(TypeModoAcceso.EDICION);
	}

	/**
	 * Abre dialogo para consultar dato.
	 */
	public void consultar() {
		abrirDlg(TypeModoAcceso.CONSULTA);
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Eliminamos
		if (formularioExternoService.removeFormularioExterno(datoSeleccionado.getCodigo())) {
			// Refrescamos datos
			buscar();
			// Mostramos mensaje
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		}
	}

	/**
	 * Refrescar cache.
	 */
	public void refrescarCache() {
		final String urlBase = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_URL);
		final String usuario = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_USER);
		final String pwd = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_PWD);
		final Entidad entidad = entidadService.loadEntidad(idEntidad);
		this.refrescarCache(urlBase, usuario, pwd, Constantes.CACHE_ENTIDAD, entidad.getCodigoDIR3());

	}

	/**
	 * Abrir ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("formulariosExternos");
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
	 * Obtiene el valor de permiteAlta.
	 *
	 * @return el valor de permiteAlta
	 */
	public boolean getPermiteAlta() {
		return (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT);
	}

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getPermiteEditar() {
		return (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT);
	}

	/**
	 * Dbl click.
	 */
	public void rcDobleClick() {
		if (getPermiteEditar()) {
			editar();
		} else {
			consultar();
		}
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
	 * @return the filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro the filtro to set
	 */
	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	/**
	 * @return the listaDatos
	 */
	public List<GestorExternoFormularios> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos the listaDatos to set
	 */
	public void setListaDatos(final List<GestorExternoFormularios> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public GestorExternoFormularios getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final GestorExternoFormularios datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * Abrir dialogo.
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
			params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));
		}

		UtilJSF.openDialog(DialogFormularioExterno.class, modoAccesoDlg, params, true, 470, 165);
	}

}
