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

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Sesion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeDominio;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.core.api.service.TramiteService;
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
public class ViewDominios extends ViewControllerBase {

	/** Enlace servicio. */
	@Inject
	private DominioService dominioService;

	/** security service. */
	@Inject
	private SecurityService securityService;

	/** tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** sistem service. */
	@Inject
	private SystemService systemService;

	/** Filtro (puede venir por parametro). */
	private String filtro;

	/** Id. **/
	private String id;

	/** Ambito. **/
	private String ambito;

	/** Area. **/
	private String area;

	/** MSG. **/
	private String msg;

	/** Accion. **/
	private String accion;

	/** Lista de areas. */
	private List<Area> listaAreas;

	/** Lista de datos. */
	private List<Dominio> listaDatos;

	/** Dato seleccionado en la lista. */
	private Dominio datoSeleccionado;

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

	private String portapapeles;

	private String errorCopiar;

	/** Inicializacion. */
	public void init() {

		if (ambito == null) {
			return;
		}
		setAccion("D");
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()) + "." + ambito);
		checkPermisos();
		buscar(null);

		if (ambito.equals(TypeAmbito.AREA.toString())) {
			Sesion sesion = null;

			// Recupera info usuario
			String userName = securityService.getUsername();

			// recuperamos datos por defecto del usuario
			if (!userName.isEmpty()) {
				sesion = systemService.getSesion(userName);
				listaAreas = tramiteService.listArea(sesion.getEntidad(), null);
				if (!tramiteService.getArea(Long.parseLong(id)).getIdentificador().equals(area)) {
					area = tramiteService.getArea(Long.parseLong(id)).getIdentificador();
				}
			}

			mostrarBreadcrumb = true;
			/* inicializa breadcrum y lo creamos */
			breadCrumb = new DefaultMenuModel();

			DefaultMenuItem item = null;

			item = new DefaultMenuItem(area);
			item.setUrl("/secure/app/viewTramites.xhtml?area=" + id);
			breadCrumb.addElement(item);

		} else {
			mostrarBreadcrumb = false;
		}
	}

	/**
	 * Permite importar.
	 *
	 * @return
	 */
	public boolean getPermiteImportar() {
		boolean permite = false;
		if (ambito.equals(TypeAmbito.GLOBAL.toString())) {
			permite = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.SUPER_ADMIN);
		} else if (ambito.equals(TypeAmbito.ENTIDAD.toString())) {
			permite = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT);
		} else if (ambito.equals(TypeAmbito.AREA.toString())) {
			permite = permiteEditar;
		}
		return permite;
	}

	/**
	 *
	 * @param filtro
	 */
	public void buscar(final String filtro) {
		final TypeAmbito typeAmbito = TypeAmbito.fromString(ambito);
		Long lId = null;
		if (id != null) {
			lId = Long.valueOf(id);
		}
		listaDatos = dominioService.listDominio(typeAmbito, lId, filtro);
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
			UtilJSF.redirectJsfPage("/secure/app/viewVariablesArea.xhtml?id=" + id);
		case "D":
			break;
		case "F":
			UtilJSF.redirectJsfPage("/secure/app/viewFuentes.xhtml?ambito=A&id=" + id + "&area=" + area);
		case "S":
			UtilJSF.redirectJsfPage("/secure/app/viewFormulariosExternos.xhtml?ambito=A&id=" + id + "&area=" + area);
		case "C":
			UtilJSF.redirectJsfPage(
					"/secure/app/viewConfiguracionAutenticacion.xhtml?ambito=A&id=" + id + "&area=" + area);
		case "E":
			UtilJSF.redirectJsfPage("/secure/app/viewEnviosRemotos.xhtml?ambito=A&id=" + id + "&area=" + area);
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

		if (this.datoSeleccionado.getTipo().equals(TypeDominio.FUENTE_DATOS)) {
			String[] param = new String[1];
			param[0] = this.datoSeleccionado.getIdentificadorFD();
			this.msg = UtilJSF.getLiteral("confirm.componente.eliminarFD", param);
			contextReq.update("form:dlgConfirmar");
		} else {
			this.msg = UtilJSF.getLiteral("confirm.borrado");
			contextReq.update("form:dlgConfirmar");
		}
		contextReq.execute("PF('confirmationButton').jq.click();");
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {

		// Eliminamos
		String eliminado = this.datoSeleccionado.getIdentificadorCompuesto();
		Long idFd = null;
		if (this.datoSeleccionado.getTipo().equals(TypeDominio.FUENTE_DATOS)) {
			idFd = this.datoSeleccionado.getIdFuenteDatos();
		}
		if (this.dominioService.removeDominio(this.datoSeleccionado.getCodigo())) {
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
			if (idFd != null) {
				if (this.dominioService.removeFuenteDato(idFd)) {
					UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
				} else {
					UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
							UtilJSF.getLiteral("error.borrar.dependencias.fd"));
				}
			} else {
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
			}
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR, UtilJSF.getLiteral("error.borrar.dependencias"));
		}
	}

	/**
	 * Ping.
	 */
	public void ping() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));

		params.put(TypeParametroVentana.AMBITO.toString(), ambito);

		UtilJSF.openDialog(DialogDominioPing.class, TypeModoAcceso.CONSULTA, params, true, 940, 700);
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
				if (respuesta.getMensaje() == null) {
					ResultadoError re = this.refrescar();
					if (re.getCodigo() != 1) {
						message = UtilJSF.getLiteral("info.modificado.ok") + ". "
								+ UtilJSF.getLiteral("error.refrescarCache") + ": " + re.getMensaje();
					} else {
						message = UtilJSF.getLiteral("info.modificado.ok") + ". " + UtilJSF.getLiteral("info.cache.ok");
					}
					UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
				} else {
					ResultadoError re = this.refrescar();
					if (re.getCodigo() != 1) {
						message = UtilJSF.getLiteral("info.modificado.ok") + ". " + respuesta.getMensaje().getMensaje()
								+ UtilJSF.getLiteral("error.refrescarCache") + ": " + re.getMensaje();
					} else {
						message = UtilJSF.getLiteral("info.modificado.ok") + ". " + respuesta.getMensaje().getMensaje()
								+ UtilJSF.getLiteral("info.cache.ok");
					}
					UtilJSF.addMessageContext(respuesta.getMensaje().getNivel(), message);
				}
			}
			// Refrescamos datos
			buscar(filtro);
		}

	}

	public void ayuda() {
		UtilJSF.openHelp("dominios");
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
	 * Retorno dialogo clonar.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoClonar(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			final String message = UtilJSF.getLiteral("info.clonado.ok");
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Mensaje dialogo
			if (respuesta.getMensaje() != null) {
				UtilJSF.addMessageContext(respuesta.getMensaje().getNivel(), respuesta.getMensaje().getMensaje());
			}

			// Refrescamos datos
			buscar(filtro);
		}

	}

	/**
	 * Importar un dominio.
	 */
	public void importar() {

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.AMBITO.toString(), this.ambito);
		if (id != null) {
			params.put(TypeParametroVentana.ID.toString(), this.id);
		}
		UtilJSF.openDialog(DialogDominioImportar.class, TypeModoAcceso.ALTA, params, true, 770, 280);
	}

	/**
	 * Exportar un dominio.
	 */
	public void exportar() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));
		UtilJSF.openDialog(DialogDominioExportar.class, TypeModoAcceso.ALTA, params, true, 500, 200);
	}

	/**
	 * Clona un dominio.
	 */
	public void clonar() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));

		if (this.datoSeleccionado.getAmbito() == TypeAmbito.AREA) {
			final Long idArea = datoSeleccionado.getArea().getCodigo();
			params.put(TypeParametroVentana.AREA.toString(), String.valueOf(idArea));
		}
		UtilJSF.openDialog(DialogDominioClonar.class, TypeModoAcceso.ALTA, params, true, 500, 200);
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
	 * Método global que prepara las variables permiteEditar o permiteConsultar.
	 * <br />
	 * - El ambito es global, sólo si tienes permiso superadmin.<br />
	 * - El ambito es entidad, revisar el metodo para ver como se controla los
	 * permisos. - El ambito es area, revisar el metodo para ver como se controla
	 * los permisos.
	 */
	public void checkPermisos() {

		permiteEditar = false;
		permiteConsultar = false;
		final TypeAmbito ambitoType = TypeAmbito.fromString(ambito);
		switch (ambitoType) {
		case GLOBAL:
			permiteEditar = (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.SUPER_ADMIN);
			break;
		case ENTIDAD:
			if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
				permiteEditar = true;
			} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
				// if (UtilJSF.getEntorno().equals(TypeEntorno.DESARROLLO.toString())) {
				// permiteEditar = true;
				// } else {
				permiteConsultar = true;
				// }
			}
			break;
		case AREA:
			checkPermisosArea();
			break;
		}

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
					.getPermisosDesarrolladorEntidadByArea(Long.valueOf(id));

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
	 * Abre dialogo de tramites.
	 *
	 * @param modoAccesoDlg Modo acceso
	 */
	public void tramites() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.datoSeleccionado.getCodigo()));
		params.put(TypeParametroVentana.AMBITO.toString(), ambito);
		final TypeAmbito typeAmbito = TypeAmbito.fromString(ambito);
		if (typeAmbito == TypeAmbito.AREA) {
			params.put("AREA", id);
		}
		if (typeAmbito == TypeAmbito.ENTIDAD) {
			params.put("ENTIDAD", id);
		}
		UtilJSF.openDialog(DialogDominioTramites.class, TypeModoAcceso.CONSULTA, params, true, 770, 400);
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
		params.put(TypeParametroVentana.AMBITO.toString(), ambito);
		final TypeAmbito typeAmbito = TypeAmbito.fromString(ambito);

		if (typeAmbito == TypeAmbito.AREA) {
			params.put("AREA", id);
		}
		if (typeAmbito == TypeAmbito.ENTIDAD) {
			params.put("ENTIDAD", id);
		}
		UtilJSF.openDialog(DialogDominio.class, modoAccesoDlg, params, true, 770, 670);
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
	public List<Dominio> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos the listaDatos to set
	 */
	public void setListaDatos(final List<Dominio> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public Dominio getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final Dominio datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * @return the ambito
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito the ambito to set
	 */
	public void setAmbito(final String ambito) {
		this.ambito = ambito;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
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
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(final String area) {
		this.area = area;
	}

	/**
	 * @return the listaAreas
	 */
	public final List<Area> getListaAreas() {
		return listaAreas;
	}

	/**
	 * @param listaAreas the listaAreas to set
	 */
	public final void setListaAreas(List<Area> listaAreas) {
		this.listaAreas = listaAreas;
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
