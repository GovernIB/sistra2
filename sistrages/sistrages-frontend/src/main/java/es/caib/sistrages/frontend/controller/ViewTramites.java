package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.ComponenteFormulario;
import es.caib.sistrages.core.api.model.ComponenteFormularioCampoSelector;
import es.caib.sistrages.core.api.model.DisenyoFormulario;
import es.caib.sistrages.core.api.model.Documento;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.FormularioTramite;
import es.caib.sistrages.core.api.model.LineaComponentesFormulario;
import es.caib.sistrages.core.api.model.PaginaFormulario;
import es.caib.sistrages.core.api.model.Script;
import es.caib.sistrages.core.api.model.Tasa;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramitePaso;
import es.caib.sistrages.core.api.model.TramitePasoAnexar;
import es.caib.sistrages.core.api.model.TramitePasoRegistrar;
import es.caib.sistrages.core.api.model.TramitePasoRellenar;
import es.caib.sistrages.core.api.model.TramitePasoTasa;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.comun.ErrorValidacion;
import es.caib.sistrages.core.api.model.comun.FilaImportarResultado;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
import es.caib.sistrages.core.api.model.types.TypeObjetoFormulario;
import es.caib.sistrages.core.api.model.types.TypePaso;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.FormularioInternoService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.TramiteVersiones;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

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

	/**
	 * Inicializacion.
	 */
	public void init() {

		dominiosNoUtilizados = null;
		height = "100%";
		width = "100%";
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

		mostrarTodasAreas = false;
		buscarAreas();

		if (idTramite != null) {

			Tramite tramCrumb = tramiteService.getTramite(Long.valueOf(idTramite));

			tramiteSeleccionadaBreadcrumb = new TramiteVersiones(tramCrumb, null);
			tramiteSeleccionada = tramiteSeleccionadaBreadcrumb;
		}

		if (idTramiteVersion != null) {
			tramiteSeleccionada = null;
			versionSeleccionada = tramiteService.getTramiteVersion(Long.valueOf(idTramiteVersion));
		}

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
		UtilJSF.openDialog(DialogArea.class, TypeModoAcceso.ALTA, null, true, 520, 160);
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
		if (tramiteService.removeArea(area.getCodigo())) {
			listaAreasSeleccionadas.clear();
			this.buscarAreas();
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral(LITERAL_INFO_BORRADO_OK));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		}

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

			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.importar.ok"));

			final FilaImportarResultado resultado = (FilaImportarResultado) respuesta.getResult();
			idArea = resultado.getIdArea().toString();
			idTramite = resultado.getIdTramite().toString();
			idTramiteVersion = resultado.getIdTramiteVersion().toString();

			// Refrescamos datos
			buscarAreas();

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

	/**
	 * Retorno dialogo control acceso.
	 *
	 * @param event respuesta dialogo
	 */
	public void returnDialogoControlAcceso(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			final String message = UtilJSF.getLiteral(LITERAL_INFO_MODIFICADO_OK);
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
		UtilJSF.openDialog(DialogTramiteVersionPrevisualizar.class, TypeModoAcceso.CONSULTA, params, true, 950, 400);
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
		return res;
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
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("form:dataTableTramites");
		dataTable.setFirst(0);

		idTramiteVersion = null;
		idTramite = null;
		this.buscarTramites(filtro, true);

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
			params.put(TypeParametroVentana.ID.toString(), tramite.getIdentificador());
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
		if (this.listaAreasSeleccionadas == null || listaAreasSeleccionadas.isEmpty()
				|| listaAreasSeleccionadas.size() > 1) {
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

	public void onResizeTable() {
//		String swidth = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("anchura");
//		String sheight = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("altura");
//		height = "100%";
//		width = "100%";

	}

	/**
	 * Buscar tramites.
	 *
	 * @param filtro
	 */
	private void buscarTramites(final String filtro, boolean nuevaArea) {

		desmarcar();

		this.dataModel = new LazyDataModel<TramiteVersiones>() {

			private static final long serialVersionUID = 1l;

			@Override
			public List<TramiteVersiones> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				listaTramiteVersiones.clear();
				setRowCount(tramiteService.listTramiteTotal(UtilJSF.getSessionBean().getEntidad().getCodigo(),
						convertirAreas(), filtro));

				List<Tramite> tramites = new ArrayList<>();

				if (listaAreasSeleccionadas != null && !listaAreasSeleccionadas.isEmpty()) {
					if (nuevaArea) {
						first = 0;

					}
					if (pag != null) {
						int primero = Integer.parseInt(pag);
						first = primero * 10;

						pag = null;
					}
					tramites = tramiteService.listTramite(first, pageSize, sortField, true,
							UtilJSF.getSessionBean().getEntidad().getCodigo(), convertirAreas(), filtro);
				}

				// Obtenemos activa a los tramites que tengan alguna version activa

				for (final Tramite tramite : tramites) {
					final List<Long> idTramites = tramiteService.listTramiteVersionActiva(tramite.getIdArea());
					if (idTramites.contains(tramite.getCodigo())) {
						tramite.setActivo(true);
					}

					final List<TramiteVersion> listaVersiones = tramiteService.listTramiteVersion(tramite.getCodigo(),
							null);

					if ((StringUtils.isNotEmpty(filtro)
							&& (tramite.getIdentificador().toUpperCase().contains(filtro.toUpperCase())
									|| tramite.getDescripcion().toUpperCase().contains(filtro.toUpperCase())))
							|| StringUtils.isEmpty(filtro))
						listaTramiteVersiones.add(new TramiteVersiones(tramite, listaVersiones));
				}

				if (idTramite != null && !idTramite.isEmpty()) {
					buscarTramitesPorDefecto();
				}

				Collections.sort(listaTramiteVersiones,
						(o1, o2) -> o1.getTramite().getIdentificador().compareTo((o2.getTramite().getIdentificador())));

				return listaTramiteVersiones;

			}

			@Override
			public TramiteVersiones getRowData(String rowKey) {

				for (TramiteVersiones tramiteVersiones : listaTramiteVersiones) {

					if (tramiteVersiones.getTramite().getCodigo().toString().equals(rowKey)) {
						return tramiteVersiones;
					}
				}

				return null;
			}

			@Override
			public Object getRowKey(TramiteVersiones tramiteVersiones) {
				return tramiteVersiones.getTramite().getCodigo();

			}

		};

	}

	private List<Long> convertirAreas() {
		List<Long> areas = new ArrayList<>();
		if (listaAreasSeleccionadas != null) {
			for (Area area : listaAreasSeleccionadas) {
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
		UtilJSF.openDialog(DialogArea.class, modoAcceso, params, true, 520, 160);
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
		UtilJSF.openDialog(DialogTramite.class, modoAcceso, params, true, 540, 220);
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
		versionSeleccionada = null;
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
		UtilJSF.openDialog(DialogTramiteVersion.class, TypeModoAcceso.ALTA, params, true, 400, 150);

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
			buscarTramites();
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
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("form:dataTableTramites");
		int pagina = dataTable.getPage();
		params.put("pagina", Arrays.asList(String.valueOf(pagina)));
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

		// Eliminamos
		this.tramiteService.removeTramiteVersion(this.versionSeleccionada.getCodigo(),
				this.versionSeleccionada.getNumeroVersion());

		// Invalidaciones
		if (!UtilJSF.getEntorno().equals(TypeEntorno.DESARROLLO.toString())) {
			final Tramite tramite = tramiteService.getTramite(this.versionSeleccionada.getIdTramite());
			final String urlBase = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_URL);
			final String usuario = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_USER);
			final String pwd = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_PWD);
			this.refrescarCache(urlBase, usuario, pwd, Constantes.CACHE_TRAMITE,
					tramite.getIdentificador() + "#" + this.versionSeleccionada.getNumeroVersion());
		}

		// Refrescamos datos
		buscarTramites();

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
	 * El returnDialog de desbloquear.
	 *
	 * @param event
	 */
	public void returnDialogoDesbloquear(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled()) {
			buscarTramites();
		}
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
	 * Calcula si se puede desbloquear el tramite o no. Se puede desblqouear si: -
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
		Tramite tramite = tramiteService.getTramite(this.versionSeleccionada.getIdTramite());
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
	 * Metodo prinicpal para refrescar la cache de un tramite (es llamado desde
	 * viewTramites y viewDefinicionVersion)
	 *
	 * @param identificadorTramite
	 */
	public void refrescarCache(final String identificadorTramite, final int numeroVersion) {
		if (identificadorTramite != null) {
			final String urlBase = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_URL);
			final String usuario = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_USER);
			final String pwd = systemService.obtenerPropiedadConfiguracion(Constantes.SISTRAMIT_REST_PWD);

			this.refrescarCache(urlBase, usuario, pwd, Constantes.CACHE_TRAMITE,
					identificadorTramite + "#" + numeroVersion);
		}
	}

	/**
	 * Regrescar.
	 */
	public void refrescar() {
		String identificador = null;

		for (final TramiteVersiones tramite : listaTramiteVersiones) {
			if (tramite.getTramite().getCodigo().equals(versionSeleccionada.getIdTramite())) {
				identificador = tramite.getTramite().getIdentificador();
				break;
			}
		}

		if (identificador != null) {
			refrescarCache(identificador, versionSeleccionada.getNumeroVersion());
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
			buscarTramites();
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
		if (validoTramiteVersion(!this.versionSeleccionada.getBloqueada())) {

			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.validacion"));

		}
	}

	private void eliminarDominio(Dominio dom) {
		int i = 0;
		boolean eliminar = false;
		for (Dominio domUsado : dominiosNoUtilizados) {
			if (domUsado.equals(dom)) {
				eliminar = true;
				break;
			} else {
				i++;
			}
		}
		if (eliminar) {
			dominiosNoUtilizados.remove(i);
		}
	}

	/**
	 * Verificar dominio.
	 *
	 * @param dominios the dominios
	 * @param codForm  the cod form
	 */
	private void verificarDominio(List<Dominio> dominios, Long codForm) {
		DisenyoFormulario formulario = formIntService.getFormularioInternoCompleto(codForm);
		if (formulario.getScriptPlantilla() != null) {
			tieneDominio(formulario.getScriptPlantilla(), dominios);

		}
		for (PaginaFormulario pag : formulario.getPaginas()) {
			dominioScriptPagina(pag, dominios);

			for (LineaComponentesFormulario linea : pag.getLineas()) {

				for (ComponenteFormulario selector : linea.getComponentes()) {
					if (selector.getTipo().equals(TypeObjetoFormulario.SELECTOR)) {
						ComponenteFormularioCampoSelector campoSelector = (ComponenteFormularioCampoSelector) selector;
						for (Dominio dom : dominios) {

							if (campoSelector.getCodDominio() != null
									&& campoSelector.getCodDominio().equals(dom.getCodigo())) {
								eliminarDominio(dom);

							}
						}
					}
				}

			}

		}

	}

	/**
	 * Dominio script pagina.
	 *
	 * @param pag the pag
	 */
	private void dominioScriptPagina(PaginaFormulario pag, List<Dominio> dominios) {

		if (pag.getScriptNavegacion() != null) {
			tieneDominio(pag.getScriptNavegacion(), dominios);

		}
		if (pag.getScriptValidacion() != null) {
			tieneDominio(pag.getScriptValidacion(), dominios);

		}

	}

	/**
	 * Dominio script.
	 *
	 * @param pasoForm the paso form
	 */
	private void dominioScript(TramitePasoRellenar pasoForm, List<Dominio> dominios) {
		TramiteVersion tramiteVersionDom = tramiteService.getTramiteVersion(this.versionSeleccionada.getCodigo());

		if (tramiteVersionDom.getScriptInicializacionTramite() != null) {
			tieneDominio(tramiteVersionDom.getScriptInicializacionTramite(), dominios);

		}
		if (tramiteVersionDom.getScriptPersonalizacion() != null) {
			tieneDominio(tramiteVersionDom.getScriptPersonalizacion(), dominios);

		}
		if (pasoForm.getScriptNavegacion() != null) {
			tieneDominio(pasoForm.getScriptNavegacion(), dominios);

		}
		if (pasoForm.getScriptVariables() != null) {
			tieneDominio(pasoForm.getScriptVariables(), dominios);

		}

	}

	/**
	 * Dominio script formulario.
	 *
	 * @param formTram the form tram
	 */
	private void dominioScriptFormulario(FormularioTramite formTram, List<Dominio> dominios) {

		if (formTram.getScriptDatosIniciales() != null) {
			tieneDominio(formTram.getScriptDatosIniciales(), dominios);

		}
		if (formTram.getScriptFirma() != null) {
			tieneDominio(formTram.getScriptFirma(), dominios);

		}
		if (formTram.getScriptObligatoriedad() != null) {
			tieneDominio(formTram.getScriptObligatoriedad(), dominios);

		}
		if (formTram.getScriptParametros() != null) {
			tieneDominio(formTram.getScriptParametros(), dominios);

		}
		if (formTram.getScriptRetorno() != null) {
			tieneDominio(formTram.getScriptRetorno(), dominios);

		}

	}

	/**
	 * Dominio script anexar.
	 *
	 * @param pasoAnexar the paso anexar
	 */
	private void dominioScriptAnexar(TramitePasoAnexar pasoAnexar, List<Dominio> dominios) {

		if (pasoAnexar.getScriptAnexosDinamicos() != null) {
			tieneDominio(pasoAnexar.getScriptAnexosDinamicos(), dominios);

		}
		if (pasoAnexar.getDocumentos() != null) {
			for (Documento document : pasoAnexar.getDocumentos()) {
				if (document.getScriptFirmarDigitalmente() != null) {
					tieneDominio(document.getScriptFirmarDigitalmente(), dominios);

				}
				if (document.getScriptObligatoriedad() != null) {
					tieneDominio(document.getScriptObligatoriedad(), dominios);

				}
				if (document.getScriptValidacion() != null) {
					tieneDominio(document.getScriptValidacion(), dominios);

				}
			}
		}

	}

	/**
	 * Dominio script pagar.
	 *
	 * @param pasoPagar the paso pagar
	 */
	private void dominioScriptPagar(TramitePasoTasa pasoPagar, List<Dominio> dominios) {

		if (pasoPagar.getTasas() != null) {
			for (Tasa tasa : pasoPagar.getTasas()) {
				if (tasa.getScriptObligatoriedad() != null) {
					tieneDominio(tasa.getScriptObligatoriedad(), dominios);

				}
				if (tasa.getScriptPago() != null) {
					tieneDominio(tasa.getScriptPago(), dominios);

				}

			}
		}
	}

	/**
	 * Dominio script registrar.
	 *
	 * @param pasoRegistrar the paso registrar
	 */
	private void dominioScriptRegistrar(TramitePasoRegistrar pasoRegistrar, List<Dominio> dominios) {

		if (pasoRegistrar.getScriptAlFinalizar() != null) {
			tieneDominio(pasoRegistrar.getScriptAlFinalizar(), dominios);

		}
		if (pasoRegistrar.getScriptDestinoRegistro() != null) {
			tieneDominio(pasoRegistrar.getScriptDestinoRegistro(), dominios);

		}
		if (pasoRegistrar.getScriptNavegacion() != null) {
			tieneDominio(pasoRegistrar.getScriptNavegacion(), dominios);

		}
		if (pasoRegistrar.getScriptPresentador() != null) {
			tieneDominio(pasoRegistrar.getScriptPresentador(), dominios);

		}
		if (pasoRegistrar.getScriptRepresentante() != null) {
			tieneDominio(pasoRegistrar.getScriptRepresentante(), dominios);

		}
		if (pasoRegistrar.getScriptValidarRegistrar() != null) {
			tieneDominio(pasoRegistrar.getScriptValidarRegistrar(), dominios);
		}
		if (pasoRegistrar.getScriptVariables() != null) {
			tieneDominio(pasoRegistrar.getScriptVariables(), dominios);

		}

	}

	/**
	 * Tiene dominio.
	 *
	 * @param script the script
	 */
	private void tieneDominio(Script script, List<Dominio> dominios) {
		for (Dominio dom : dominios) {
			if (script.getContenido().contains(dom.getDescripcion())) {
				eliminarDominio(dom);

			}
		}

	}

	/**
	 * Dom paso rellenar.
	 *
	 * @param paso     the paso
	 * @param dominios the dominios
	 */
	private void domPasoRellenar(TramitePaso paso, List<Dominio> dominios) {
		final TramitePasoRellenar pasoForm = (TramitePasoRellenar) paso;

		dominioScript(pasoForm, dominios);

		for (FormularioTramite formTram : pasoForm.getFormulariosTramite()) {
			if (formTram.getIdFormularioInterno() != null) {
				verificarDominio(dominios, formTram.getIdFormularioInterno());

				dominioScriptFormulario(formTram, dominios);

			}
		}

	}

	/**
	 * Dom paso anexar.
	 *
	 * @param paso the paso
	 */
	private void domPasoAnexar(TramitePaso paso, List<Dominio> dominios) {
		final TramitePasoAnexar pasoAnexar = (TramitePasoAnexar) paso;

		dominioScriptAnexar(pasoAnexar, dominios);

	}

	/**
	 * Dom paso pagar.
	 *
	 * @param paso the paso
	 */
	private void domPasoPagar(TramitePaso paso, List<Dominio> dominios) {
		final TramitePasoTasa pasopagar = (TramitePasoTasa) paso;

		dominioScriptPagar(pasopagar, dominios);

	}

	/**
	 * Dom paso registrar.
	 *
	 * @param paso the paso
	 */
	private void domPasoRegistrar(TramitePaso paso, List<Dominio> dominios) {
		final TramitePasoRegistrar pasoRegistrar = (TramitePasoRegistrar) paso;

		dominioScriptRegistrar(pasoRegistrar, dominios);

	}

	/**
	 * Contiene dominio.
	 */
	private void contieneDominio() {

		List<Dominio> dominios = tramiteService.getDominioSimpleByTramiteId(this.versionSeleccionada.getCodigo());
		dominiosNoUtilizados = dominios;
		final List<TramitePaso> pasos = tramiteService.getTramitePasos(this.versionSeleccionada.getCodigo());
		for (TramitePaso paso : pasos) {
			if (paso.getTipo().equals(TypePaso.RELLENAR)) {
				domPasoRellenar(paso, dominios);

			} else if (paso.getTipo().equals(TypePaso.ANEXAR)) {
				domPasoAnexar(paso, dominios);

			} else if (paso.getTipo().equals(TypePaso.PAGAR)) {
				domPasoPagar(paso, dominios);

			} else if (paso.getTipo().equals(TypePaso.REGISTRAR)) {
				domPasoRegistrar(paso, dominios);

			}

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

//		contieneDominio();
//		if (dominiosNoUtilizados != null) {
//			for (Dominio dominioNoUtil : dominiosNoUtilizados) {
//
//				ErrorValidacion errorVal = new ErrorValidacion();
//				errorVal.setDescripcion("No se esta utilizando el dominio indicado");
//				errorVal.setElemento(dominioNoUtil.getIdentificador());
//				errorVal.setTipo(TypeErrorValidacion.DOMINIOS);
//				listaErrores.add(errorVal);
//			}
//		}

		if (!listaErrores.isEmpty()) {
			for (ErrorValidacion error : listaErrores) {
				if (error.getDescripcion().equals("errorFormulario")) {
					error.setDescripcion(UtilJSF.getLiteral("error.formulario.vacio"));
				}
			}
			Map<String, String> params = null;
			TypeModoAcceso modoAccesoErrores;

			final Map<String, Object> mochilaDatos = UtilJSF.getSessionBean().getMochilaDatos();

			mochilaDatos.put(Constantes.CLAVE_MOCHILA_ERRORESVALIDACION,
					listaErrores.stream().map(SerializationUtils::clone).collect(java.util.stream.Collectors.toList()));

			if (pEdicion) {
				params = new HashMap<>();
				params.put(TypeParametroVentana.IDIOMAS.toString(),
						tramiteService.getIdiomasDisponibles(String.valueOf(this.versionSeleccionada.getCodigo())));

				params.put(TypeParametroVentana.TRAMITE.toString(),
						String.valueOf(this.versionSeleccionada.getIdTramite()));

				params.put(TypeParametroVentana.TRAMITEVERSION.toString(),
						String.valueOf(this.versionSeleccionada.getCodigo()));

				modoAccesoErrores = TypeModoAcceso.EDICION;
			} else {
				modoAccesoErrores = TypeModoAcceso.CONSULTA;
			}

			UtilJSF.openDialog(DialogErroresValidacion.class, modoAccesoErrores, params, true, 1050, 520);
			return false;
		}

		return true;
	}

	private boolean tieneFormulario() {
		/**
		 * Recupera disenyo formularios.
		 *
		 * @param pTramiteVersion tramite version
		 */

		if (!this.versionSeleccionada.getListaPasos().isEmpty()) {
			for (final TramitePaso paso : this.versionSeleccionada.getListaPasos()) {
				if (paso instanceof TramitePasoRellenar) {
					final List<FormularioTramite> formularios = ((TramitePasoRellenar) paso).getFormulariosTramite();
					if (formularios != null && !formularios.isEmpty()) {
						return true;
					}
				}
			}
		}
		return false;
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

	public void setDataModel(LazyDataModel<TramiteVersiones> dataModel) {
		this.dataModel = dataModel;
	}

	public boolean isAdmEntidad() {
		return admEntidad;
	}

	public void setAdmEntidad(boolean admEntidad) {
		this.admEntidad = admEntidad;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getPag() {
		return pag;
	}

	public void setPag(String pag) {
		this.pag = pag;
	}

	public List<Dominio> getDominiosNoUtilizados() {
		return dominiosNoUtilizados;
	}

	public void setDominiosNoUtilizados(List<Dominio> dominiosNoUtilizados) {
		this.dominiosNoUtilizados = dominiosNoUtilizados;
	}

}
