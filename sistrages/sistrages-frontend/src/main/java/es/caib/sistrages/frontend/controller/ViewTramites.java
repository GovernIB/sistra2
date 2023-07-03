package es.caib.sistrages.frontend.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Rol;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.ErrorValidacion;
import es.caib.sistrages.core.api.model.comun.FilaImportarResultado;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.service.RolService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.ResultadoError;
import es.caib.sistrages.frontend.model.TramiteVersiones;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilRest;

/**
 * Mantenimiento de tramites.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewTramites extends ViewControllerBase {

	@Inject
	FormularioInternoService formIntService;
	/** Service. */
	@Inject
	private TramiteService tramiteService;

	/** security service. */
	@Inject
	private SecurityService securityService;

	/** rol service. */
	@Inject
	private RolService rolService;
	/** System service. **/
	@Inject
	private SystemService systemService;

	/** Filtro (puede venir por parametro). */
	private String filtro;

	/** Id Area. */
	private String idArea;

	/** Id Area. */
	private String idTramite;

	/** Id Area. */
	private String idTramiteVersion;

	/** Dato seleccionado en la lista. */
	private TramiteVersiones tramiteSeleccionada;

	/** Dato seleccionado en la lista. */
	private TramiteVersiones tramiteSeleccionadaBreadcrumb;

	/** Lista de datos. */
	private List<TramiteVersiones> listaTramiteVersiones;

	/** Dato seleccionado en la lista. */
	private TramiteVersion versionSeleccionada;

	/** Lista de areas. **/
	private List<Area> listaAreas = new ArrayList<>();

	/** Lista de areas seleccionadas. **/
	private List<Area> listaAreasSeleccionadas = new ArrayList<>();

	/** Para evitar el retrase en las comprobaciones en perfil desarrollador. **/
	private List<TypeRolePermisos> permisosCacheados = new ArrayList<>();
	private Long idAreaCacheado = null;
	private Long idTramiteCacheado = null;

	/** Indica si se permite refrescar. **/
	private boolean permiteRefrescar;
	private boolean mostrarTodasAreas;

	/** Indica si es administrador entidad. **/
	private boolean admEntidad;

	/** Literal. **/
	private static final String LITERAL_INFO_ALTA_OK = "info.alta.ok";
	private static final String LITERAL_INFO_BORRADO_OK = "info.borrado.ok";
	private static final String LITERAL_INFO_MODIFICADO_OK = "info.modificado.ok";

	private LazyDataModel<TramiteVersiones> dataModel;
	/** Datos que obtienen el ancho y alto de la pantalla. **/
	private String height;
	private String width;
	private String pag;
	private List<Dominio> dominiosNoUtilizados;
	private List<String> scriptsProblematicos;

	private String idAreaAux;
	private String idTramiteAux;

	private Integer paginacion;

	private String portapapeles;

	private Boolean renderCmenu = true;

	private String errorCopiar;

	/**
	 * Inicializacion.
	 */
	public void init() {

		idAreaAux = null;
		idTramiteAux = null;
		dominiosNoUtilizados = null;
		scriptsProblematicos = new ArrayList<>();
		height = "100%";
		width = "100%";
		paginacion = UtilJSF.getPaginacion("viewTramites");
		// Id entidad
		final Long idEntidad = UtilJSF.getIdEntidad();
		// Control acceso
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidadByEntidad(idEntidad);
		// comprueba si es admin entidad
		admEntidad = UtilJSF.isAdministradorDesarrolladorEntidad(idEntidad);

		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		// Recupera areas

		// Para obtener el idArea, idTramite e idTramiteVersion
		idArea = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getParameter("area");
		idTramite = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getParameter("tramite");

		idTramiteVersion = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getParameter("tramite_version");

		pag = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getParameter("pag");

		if (pag == null) {
			pag = "0";
		}

		mostrarTodasAreas = false;
		buscarAreas();

		if (idTramite != null) {

			final Tramite tramCrumb = tramiteService.getTramite(Long.valueOf(idTramite));

			tramiteSeleccionadaBreadcrumb = new TramiteVersiones(tramCrumb, null);
			tramiteSeleccionada = tramiteSeleccionadaBreadcrumb;
		}

		if (idTramiteVersion != null) {
			tramiteSeleccionada = null;
			versionSeleccionada = tramiteService.getTramiteVersion(Long.valueOf(idTramiteVersion));
		}
	}

	public void asignarSeleccionado() {
		unSelectVersionTramite();
		String codigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param");
		String x = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("x");
		String y = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("y");
		// TramiteVersiones tv = new
		// TramiteVersiones(tramiteService.getTramite(Long.parseLong(codigo)),
		// tramiteService.listTramiteVersion(Long.parseLong(codigo), null));
		Tramite tr = new Tramite();
		try {
			tr.setCodigo(Long.parseLong(codigo));
			List<TramiteVersion> ltv = new ArrayList<TramiteVersion>();
			TramiteVersiones tv = new TramiteVersiones(tr, ltv);
			this.setTramiteSeleccionada(tv);
		} catch (NumberFormatException e) {
			this.setTramiteSeleccionada(null);
		}
		PrimeFaces.current().ajax().update("form:toolbarTramites");
		PrimeFaces.current()
				.executeScript("document.elementFromPoint(" + x + ", " + y + ").classList.add('resaltar');");
	}

	/**
	 * Abre dialogo para consultar datos de Area.
	 */
	public void consultarArea() {
		abrirArea(TypeModoAcceso.CONSULTA);
	}

	/**
	 * Abre dialogo para editar dato de Area.
	 */
	public void editarArea() {
		abrirArea(TypeModoAcceso.EDICION);
	}

	/**
	 * Abre dialogo de nueva Area.
	 */
	public void nuevaArea() {
		UtilJSF.openDialog(DialogArea.class, TypeModoAcceso.ALTA, null, true, 520, 180);
	}

	/**
	 * Abre dialogo para eliminar area.
	 */
	public void eliminarArea() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea()) {
			return;
		}

		final Area area = listaAreasSeleccionadas.get(0);

		if (borrarRolesAsociados(area.getCodigo()) && tramiteService.removeArea(area.getCodigo())) {
			listaAreasSeleccionadas.clear();
			this.buscarAreas();
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral(LITERAL_INFO_BORRADO_OK));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		}

	}

	private boolean borrarRolesAsociados(Long codArea) {
		boolean noFallo = true;
		List<Rol> roles = rolService.getRolesByArea(codArea);
		for (Rol rol : roles) {
			if (!rolService.removeRol(rol.getCodigo())) {
				noFallo = false;
			}
		}
		return noFallo;
	}

	/**
	 * Dominios de area.
	 */
	public void dominioArea() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea()) {
			return;
		}

		final Area area = listaAreasSeleccionadas.get(0);
		UtilJSF.redirectJsfPage(
				"/secure/app/viewDominios.xhtml?ambito=A&id=" + area.getCodigo() + "&area=" + area.getIdentificador());
	}

	/**
	 * Variables area
	 */
	public void varArea() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea()) {
			return;
		}

		final Area area = listaAreasSeleccionadas.get(0);
		UtilJSF.redirectJsfPage("/secure/app/viewVariablesArea.xhtml?id=" + area.getCodigo());

	}

	/**
	 * Fuente datos area.
	 */
	public void sgeArea() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea()) {
			return;
		}

		final Area area = listaAreasSeleccionadas.get(0);
		UtilJSF.redirectJsfPage("/secure/app/viewFormulariosExternos.xhtml?ambito=A&id=" + area.getCodigo() + "&area="
				+ area.getIdentificador());
	}

	/**
	 * Fuente datos area.
	 */
	public void confAutArea() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea()) {
			return;
		}

		final Area area = listaAreasSeleccionadas.get(0);
		UtilJSF.redirectJsfPage("/secure/app/viewConfiguracionAutenticacion.xhtml?ambito=A&id=" + area.getCodigo()
				+ "&area=" + area.getIdentificador());
	}

	/**
	 * Envio remoto
	 */
	public void envioRemoto() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea()) {
			return;
		}

		final Area area = listaAreasSeleccionadas.get(0);
		UtilJSF.redirectJsfPage("/secure/app/viewEnviosRemotos.xhtml?ambito=A&id=" + area.getCodigo() + "&area="
				+ area.getIdentificador());
	}

	/**
	 * Fuente datos area.
	 */
	public void datosArea() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea()) {
			return;
		}

		final Area area = listaAreasSeleccionadas.get(0);
		UtilJSF.redirectJsfPage(
				"/secure/app/viewFuentes.xhtml?ambito=A&id=" + area.getCodigo() + "&area=" + area.getIdentificador());
	}

	/**
	 * Abre dialogo de nuevo tramite.
	 */
	public void nuevoTramite() {
		abrirTramite(TypeModoAcceso.ALTA);
	}

	/**
	 * Abre dialogo para consultar datos de tramite.
	 */
	public void consultarTramite() {
		abrirTramite(TypeModoAcceso.CONSULTA);
	}

	/**
	 * Dble click. Si tiene permiso, abre dialogo para editar tramite.
	 */
	public void dblEditarDblTramite() {
		if (this.getTienePermisosArea()) {
			editarTramite();
		} else {
			consultarTramite();
		}
	}

	/**
	 * Abre dialogo para editar tramite
	 */
	public void editarTramite() {
		abrirTramite(TypeModoAcceso.EDICION);
	}

	/**
	 * Importar versión.
	 */
	public void importar() {
		final Map<String, String> params = new HashMap<>();
		UtilJSF.openDialog(DialogTramiteImportar.class, TypeModoAcceso.EDICION, params, true, 640);
	}

	/**
	 * Procesar Cuaderno Carga.
	 */
	public void procesarCC() {
		final Map<String, String> params = new HashMap<>();
		UtilJSF.openDialog(DialogCuadernoCarga.class, TypeModoAcceso.EDICION, params, true, 800);
	}

	/**
	 * Return dialogo de importar.
	 *
	 * @param event
	 */
	public final void returnDialogoImportar(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {

			final FilaImportarResultado resultado = (FilaImportarResultado) respuesta.getResult();
			idArea = resultado.getIdArea().toString();
			idTramite = resultado.getIdTramite().toString();
			idTramiteVersion = resultado.getIdTramiteVersion().toString();
			// Refrescamos datos
			buscarAreas();
			versionSeleccionada = tramiteService.getTramiteVersion(resultado.getIdTramiteVersion());

		}
	}

	/**
	 * Abre dialogo para eliminar tramite.
	 */
	public void eliminarTramite() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaTramite())
			return;

		if (tramiteService.tieneTramiteVersion(tramiteSeleccionada.getTramite().getCodigo())) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.errorTramiteVersion"));
			return;
		}

		if (tramiteService.removeTramite(tramiteSeleccionada.getTramite().getCodigo())) {
			buscarTramites();
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral(LITERAL_INFO_BORRADO_OK));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		}

	}

	public boolean isAdminArea() {
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR
				&& permisosCacheados.contains(TypeRolePermisos.ADMINISTRADOR_AREA)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Abre dialogo para editar el control de acceso de un trámite.
	 */
	public void controlAcceso() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaVersion())
			return;

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.versionSeleccionada.getCodigo().toString());
		UtilJSF.openDialog(DialogTramiteControlAcceso.class, TypeModoAcceso.EDICION, params, true, 1000, 500);

	}

	public boolean isDesarrollo() {
		if (UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isPreproduccion() {
		if (UtilJSF.checkEntorno(TypeEntorno.PREPRODUCCION)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retorno dialogo control acceso.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoControlAcceso(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			String message = "";

			ResultadoError re = null;
			re = this.refrescar();
			if (re != null && re.getCodigo() == 1) {
				message = UtilJSF.getLiteral(LITERAL_INFO_MODIFICADO_OK) + ". " + UtilJSF.getLiteral("info.cache.ok");
			} else {
				message = UtilJSF.getLiteral(LITERAL_INFO_MODIFICADO_OK) + ". "
						+ UtilJSF.getLiteral("error.refrescarCache") + ": " + re.getMensaje();
			}

			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Refrescamos datos
			buscarTramites();
		}
	}

	/**
	 * Abre un dialogo para previsualizar tramite.
	 */
	public void previsualizar() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.versionSeleccionada.getCodigo()));

		if (this.isPermiteDesbloquear() && this.getTienePermisosVersion()) {
			UtilJSF.openDialog(DialogTramiteVersionPrevisualizar.class, TypeModoAcceso.EDICION, params, true, 830, 430);
		} else {
			UtilJSF.openDialog(DialogTramiteVersionPrevisualizar.class, TypeModoAcceso.CONSULTA, params, true, 830,
					430);
		}
	}

	/**
	 * Abre un dialogo para previsualizar tramite.
	 */
	public void previsualizarEdicion() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.versionSeleccionada.getCodigo()));
		UtilJSF.openDialog(DialogTramiteVersionPrevisualizar.class, TypeModoAcceso.EDICION, params, true, 950, 430);
	}

	/**
	 * Retorno dialogo area.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoArea(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			String message = null;
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral(LITERAL_INFO_ALTA_OK);
			} else {
				message = UtilJSF.getLiteral(LITERAL_INFO_MODIFICADO_OK);
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Refrescamos datos
			buscarAreas();

			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				Area areaRespuesta = (Area) respuesta.getResult();
				Area aCreada = null;
				for (Area area : listaAreasSeleccionadas) {
					if (area.getIdentificador().equals(areaRespuesta.getIdentificador())) {
						aCreada = area;
					}
				}
				listaAreasSeleccionadas.clear();
				listaAreasSeleccionadas.add(aCreada);
			} else {
				listaAreasSeleccionadas.clear();
				listaAreasSeleccionadas.add((Area) respuesta.getResult());
			}

		}
	}

	/**
	 * Obtiene el valor de permiteEditarArea.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getTienePermisosAreas() {
		boolean retorno;
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			retorno = true;
		} else {
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Obtiene el valor de permiteEditarArea.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getTienePermisosArea() {
		boolean retorno;
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			retorno = true;
		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR
				&& verificarFilaSeleccionadaArea()) {

			actualizarPermisosCacheados(listaAreasSeleccionadas.get(0).getCodigo());

			retorno = permisosCacheados.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
					|| permisosCacheados.contains(TypeRolePermisos.DESARROLLADOR_AREA);

		} else {
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Obtiene el valor de permiteEditarArea.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getTienePermisosAreaConsulta() {
		boolean retorno;
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			retorno = true;
		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR
				&& verificarFilaSeleccionadaArea()) {

			actualizarPermisosCacheados(listaAreasSeleccionadas.get(0).getCodigo());

			retorno = permisosCacheados.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
					|| permisosCacheados.contains(TypeRolePermisos.DESARROLLADOR_AREA)
					|| permisosCacheados.contains(TypeRolePermisos.CONSULTA);

		} else {
			retorno = false;
		}
		return retorno;
	}

	/**
	 * Actualiza los permisos si no está cacheado los permisos del area.
	 *
	 * @param idArea
	 */
	private void actualizarPermisosCacheados(final Long idArea) {
		if (this.idAreaCacheado == null || idArea.compareTo(idAreaCacheado) != 0) {
			permisosCacheados = securityService
					.getPermisosDesarrolladorEntidadByArea(listaAreasSeleccionadas.get(0).getCodigo());
			idAreaCacheado = idArea;
		}
	}

	/** Comprueba que el entorno no es desarrollo. **/
	@Override
	public boolean isNotDesarrollo() {
		return TypeEntorno.fromString(UtilJSF.getEntorno()) != TypeEntorno.DESARROLLO;
	}

	/**
	 * Obtiene el valor de tienePermisosTramite, se tiene que cumplir que:
	 * <ul>
	 * <li>Tiene que haber una fila o versión seleccionada.</li>
	 * <li>Si eres adm. entidad ya puedes tocarlo.</li>
	 * <li>Sino tienes que ser adminisitrador o desarrollador de área.</li>
	 * </ul>
	 *
	 * @return el valor de tienePermisosTramite
	 */
	public boolean getTienePermisosTramite() {
		boolean res;

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaTramite() && !verificarFilaSeleccionadaVersion()) {
			res = false;
		}
		// Admin entidad
		else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			res = true;
		}
		// Admin area o Desarrollador (solo funciona en entorno de desarrollo)
		else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR
				&& TypeEntorno.fromString(UtilJSF.getEntorno()) == TypeEntorno.DESARROLLO) {

			if (tramiteSeleccionada != null) {
				actualizarPermisosCacheados(tramiteSeleccionada.getTramite().getIdArea());
			} else {
				actualizarPermisosCacheados(this.versionSeleccionada.getIdArea());
			}

			if (permisosCacheados != null) {
				res = permisosCacheados.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
						|| permisosCacheados.contains(TypeRolePermisos.DESARROLLADOR_AREA);

			} else {
				res = false;
			}
		} else {
			res = false;
		}
		PrimeFaces.current().executeScript("opcTramites();");
		return res;

	}

	/**
	 * Propiedad que muestra el botón previsualizar en pre a desarrollador
	 */
	public boolean getPrevis() {
		if ((UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR
				&& TypeEntorno.fromString(UtilJSF.getEntorno()) == TypeEntorno.PREPRODUCCION)
				|| (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR
						&& TypeEntorno.fromString(UtilJSF.getEntorno()) == TypeEntorno.DESARROLLO)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Obtiene el valor de tienePermisosVersion, se tiene que cumplir que:
	 * <ul>
	 * <li>Tiene que haber una versión seleccionada.</li>
	 * <li>Eres el que la ha bloqueado.</li>
	 * </ul>
	 *
	 * @return el valor de tienePermisosVersion
	 */
	public boolean getTienePermisosVersion() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaVersion()) {
			return false;
		}

		if (!this.versionSeleccionada.getBloqueada()) {
			return false;
		}

		return this.versionSeleccionada.getDatosUsuarioBloqueo().equals(UtilJSF.getSessionBean().getUserName());

	}

	/**
	 * Método publico de filtrar
	 */
	public void filtrar() {

		filtro = filtro != null ? escaparFiltro(filtro) : null;
		final DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("form:dataTableTramites");
		dataTable.setFirst(0);
		idTramiteVersion = null;
		idTramite = null;

		this.buscarTramites(filtro, true);
		filtro = filtro != null ? quitarEscapeFiltro(filtro) : null;
		PrimeFaces.current().executeScript("setHeight();");

	}

	/**
	 * Método que escapa los carácteres especiales del filtro
	 */
	public String escaparFiltro(String filtro) {
		filtro = filtro.replace("_", "@_");
		filtro = filtro.replace("%", "@%");
		filtro = filtro.trim();
		return filtro;
	}

	/**
	 * Método que escapa los carácteres especiales del filtro
	 */
	public String quitarEscapeFiltro(String filtro) {
		if (!StringUtils.isEmpty(filtro)) {
			filtro = filtro.replace("@", "");
		}
		return filtro;
	}

	/**
	 * Método publico de paginar
	 */
	public void paginar() {

		this.buscarTramites(filtro, false);
	}

	/**
	 * Retorno dialogo tramite.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoTramite(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			String message = null;
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral(LITERAL_INFO_ALTA_OK);
			} else {
				message = UtilJSF.getLiteral(LITERAL_INFO_MODIFICADO_OK);
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Refrescamos datos
			buscarTramites();
		}
	}

	/**
	 * Retorno dialogo mover tramite.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoMoverTramite(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled()) {
			// Mensaje
			String message = null;
			message = UtilJSF.getLiteral(LITERAL_INFO_MODIFICADO_OK);
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Refrescamos datos
			buscarTramites();
		}
	}

	/**
	 * Procedimientos tramite.
	 */
	public void procedimientosTramite() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaVersion() && !verificarFilaSeleccionadaTramite())
			return;

		final Map<String, String> params = new HashMap<>();
		if (verificarFilaSeleccionadaTramite()) {
			params.put(TypeParametroVentana.ID.toString(), this.tramiteSeleccionada.getTramite().getIdentificador());

		} else if (verificarFilaSeleccionadaVersion()) {
			final Tramite tramite = tramiteService.getTramite(this.versionSeleccionada.getIdTramite());
			params.put(TypeParametroVentana.ID.toString(), tramite.getIdentificadorCompuesto());
			params.put(TypeParametroVentana.VERSION.toString(),
					String.valueOf(this.versionSeleccionada.getNumeroVersion()));

		}

		UtilJSF.openDialog(DialogTramiteProcedimientos.class, TypeModoAcceso.EDICION, params, true, 1000, 500);

	}

	/**
	 * Verifica si hay algun area seleccionado
	 *
	 * @return
	 */
	private boolean verificarFilasSeleccionadasArea() {
		boolean filaSeleccionada = true;
		if (this.listaAreasSeleccionadas == null || listaAreasSeleccionadas.isEmpty()) {
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Verifica si hay fila seleccionada de area.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaArea() {
		boolean filaSeleccionada = true;
		if (this.listaAreasSeleccionadas == null || listaAreasSeleccionadas.isEmpty()) {
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Verifica si hay fila seleccionada de tramite.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaTramite() {
		boolean filaSeleccionada = true;
		if (this.tramiteSeleccionada == null) {
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Verifica si hay fila seleccionada de version.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaVersion() {
		boolean filaSeleccionada = true;
		if (this.versionSeleccionada == null) {
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/** Calcular areas cuando pulsas el checkbox de visualizar todas. */
	public void calcularAreas() {
		buscarAreas();
	}

	/** Buscar areas. */
	private void buscarAreas() {

		// Obtenemos todas las áreas.
		final List<Area> listaTodasAreas = tramiteService.listArea(UtilJSF.getSessionBean().getEntidad().getCodigo(),
				null);

		// Limpiamos las áreas seleccionadas y las áreas
		listaAreasSeleccionadas.clear();
		listaAreas.clear();

		// filtramos las areas del desarrollador
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			setListaAreas(listaTodasAreas);
		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
			for (final Area area : listaTodasAreas) {
				final List<TypeRolePermisos> permisos = securityService
						.getPermisosDesarrolladorEntidadByArea(area.getCodigo());

				if (UtilJSF.getEntorno().equals(TypeEntorno.DESARROLLO.toString())) {

					if (permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
							|| permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)
							|| permisos.contains(TypeRolePermisos.CONSULTA)) {
						area.setTienePermiso(true);
						getListaAreas().add(area);
					} else if (mostrarTodasAreas) {
						area.setTienePermiso(false);
						getListaAreas().add(area);
					}
				} else {
					if (permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
							|| permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)) {
						area.setTienePermiso(true);
						getListaAreas().add(area);
					} else if (mostrarTodasAreas) {
						area.setTienePermiso(false);
						getListaAreas().add(area);
					}

				}
			}
		}

		buscarAreasPreseleccionadas();

		buscarTramites();
	}

	/**
	 * Si hay un area marcada por url, lo marcaremos como la seleccionada. Sino,
	 * marcaremos todas como seleccionadas.
	 */
	private void buscarAreasPreseleccionadas() {
		if (StringUtils.isNotEmpty(idArea)) {
			for (final Area area : getListaAreas()) {
				if (area.getCodigo().compareTo(Long.valueOf(idArea)) == 0) {
					if (listaAreasSeleccionadas == null) {
						listaAreasSeleccionadas = new ArrayList<>();
					}
					listaAreasSeleccionadas.add(area);
				}
			}
		} else {
			if (listaAreasSeleccionadas == null) {
				listaAreasSeleccionadas = new ArrayList<>();
			}

			for (final Area area : getListaAreas()) {
				listaAreasSeleccionadas.add(area);
			}
		}

		// Lo desactivamos
		idArea = null;
	}

	/** Buscar tramites. **/
	private void buscarTramites() {
		buscarTramites(null, false);
	}

	/** Desmarca quitando la seleccion. **/
	private void desmarcar() {
		// Quitamos seleccion de dato
		tramiteSeleccionada = null;
		versionSeleccionada = null;

		if (listaTramiteVersiones == null) {
			listaTramiteVersiones = new ArrayList<>();
		} else {
			listaTramiteVersiones.clear();
		}
	}

	public boolean getTienePermiso() {

		for (Area ar : listaAreas) {
			List<TypeRolePermisos> permisos = securityService.getPermisosDesarrolladorEntidadByArea(ar.getCodigo());
			if (ar.getCodigo().equals(versionSeleccionada.getIdArea())
					&& !(permisos.size() == 1 && permisos.get(0).equals(TypeRolePermisos.CONSULTA))) {
				return ar.getTienePermiso();
			}
		}
		return false;

	}

	public void onResizeTable() {
		// String swidth =
		// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("anchura");
		// String sheight =
		// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("altura");
		// height = "100%";
		// width = "100%";

	}

	/**
	 * Para mostrar la paginacion de tramites.
	 *
	 * @return
	 */
	public boolean mostrarPaginacion() {
		return this.dataModel != null && this.dataModel.getWrappedData() != null
				&& !((List) this.dataModel.getWrappedData()).isEmpty();
	}

	/**
	 * Buscar tramites.
	 *
	 * @param filtro
	 */
	private void buscarTramites(final String filtro, final boolean nuevaArea) {

		desmarcar();

		this.dataModel = new LazyDataModel<TramiteVersiones>() {

			private static final long serialVersionUID = 1l;

			@Override
			public List<TramiteVersiones> load(int first, final int pageSize, final String sortField,
					final SortOrder sortOrder, final Map<String, Object> filters) {
				String filtroNuevo = null;
				UtilJSF.setPaginacion(pageSize, "viewTramites");
				listaTramiteVersiones.clear();
				setRowCount(tramiteService.listTramiteTotal(UtilJSF.getSessionBean().getEntidad().getCodigo(),
						convertirAreas(), filtro));

				List<Tramite> tramites = new ArrayList<>();
				String sortFieldAux = "";

				if (listaAreasSeleccionadas != null && !listaAreasSeleccionadas.isEmpty()) {
					if (nuevaArea) {
						first = 0;

					}
					if (!pag.equals("0")) {
						final int primero = Integer.parseInt(pag);
						first = primero * pageSize;

						pag = "0";
					}
					if (sortField != null && !sortField.isEmpty()) {
						sortFieldAux = sortField.substring(sortField.indexOf('.') + 1, sortField.length());
					}

					tramites = tramiteService.listTramite(UtilJSF.getSessionBean().getEntidad().getCodigo(),
							convertirAreas(), filtro);

				}

				// Obtenemos activa a los tramites que tengan alguna version activa

				for (final Tramite tramite : tramites) {
					final List<Long> idTramites = tramiteService.listTramiteVersionActiva(tramite.getIdArea());
					if (idTramites.contains(tramite.getCodigo())) {
						tramite.setActivo(true);
					}

					final List<TramiteVersion> listaVersiones = tramiteService.listTramiteVersion(tramite.getCodigo(),
							null);

					if (!StringUtils.isEmpty(filtro)) {
						filtroNuevo = filtro.replace("@", "");
					}

					if ((StringUtils.isNotEmpty(filtroNuevo)
							&& (tramite.getIdentificador().toUpperCase().contains(filtroNuevo.toUpperCase())
									|| tramite.getDescripcion().toUpperCase().contains(filtroNuevo.toUpperCase())))
							|| StringUtils.isEmpty(filtroNuevo))

						listaTramiteVersiones.add(new TramiteVersiones(tramite, listaVersiones));

				}

				if (idTramite != null && !idTramite.isEmpty()) {
					buscarTramitesPorDefecto();
				}

				if (sortFieldAux != null && !sortFieldAux.isEmpty()) {
					switch (sortFieldAux) {
					case "identificador":
						Collections.sort(listaTramiteVersiones, (o1, o2) -> o1.getTramite().getIdentificador()
								.toUpperCase().compareTo((o2.getTramite().getIdentificador().toUpperCase())));
						if (sortOrder.equals(SortOrder.DESCENDING)) {
							Collections.reverse(listaTramiteVersiones);
						}
						break;

					case "descripcion":
						Collections.sort(listaTramiteVersiones, (o1, o2) -> o1.getTramite().getDescripcion()
								.toUpperCase().compareTo((o2.getTramite().getDescripcion().toUpperCase())));
						if (sortOrder.equals(SortOrder.DESCENDING)) {
							Collections.reverse(listaTramiteVersiones);
						}
						break;

					case "area":
						Collections.sort(listaTramiteVersiones, (o1, o2) -> o1.getTramite().getIdentificadorArea()
								.toUpperCase().compareTo((o2.getTramite().getIdentificadorArea().toUpperCase())));
						if (sortOrder.equals(SortOrder.DESCENDING)) {
							Collections.reverse(listaTramiteVersiones);
						}
						break;

					case "activo":
						Collections.sort(listaTramiteVersiones, (o1, o2) -> Boolean.toString(o1.getTramite().isActivo())
								.compareTo((Boolean.toString(o2.getTramite().isActivo()))));
						if (sortOrder.equals(SortOrder.DESCENDING)) {
							Collections.reverse(listaTramiteVersiones);
						}
						break;

					case "ultima":
						Collections.sort(listaTramiteVersiones,
								(o1, o2) -> (getUltimaVersionFecha(o1.getTramite().getCodigo()))
										.compareTo((getUltimaVersionFecha(o2.getTramite().getCodigo()))));
						if (sortOrder.equals(SortOrder.DESCENDING)) {
							Collections.reverse(listaTramiteVersiones);
						}
						break;
					}
				} else {
					Collections.sort(listaTramiteVersiones, (o1, o2) -> o1.getTramite().getIdentificador()
							.compareTo((o2.getTramite().getIdentificador())));
				}

				List<TramiteVersiones> listAux = new ArrayList<TramiteVersiones>();
				for (int i = first; (i < pageSize + first) && (i < listaTramiteVersiones.size()); i++) {
					listAux.add(listaTramiteVersiones.get(i));
				}
				return listAux;

			}

			@Override
			public TramiteVersiones getRowData(final String rowKey) {

				for (final TramiteVersiones tramiteVersiones : listaTramiteVersiones) {

					if (tramiteVersiones.getTramite().getCodigo().toString().equals(rowKey)) {
						return tramiteVersiones;
					}
				}

				return null;
			}

			@Override
			public Object getRowKey(final TramiteVersiones tramiteVersiones) {
				return tramiteVersiones.getTramite().getCodigo();

			}

		};
	}

	private List<Long> convertirAreas() {
		final List<Long> areas = new ArrayList<>();
		if (listaAreasSeleccionadas != null) {
			for (final Area area : listaAreasSeleccionadas) {
				areas.add(area.getCodigo());
			}
		}
		return areas;
	}

	/**
	 * Método para buscar si tiene marcado un idTramite o idTramiteVersion por
	 * defecto.
	 */
	private void buscarTramitesPorDefecto() {
		/**
		 * Comprobamos si hay que marcar algo por defecto (tramite o tramite version).
		 **/
		final Long lIdTramite = Long.valueOf(idTramite);
		for (final TramiteVersiones itemTramiteVersiones : listaTramiteVersiones) {
			if (itemTramiteVersiones.getTramite().getCodigo().compareTo(lIdTramite) == 0) {
				this.tramiteSeleccionada = itemTramiteVersiones;

				break;
			}
		}

		/**
		 * Si tiene un trámite seleccionado, lo buscamos
		 */
		if (this.tramiteSeleccionada != null && idTramiteVersion != null && !idTramiteVersion.isEmpty()) {
			final Long lIdTramiteVersion = Long.valueOf(idTramiteVersion);
			for (final TramiteVersion version : this.tramiteSeleccionada.getListaVersiones()) {
				if (version.getCodigo().compareTo(lIdTramiteVersion) == 0) {
					this.versionSeleccionada = version;
					this.tramiteSeleccionada = null;
				}
			}
		}

	}

	/**
	 * Evento para expandir una fila. Sólo se expande si el código del trámite pasa
	 * por la url.
	 *
	 * @param tv
	 */
	public boolean expandir(final TramiteVersiones tv) {
		boolean expandir;
		if (idTramite != null && !idTramite.isEmpty()
				&& tv.getTramite().getCodigo().compareTo(Long.valueOf(idTramite)) == 0) {
			expandir = true;
		} else {
			expandir = false;
		}
		return expandir;
	}

	private int numPagina(Long idAreas, Long idTramite) {
		idArea = idAreas.toString();
		// refrescamos
		buscarAreas();
		int paginaIni = 0;
		this.dataModel.load(paginaIni, paginacion, null, null, null);
		// DataModel model = getDataModel();
		/*
		 * if (model != null && model instanceof LazyDataModel) { LazyDataModel
		 * lazyModel = (LazyDataModel) model; List<?> data = null; data =
		 * lazyModel.load(paginaIni, paginacion, null, null, null);
		 */
		int i = 0;
		boolean encontrado = false;
		while (!encontrado) {

			if (listaTramiteVersiones.get(i).getTramite().getCodigo().compareTo(idTramite) == 0) {
				encontrado = true;
				break;
			} else if (i == listaTramiteVersiones.size() - 1) {
				i = 0;
				paginaIni += paginacion;
				// data = lazyModel.load(paginaIni, paginacion, null, null, null);
				this.dataModel.load(paginaIni, paginacion, null, null, null);
			} else {
				i++;
			}
		}
		i += paginaIni;
		DecimalFormat formato1 = new DecimalFormat("000.00");
		float pEntera = ((float) i / (float) paginacion);
		int resultado = Integer.parseInt(formato1.format(pEntera).substring(0, formato1.format(pEntera).length() - 3));
		return resultado;
		/*
		 * } else { return 0; }
		 */

	}

	/**
	 * Abre dialogo para editar dato de Area.
	 */
	private void abrirArea(final TypeModoAcceso modoAcceso) {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea()) {
			return;
		}
		final Area areaSeleccionada = listaAreasSeleccionadas.get(0);

		params.put(TypeParametroVentana.ID.toString(), String.valueOf(areaSeleccionada.getCodigo()));
		UtilJSF.openDialog(DialogArea.class, modoAcceso, params, true, 520, 180);
	}

	/**
	 * Abre dialogo para editar tramite.
	 */
	private void abrirTramite(final TypeModoAcceso modoAcceso) {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();

		if (modoAcceso == TypeModoAcceso.ALTA) {
			final Area area = listaAreasSeleccionadas.get(0);
			params.put(TypeParametroVentana.AREA.toString(), String.valueOf(area.getCodigo()));
		} else {

			// Verifica si no hay fila seleccionada (editar/consultar)
			if (!verificarFilaSeleccionadaTramite()) {
				return;
			}

			params.put(TypeParametroVentana.ID.toString(),
					String.valueOf(this.tramiteSeleccionada.getTramite().getCodigo()));
		}
		UtilJSF.openDialog(DialogTramite.class, modoAcceso, params, true, 650, 220);
	}

	/**
	 * Abre dialogo para mover tramite.
	 */
	public void moverTramite() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaTramite()) {
			return;
		}

		boolean versionBloqueada = false;
		for (final TramiteVersion version : this.tramiteSeleccionada.getListaVersiones()) {
			if (version.getBloqueada()) {
				versionBloqueada = true;
				break;
			}
		}

		if (versionBloqueada) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewTramitesVersion.movertramite.versionbloqueada"));
			return;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(),
				String.valueOf(this.tramiteSeleccionada.getTramite().getCodigo()));
		UtilJSF.openDialog(DialogMoverTramite.class, TypeModoAcceso.EDICION, params, true, 540, 180);
	}

	/** UnselectTramite. **/
	public void unSelectTramite() {
		tramiteSeleccionada = null;
	}

	/** SelectTramite. **/
	public void unSelectVersionTramite() {
		this.setVersionSeleccionada(null);
		idTramite = null;
		idTramiteVersion = null;
	}

	/**
	 * Abre dialogo para nueva version.
	 */
	public void nuevaVersion() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();

		Long idTramiteSeleccionado = null;
		Long idArea = null;
		if (this.versionSeleccionada != null) {
			idTramiteSeleccionado = this.versionSeleccionada.getIdTramite();
			idArea = this.versionSeleccionada.getIdArea();
		} else {
			idTramiteSeleccionado = this.tramiteSeleccionada.getTramite().getCodigo();
			idArea = this.tramiteSeleccionada.getTramite().getIdArea();
		}
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(idTramiteSeleccionado));
		params.put(TypeParametroVentana.AREA.toString(), String.valueOf(idArea));
		UtilJSF.openDialog(DialogTramiteVersion.class, TypeModoAcceso.ALTA, params, true, 400, 180);

	}

	/**
	 * Retorno dialogo nueva version.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoNuevaVersion(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral(LITERAL_INFO_ALTA_OK);
			} else {
				message = UtilJSF.getLiteral(LITERAL_INFO_MODIFICADO_OK);
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			final TramiteVersion resultado = (TramiteVersion) respuesta.getResult();
			idArea = resultado.getIdArea().toString();
			idTramite = resultado.getIdTramite().toString();
			idTramiteVersion = resultado.getCodigo().toString();

			// Refrescamos datos
			buscarTramites(filtro, false);
			this.setVersionSeleccionada(resultado);
			PrimeFaces.current().ajax().update("form:toolbarTramites");
		}
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editarVersionDblClick() {
		if (this.getTienePermisosArea()) {
			editarVersion();
		} else {
			consultarVersion();
		}
	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editarVersion() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaVersion()) {
			return;
		}

		if (!this.getTienePermisosArea()) {
			return;
		}

		// Muestra dialogo
		final Map<String, List<String>> params = new HashMap<>();
		// params.put(TypeParametroVentana.ID.toString(),
		// final DataTable dataTable = (DataTable)
		// FacesContext.getCurrentInstance().getViewRoot()
		// .findComponent("form:dataTableTramites");
		// final int pagina = dataTable.getPage();

		// params.put("pagina", Arrays.asList(String.valueOf(1)));
		TramiteVersion versionSeleccionadaPre = versionSeleccionada;
		int numPag = numPagina(versionSeleccionada.getIdArea(), versionSeleccionada.getIdTramite());
		versionSeleccionada = versionSeleccionadaPre;
		params.put("pagina", Arrays.asList(String.valueOf(numPag)));
		params.put(TypeParametroVentana.ID.toString(), Arrays.asList(versionSeleccionada.getCodigo().toString()));
		UtilJSF.redirectJsfPage("/secure/app/viewDefinicionVersion.xhtml", params);

	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void consultarVersion() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaVersion())
			return;

		if (!this.getTienePermisosAreaConsulta()) {
			return;
		}

		// Muestra dialogo
		final Map<String, List<String>> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), Arrays.asList(String.valueOf(versionSeleccionada.getCodigo())));
		params.put(TypeParametroVentana.MODO_ACCESO.toString(), Arrays.asList(TypeModoAcceso.CONSULTA.name()));
		UtilJSF.redirectJsfPage("/secure/app/viewDefinicionVersion.xhtml", params);

	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminarVersion() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaVersion())
			return;

		// Solo se pueden eliminar versiones desbloqueadas
		if (this.versionSeleccionada.getBloqueada()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewTramitesVersion.eliminar.versionbloqueada"));
			return;
		}

		idArea = this.versionSeleccionada.getIdArea().toString();
		idTramite = this.versionSeleccionada.getIdTramite().toString();
		idTramiteVersion = null;
		// Eliminamos
		this.tramiteService.removeTramiteVersion(this.versionSeleccionada.getCodigo(),
				this.versionSeleccionada.getNumeroVersion());

		// Invalidaciones
		if (!UtilJSF.getEntorno().equals(TypeEntorno.DESARROLLO.toString())) {
			this.refrescar();
		}

		// Refrescamos datos
		// buscarTramites();
		this.versionSeleccionada = null;
		final Tramite tramCrumb = tramiteService.getTramite(Long.valueOf(idTramite));

		tramiteSeleccionadaBreadcrumb = new TramiteVersiones(tramCrumb, null);
		tramiteSeleccionada = tramiteSeleccionadaBreadcrumb;
		// Mostramos mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral(LITERAL_INFO_BORRADO_OK));
	}

	/**
	 * Bloquear version. Pautas a seguir:
	 * <ul>
	 * <li>Revisar si el tramite versión está bloqueado.</li>
	 * <li>Revisar si tiene permiso de edición.</li>
	 * <li>Sólo se puede bloquear en el entorno de desarrollo.</li>
	 * </ul>
	 */
	public void bloquear() {

		if (!isPermiteBloquear() || !getTienePermisosTramite()) {
			return;
		}

		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewTramitesVersion.entorno.noDesarrollo"));
			return;
		}

		tramiteService.bloquearTramiteVersion(this.versionSeleccionada.getCodigo(),
				UtilJSF.getSessionBean().getUserName());

		editarVersion();

	}

	/**
	 * Devuelve la ultima version editada
	 */
	private String getUltimaVersion(Long tramite) {
		final List<TramiteVersion> listaVersiones = tramiteService.listTramiteVersion(tramite, null);
		TramiteVersion ftv = null;
		for (TramiteVersion tv : listaVersiones) {
			if (ftv == null || ftv.getFechaUltima() == null || (tv != null && tv.getFechaUltima() != null
					&& ftv.getFechaUltima().compareTo(tv.getFechaUltima()) < 1)) {
				ftv = tv;
			}
		}
		if (ftv != null) {
			return Integer.toString(ftv.getNumeroVersion());
		} else {
			return "-1";
		}
	}

	/**
	 * Devuelve la fecha ultima version editada
	 */
	private Date getUltimaVersionFecha(Long tramite) {
		final List<TramiteVersion> listaVersiones = tramiteService.listTramiteVersion(tramite, null);
		Date ftv = null;
		for (TramiteVersion tv : listaVersiones) {
			if (ftv == null || (tv != null && tv.getFechaUltima() != null && ftv.compareTo(tv.getFechaUltima()) < 1)) {
				ftv = tv.getFechaUltima();
			}
		}
		if (ftv != null) {
			return ftv;
		} else {
			return new Date(0);
		}
	}

	/**
	 * Devuelve la ultima version editada parseada
	 */
	public String getUltimaVersionFormateado(Long tramite) {
		String res = getUltimaVersion(tramite);
		if (Integer.parseInt(res) >= 0) {
			return res;
		} else {
			return "";
		}
	}

	/**
	 * Bloquear version.
	 */
	public void desbloquear() {

		if (!isPermiteDesbloquear() || !getTienePermisosTramite()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.versionSeleccionada.getCodigo().toString());

		UtilJSF.openDialog(DialogTramiteDesbloquear.class, TypeModoAcceso.EDICION, params, true, 500, 320);

	}

	/**
	 * Refrescar cache.
	 */
	public void refrescarCache() {
		final String urlBase = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_URL.toString());
		final String usuario = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_USER.toString());
		final String pwd = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_PWD.toString());

		final ResultadoError resultado = UtilRest.refrescar(urlBase, usuario, pwd, Constantes.CACHE_COMPLETA);

		if (resultado.getCodigo() == 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.refrescar"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("error.refrescar") + ": " + resultado.getMensaje());
		}

	}

	public void ordenarDatatble(final String campo) {
		this.buscarTramites(null, false);
	}

	/**
	 * El returnDialog de desbloquear.
	 *
	 * @param event
	 */
	public void returnDialogoDesbloquear(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled()) {

			recuperdatosVersion();
			this.versionSeleccionada.setBloqueada(false);
			// buscarTramites();
		}
	}

	private void recuperdatosVersion() {
		idArea = this.versionSeleccionada.getIdArea().toString();
		idTramite = this.versionSeleccionada.getIdTramite().toString();

		this.tramiteSeleccionada = null;
		idTramiteVersion = versionSeleccionada.getCodigo().toString();
	}

	/**
	 * Calcula si se puede bloquear el tramite o no.
	 *
	 */
	public boolean isPermiteBloquear() {

		// Si no hay versión seleccionada o está bloqueada
		if (this.versionSeleccionada == null || versionSeleccionada.getBloqueada()) {
			return false;
		}

		// Solo se puede bloquear/desbloquear en desarrollo
		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			return false;
		}

		// El ADM. ENTIDAD puede desbloquearlo
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			return true;
		}

		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {

			if (this.idTramiteCacheado == null
					|| this.idTramiteCacheado.compareTo(this.versionSeleccionada.getIdTramite()) != 0) {
				this.actualizarPermisosCacheados(this.versionSeleccionada.getIdArea());
				this.idTramiteCacheado = this.versionSeleccionada.getIdTramite();
			}

			return permisosCacheados.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
					|| permisosCacheados.contains(TypeRolePermisos.DESARROLLADOR_AREA);
		}

		return true;

	}

	/**
	 * Calcula si se puede desbloquear el tramite o no. Se puede desbloquear si: -
	 * Estamos en desarrollo. - Si eres ADM. ENTIDAD puedes. - Si eres
	 * desarrollador: - Si eres adm. AREA puedes desbloquear - Si eres des. AREA
	 * sólo puedes desbloquear si eres el que lo bloqueó.
	 *
	 *
	 */
	public boolean isPermiteDesbloquear() {

		// Si no hay versión seleccionada o ya está desbloqueada
		if (this.versionSeleccionada == null || !versionSeleccionada.getBloqueada()) {
			return false;
		}

		// Solo se puede bloquear/desbloquear en desarrollo
		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			return false;
		}

		// El ADM. ENTIDAD puede desbloquearlo
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			return true;
		}

		// Tiene que estar bloqueado por el mismo usuario
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR && this.versionSeleccionada != null) {

			if (this.idTramiteCacheado == null
					|| this.idTramiteCacheado.compareTo(this.versionSeleccionada.getIdTramite()) != 0) {
				this.actualizarPermisosCacheados(this.versionSeleccionada.getIdArea());
				this.idTramiteCacheado = this.versionSeleccionada.getIdTramite();
			}

			if (permisosCacheados.contains(TypeRolePermisos.ADMINISTRADOR_AREA)) {
				return true;
			}

			if (permisosCacheados.contains(TypeRolePermisos.DESARROLLADOR_AREA) && UtilJSF.getSessionBean()
					.getUserName().equals(this.versionSeleccionada.getDatosUsuarioBloqueo())) {
				return true;
			}

			return false;
		}

		return true;
	}

	/**
	 * Genera todos los scripts.
	 */
	public void generarScript() {

		if (!verificarFilaSeleccionadaVersion()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.versionSeleccionada.getCodigo().toString());
		UtilJSF.openDialog(DialogTramiteScripts.class, TypeModoAcceso.EDICION, params, true, 1000, 520);
	}

	/**
	 * Borra todos los scripts.
	 */
	public void borrarScript() {

		if (!verificarFilaSeleccionadaVersion()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.versionSeleccionada.getCodigo().toString());
		final Tramite tramite = tramiteService.getTramite(this.versionSeleccionada.getIdTramite());
		params.put(TypeParametroVentana.TRAMITE.toString(), tramite.getIdentificador());
		params.put(TypeParametroVentana.VERSION.toString(),
				String.valueOf(this.versionSeleccionada.getNumeroVersion()));
		UtilJSF.openDialog(DialogTramiteBorrarScripts.class, TypeModoAcceso.EDICION, params, true, 700, 360);
	}

	/**
	 * Exportar version.
	 */
	public void exportarVersion() {
		exportarTramiteVersion(Constantes.IMPORTAR_TIPO_IM);
	}

	/**
	 * Generar cuaderno carga.
	 */
	public void generarCuadernoCarga() {
		exportarTramiteVersion(Constantes.IMPORTAR_TIPO_CC);
	}

	/**
	 * Exportar version.
	 */
	public void exportarTramiteVersion(final String modo) {
		if (!verificarFilaSeleccionadaVersion()) {
			return;
		}

		/** No se pueden exportar las bloqueadas. **/
		if (this.versionSeleccionada.getBloqueada()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteExportar.error.tramiteBloqueado"));
			return;
		}

		// validamos antes de exportar
		if (!validoTramiteVersion(false)) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.versionSeleccionada.getCodigo().toString());
		params.put(TypeParametroVentana.MODO_IMPORTAR.toString(), modo);
		UtilJSF.openDialog(DialogTramiteExportar.class, TypeModoAcceso.EDICION, params, true, 900, 520);
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
			final TramiteVersion resultado = (TramiteVersion) respuesta.getResult();
			filtro = "";
			filtrar();
			idArea = resultado.getIdArea().toString();
			idTramite = resultado.getIdTramite().toString();
			idTramiteVersion = resultado.getCodigo().toString();
			listaAreasSeleccionadas.clear();
			listaAreasSeleccionadas.add(tramiteService.getArea(resultado.getIdArea()));
			buscarTramites();
			resultado.setBloqueada(false);
			pag = Integer.toString(numPagina(resultado.getIdArea(), resultado.getIdTramite()));
			this.setVersionSeleccionada(resultado);
			PrimeFaces.current().ajax().update("form:toolbarTramites");
		}

	}

	/**
	 * Duplicar.
	 */
	public void duplicarVersion() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaVersion()) {
			return;
		}

		if (this.versionSeleccionada.getBloqueada()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("dialogTramiteExportar.error.tramiteBloqueado"));
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.versionSeleccionada.getCodigo()));
		UtilJSF.openDialog(DialogTramiteClonar.class, TypeModoAcceso.ALTA, params, true, 550, 200);

	}

	/**
	 * Historial.
	 */
	public void historialVersion() {

		if (!verificarFilaSeleccionadaVersion()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.versionSeleccionada.getCodigo().toString());
		UtilJSF.openDialog(DialogHistorialVersion.class, TypeModoAcceso.CONSULTA, params, true, 900, 520);

	}

	/**
	 * Obtiene el identificador de un area
	 *
	 * @param idArea
	 * @return
	 */
	public String getIdentificadorArea(final String idArea) {

		for (final Area area : listaAreasSeleccionadas) {
			if (area.getCodigo().equals(Long.valueOf(idArea))) {
				return area.getIdentificador();
			}
		}
		return "";

	}

	/**
	 * Ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("tramites");
	}

	/**
	 * Valida version.
	 */
	public void validarVersion() {

		if (!verificarFilaSeleccionadaVersion()) {
			return;
		}

		// miramos si esta bloqueado o no para permitir la correccion de errores
		if (validoTramiteVersion(this.versionSeleccionada.getBloqueada())) {

			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.validacion"));

		}
	}

	/**
	 * Valido tramite version.
	 *
	 * @param pEdicion the edicion
	 * @return true, if successful
	 */
	private boolean validoTramiteVersion(final boolean pEdicion) {
		final List<ErrorValidacion> listaErrores = tramiteService
				.validarVersionTramite(this.versionSeleccionada.getCodigo(), UtilJSF.getSessionBean().getLang());

		if (!listaErrores.isEmpty()) {
			Map<String, String> params = null;
			TypeModoAcceso modoAccesoErrores;

			final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

			mochilaDatos.put(Constantes.CLAVE_MOCHILA_ERRORESVALIDACION,
					listaErrores.stream().map(SerializationUtils::clone).collect(java.util.stream.Collectors.toList()));

			params = new HashMap<>();
			params.put(TypeParametroVentana.IDIOMAS.toString(),
					tramiteService.getIdiomasDisponibles(String.valueOf(this.versionSeleccionada.getCodigo())));

			params.put(TypeParametroVentana.TRAMITE.toString(),
					String.valueOf(this.versionSeleccionada.getIdTramite()));

			params.put(TypeParametroVentana.TRAMITEVERSION.toString(),
					String.valueOf(this.versionSeleccionada.getCodigo()));
			if (pEdicion) {
				modoAccesoErrores = TypeModoAcceso.EDICION;
			} else {
				modoAccesoErrores = TypeModoAcceso.CONSULTA;
			}

			UtilJSF.openDialog(DialogErroresValidacion.class, modoAccesoErrores, params, true, 1050, 520);
			return false;
		}

		return true;
	}

	/**
	 * Obtiene el valor de filtro.
	 *
	 * @return el valor de filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * Establece el valor de filtro.
	 *
	 * @param filtro el nuevo valor de filtro
	 */
	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	/**
	 * Obtiene el valor de tramiteSeleccionada.
	 *
	 * @return el valor de tramiteSeleccionada
	 */
	public TramiteVersiones getTramiteSeleccionada() {
		return tramiteSeleccionada;
	}

	/**
	 * Establece el valor de tramiteSeleccionada.
	 *
	 * @param tramiteSeleccionada el nuevo valor de tramiteSeleccionada
	 */
	public void setTramiteSeleccionada(final TramiteVersiones tramiteSeleccionada) {
		this.tramiteSeleccionada = tramiteSeleccionada;
	}

	/**
	 * @return the idArea
	 */
	public String getIdArea() {
		return idArea;
	}

	/**
	 * @param idArea the idArea to set
	 */
	public void setIdArea(final String idArea) {
		this.idArea = idArea;
	}

	public TramiteVersion getVersionSeleccionada() {
		return versionSeleccionada;
	}

	public void setVersionSeleccionada(final TramiteVersion versionSeleccionado) {
		this.versionSeleccionada = versionSeleccionado;
	}

	public List<TramiteVersiones> getListaTramiteVersiones() {
		return listaTramiteVersiones;
	}

	public void setListaTramiteVersiones(final List<TramiteVersiones> listaTramiteVersiones) {
		this.listaTramiteVersiones = listaTramiteVersiones;
	}

	public List<Area> getListaAreas() {
		return listaAreas;
	}

	public void setListaAreas(final List<Area> listaAreas) {
		this.listaAreas = listaAreas;
	}

	public List<Area> getListaAreasSeleccionadas() {
		return listaAreasSeleccionadas;
	}

	public void setListaAreasSeleccionadas(final List<Area> listaAreasSeleccionadas) {
		this.listaAreasSeleccionadas = listaAreasSeleccionadas;
	}

	public boolean isPermiteRefrescar() {
		return permiteRefrescar;
	}

	public void setPermiteRefrescar(final boolean permiteRefrescar) {
		this.permiteRefrescar = permiteRefrescar;
	}

	/**
	 * @return the idTramite
	 */
	public String getIdTramite() {
		return idTramite;
	}

	/**
	 * @param idTramite the idTramite to set
	 */
	public void setIdTramite(final String idTramite) {
		this.idTramite = idTramite;
	}

	/**
	 * @return the idTramiteVersion
	 */
	public String getIdTramiteVersion() {
		return idTramiteVersion;
	}

	/**
	 * @param idTramiteVersion the idTramiteVersion to set
	 */
	public void setIdTramiteVersion(final String idTramiteVersion) {
		this.idTramiteVersion = idTramiteVersion;
	}

	/**
	 * Get seleccionadas areas
	 *
	 * @return
	 */
	public boolean getSeleccionadasAreas() {
		return verificarFilasSeleccionadasArea();
	}

	/**
	 * Get seleccionada area.
	 *
	 * @return
	 */
	public boolean getSeleccionadaArea() {
		return verificarFilaSeleccionadaArea();
	}

	public boolean isMostrarTodasAreas() {
		return mostrarTodasAreas;
	}

	public void setMostrarTodasAreas(final boolean mostrarTodasAreas) {
		this.mostrarTodasAreas = mostrarTodasAreas;
	}

	public LazyDataModel<TramiteVersiones> getDataModel() {
		return dataModel;
	}

	public void setDataModel(final LazyDataModel<TramiteVersiones> dataModel) {
		this.dataModel = dataModel;
	}

	public boolean isAdmEntidad() {
		return admEntidad;
	}

	public void setAdmEntidad(final boolean admEntidad) {
		this.admEntidad = admEntidad;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(final String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(final String width) {
		this.width = width;
	}

	public String getPag() {
		return pag;
	}

	public void setPag(final String pag) {
		this.pag = pag;
	}

	public List<Dominio> getDominiosNoUtilizados() {
		return dominiosNoUtilizados;
	}

	public void setDominiosNoUtilizados(final List<Dominio> dominiosNoUtilizados) {
		this.dominiosNoUtilizados = dominiosNoUtilizados;
	}

	public List<String> getScriptsProblematicos() {
		return scriptsProblematicos;
	}

	public void setScriptsProblematicos(final List<String> scriptsProblematicos) {
		this.scriptsProblematicos = scriptsProblematicos;
	}

	public String getIdAreaAux() {
		return idAreaAux;
	}

	public void setIdAreaAux(final String idAreaAux) {
		this.idAreaAux = idAreaAux;
	}

	public String getIdTramiteAux() {
		return idTramiteAux;
	}

	public void setIdTramiteAux(final String idTramiteAux) {
		this.idTramiteAux = idTramiteAux;
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

	public final String getPortapapeles() {
		return portapapeles;
	}

	public final void setPortapapeles(String portapapeles) {
		this.portapapeles = portapapeles;
	}

	public final Boolean getRenderCmenu() {
		return renderCmenu;
	}

	public final void setRenderCmenu(Boolean renderCmenu) {
		this.renderCmenu = renderCmenu;
	}

	public boolean isServicioActivado() {
		return UtilJSF.isServicioActivado();
	}
}
