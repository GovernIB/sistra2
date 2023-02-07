package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import es.caib.sistrages.core.api.model.VariableArea;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.core.api.service.VariablesAreaService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.ResultadoError;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de dominiosId (ambito global, entidad y area).
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewVariablesArea extends ViewControllerBase {

	/** Enlace servicio. */
	@Inject
	private VariablesAreaService vaService;

	/** security service. */
	@Inject
	private SecurityService securityService;

	/** tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Filtro (puede venir por parametro). */
	private String filtro;

	/** Id. **/
	private String idArea;

	/** MSG. **/
	private String msg;

	/** Accion. **/
	private String accion;

	/** Lista de datos. */
	private List<VariableArea> listaDatos;

	/** Dato seleccionado en la lista. */
	private VariableArea datoSeleccionado;

	/** Permite editar. **/
	private Boolean permiteEditar = false;

	/** Permite consultar. **/
	private Boolean permiteConsultar = false;

	/** Literal sin permisos. **/
	private static final String LITERAL_SIN_PERMISOS = "Sin permisos";

	/** Mostrar breadcrumb. **/
	private boolean mostrarBreadcrumb;

	/** miga de pan */
	private MenuModel breadCrumb;

	/** Paginacion */
	private Integer paginacion;

	private String portapapeles;

	private String errorCopiar;

	/** Inicializacion. */
	public void init() {

		setAccion("V");
		paginacion = UtilJSF.getPaginacion("viewVariablesArea");
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		checkPermisosArea();
		buscar(null);

		mostrarBreadcrumb = true;
		/* inicializa breadcrum y lo creamos */
		breadCrumb = new DefaultMenuModel();

		DefaultMenuItem item = null;

		item = new DefaultMenuItem(tramiteService.getArea(Long.parseLong(idArea)).getIdentificador());
		item.setUrl("/secure/app/viewTramites.xhtml?area=" + idArea);
		breadCrumb.addElement(item);

	}

	/**
	 *
	 * @param filtro
	 */
	public void buscar(final String filtro) {
		listaDatos = vaService.listVariableArea(Long.parseLong(idArea), filtro);
	}

	/**
	 * Recuperacion de datos.
	 */
	public void filtrar() {

		this.buscar(filtro);

		// Quitamos seleccion de dato
		datoSeleccionado = null;
	}

	/**
	 * Cambia de acción
	 */
	public void cambiarAccion() {
		switch (accion) {
		case "V":
			break;
		case "D":
			UtilJSF.redirectJsfPage("/secure/app/viewDominios.xhtml?ambito=A&id=" + idArea + "&area="
					+ tramiteService.getArea(Long.parseLong(idArea)).getIdentificador());
		case "F":
			UtilJSF.redirectJsfPage("/secure/app/viewFuentes.xhtml?ambito=A&id=" + idArea + "&area="
					+ tramiteService.getArea(Long.parseLong(idArea)).getIdentificador());
		case "S":
			UtilJSF.redirectJsfPage("/secure/app/viewFormulariosExternos.xhtml?ambito=A&id=" + idArea + "&area="
					+ tramiteService.getArea(Long.parseLong(idArea)).getIdentificador());
		case "C":
			UtilJSF.redirectJsfPage("/secure/app/viewConfiguracionAutenticacion.xhtml?ambito=A&id=" + idArea + "&area="
					+ tramiteService.getArea(Long.parseLong(idArea)).getIdentificador());
		case "E":
			UtilJSF.redirectJsfPage("/secure/app/viewEnviosRemotos.xhtml?ambito=A&id=" + idArea + "&area="
					+ tramiteService.getArea(Long.parseLong(idArea)).getIdentificador());
		default:
			break;
		}
	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {

		// Si es modo alta, verificamos por si acaso si hay permisos (no debería entrar
		// por aqui)
		if (!permiteEditar) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, LITERAL_SIN_PERMISOS);
			return;
		}

		abrirDlg(TypeModoAcceso.ALTA);
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {
		if (!permiteEditar) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, LITERAL_SIN_PERMISOS);
			return;
		}
		abrirDlg(TypeModoAcceso.EDICION);
	}

	/**
	 * Abre dialogo para consultar dato.
	 */
	public void consultar() {
		if (!permiteConsultar) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, LITERAL_SIN_PERMISOS);
			return;
		}

		abrirDlg(TypeModoAcceso.CONSULTA);
	}

	public void avisoEliminar() {
		// Verificamos por si acaso si hay permisos
		if (!permiteEditar) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, LITERAL_SIN_PERMISOS);
			return;
		}

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada()) {
			return;
		}

		final RequestContext contextReq = RequestContext.getCurrentInstance();

		this.msg = UtilJSF.getLiteral("confirm.borrado");
		contextReq.update("form:dlgConfirmar");

		contextReq.execute("PF('confirmationButton').jq.click();");
	}

	public boolean eliminarOk() {
		boolean elOk = true;

		if (!vaService.gfeByVariable(datoSeleccionado).isEmpty()
				|| !vaService.envioRemotoByVariable(datoSeleccionado).isEmpty()
				|| !vaService.dominioByVariable(datoSeleccionado).isEmpty()) {
			elOk = false;
		}

		return elOk;
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {

		if (!verificarFilaSeleccionada()) {
			return;
		}

		if (!eliminarOk()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("viewVariablesArea.error.elementosAsociados"));
			return;
		}

		if (vaService.removeVariableArea(this.datoSeleccionado.getCodigo())) {
			// Refrescamos datos
			filtrar();
			ResultadoError re = this.refrescar();
			String message = "";
			// Mostramos mensaje
			if (re.getCodigo() != 1) {
				message = UtilJSF.getLiteral("info.borrado.ok") + ". " + UtilJSF.getLiteral("error.refrescarCache")
						+ ": " + re.getMensaje();
			} else {
				message = UtilJSF.getLiteral("info.borrado.ok") + ". " + UtilJSF.getLiteral("info.cache.ok");
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.borrar.dependencias"));
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
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				/*
				 * ResultadoError re =
				 * this.refrescar(this.datoSeleccionado.getIdentificadorCompuesto()); if
				 * (re.getCodigo() != 1) { message = UtilJSF.getLiteral("info.alta.ok") + ". " +
				 * UtilJSF.getLiteral("error.refrescarCache") + ": " + re.getMensaje(); } else {
				 * message = UtilJSF.getLiteral("info.alta.ok") + ". " +
				 * UtilJSF.getLiteral("info.cache.ok"); }
				 */
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.alta.ok"));
			} else {
				String message = null;
				ResultadoError re = this.refrescar();
				if (respuesta.getMensaje() == null) {
					if (re.getCodigo() != 1) {
						message = UtilJSF.getLiteral("info.modificado.ok") + ". "
								+ UtilJSF.getLiteral("error.refrescarCache") + ": " + re.getMensaje();
					} else {
						message = UtilJSF.getLiteral("info.modificado.ok") + ". " + UtilJSF.getLiteral("info.cache.ok");
					}
					UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
				} else {
					if (re.getCodigo() != 1) {
						message = UtilJSF.getLiteral("info.modificado.ok") + ". " + respuesta.getMensaje().getMensaje()
								+ ". " + UtilJSF.getLiteral("error.refrescarCache") + ": " + re.getMensaje();
					} else {
						message = UtilJSF.getLiteral("info.modificado.ok") + ". " + respuesta.getMensaje().getMensaje()
								+ ". " + UtilJSF.getLiteral("info.cache.ok");
					}
					UtilJSF.addMessageContext(respuesta.getMensaje().getNivel(), message);
				}

			}

			// Refrescamos datos
			buscar(filtro);
		}

	}

	public void ayuda() {
		UtilJSF.openHelp("variablesArea");
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoImportar(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && respuesta.getModoAcceso() != null
				&& !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			ResultadoError re = this.refrescar();
			if (re.getCodigo() != 1) {
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.importar.ok") + ". "
						+ UtilJSF.getLiteral("error.refrescarCache") + ": " + re.getMensaje());
			} else {
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
						UtilJSF.getLiteral("info.importar.ok") + ". " + UtilJSF.getLiteral("info.cache.ok"));
			}
			// Refrescamos datos
			buscar(filtro);

		}

	}

	/**
	 * Obtiene el valor de filaSeleccionada.
	 *
	 * @return el valor de filaSeleccionada
	 */
	public boolean getFilaSeleccionada() {
		return verificarFilaSeleccionada();
	}

	// ------- FUNCIONES PRIVADAS ------------------------------
	/**
	 * Checkea permisos area. Las reglas son las siguientes: <br />
	 * - Si eres administrador de entidad, puedes editar en cualquier entorno.
	 * <br />
	 * - Si eres desarrollador de entidad, depende del entorno y tus permisos:
	 * <br />
	 * ---> Permiso admin. area en des/pre/pro puedes tot <br />
	 * ---> Permiso desa. area en desarrollo puedes tot mientras que en pre/pro
	 * puede consultar <br />
	 * ---> Permiso consulta en des/pre/pro solo consulta.
	 *
	 */
	private void checkPermisosArea() {
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			permiteEditar = true;
		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {

			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(Long.valueOf(idArea));

			if (permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)) {
				permiteEditar = true;
			} else if (permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)) {
				if (UtilJSF.getEntorno().equals(TypeEntorno.DESARROLLO.toString())) {
					permiteEditar = true;
				} else {
					permiteConsultar = true;
				}
			} else {
				permiteConsultar = true;
			}
		}
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
			params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));
		}

		params.put(TypeParametroVentana.AREA.toString(), this.idArea);
		UtilJSF.openDialog(DialogVariablesArea.class, modoAccesoDlg, params, true, 730, 250);
	}

	/**
	 * Dbl click.
	 */
	public void rcDobleClick() {
		if (permiteEditar) {
			editar();
		} else if (permiteConsultar) {
			consultar();
		}
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
		UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("viewTramites.copiar"));
	}

	// ------- GETTERS / SETTERS --------------------------------
	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
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
	public List<VariableArea> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos the listaDatos to set
	 */
	public void setListaDatos(final List<VariableArea> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public VariableArea getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final VariableArea datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * @return the id
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * @param id the id to set
	 */
	public void setIdArea(final String idArea) {
		this.idArea = idArea;
	}

	/**
	 * @return the permiteEditar
	 */
	public Boolean getPermiteEditar() {
		return permiteEditar;
	}

	/**
	 * @param permiteEditar the permiteEditar to set
	 */
	public void setPermiteEditar(final Boolean permiteEditar) {
		this.permiteEditar = permiteEditar;
	}

	/**
	 * @return the permiteConsultar
	 */
	public Boolean getPermiteConsultar() {
		return permiteConsultar;
	}

	/**
	 * @param permiteConsultar the permiteConsultar to set
	 */
	public void setPermiteConsultar(final Boolean permiteConsultar) {
		this.permiteConsultar = permiteConsultar;
	}

	/**
	 * @return the mostrarBreadcrumb
	 */
	public boolean isMostrarBreadcrumb() {
		return mostrarBreadcrumb;
	}

	/**
	 * @param mostrarBreadcrumb the mostrarBreadcrumb to set
	 */
	public void setMostrarBreadcrumb(final boolean mostrarBreadcrumb) {
		this.mostrarBreadcrumb = mostrarBreadcrumb;
	}

	/**
	 * @return the breadCrumb
	 */
	public MenuModel getBreadCrumb() {
		return breadCrumb;
	}

	/**
	 * @param breadCrumb the breadCrumb to set
	 */
	public void setBreadCrumb(final MenuModel breadCrumb) {
		this.breadCrumb = breadCrumb;
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
		UtilJSF.setPaginacion(paginacion, "viewDominios");
	}

	/**
	 * @return the accion
	 */
	public final String getAccion() {
		return accion;
	}

	/**
	 * @param accion the accion to set
	 */
	public final void setAccion(String accion) {
		this.accion = accion;
	}

	/**
	 * @return the msg
	 */
	public final String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public final void setMsg(String msg) {
		this.msg = msg;
	}

}
