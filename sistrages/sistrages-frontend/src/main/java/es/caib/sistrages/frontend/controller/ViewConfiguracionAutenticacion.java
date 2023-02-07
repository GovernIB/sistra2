package es.caib.sistrages.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.Sesion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
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
 * Mantenimiento de configuracion autenticacion de area.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewConfiguracionAutenticacion extends ViewControllerBase {

	@Inject
	private ConfiguracionAutenticacionService configuracionAutenticacionService;

	@Inject
	private SystemService systemService;

	@Inject
	private SecurityService securityService;

	/** tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Filtro (puede venir por parametro). */
	private String filtro;

	/** Lista de datos. */
	private List<ConfiguracionAutenticacion> listaDatos;

	/** Dato seleccionado en la lista. */
	private ConfiguracionAutenticacion datoSeleccionado;

	/** Paginacion */
	private Integer paginacion;

	/** Accion. **/
	private String accion;

	/** Id. **/
	private String id;
	private Long idArea;
	private Long idEntidad;

	/** Ambito. **/
	private String ambito;

	/** Area. **/
	private String area;

	/** Lista de areas. */
	private List<Area> listaAreas;

	boolean permiteAlta = false;
	boolean permiteEditar = false;

	/** Mostrar breadcrumb. **/
	private boolean mostrarBreadcrumb;

	/** miga de pan */
	private MenuModel breadCrumb;

	private String portapapeles;

	private String errorCopiar;

	/**
	 * Inicializacion.
	 */
	public void init() {
		// Titulo
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		setAccion("C");

		paginacion = UtilJSF.getPaginacion("viewConfiguracionAutenticacion");

		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(Long.valueOf(id));

			/*
			 * if (!permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA) &&
			 * !permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA) &&
			 * !permisos.contains(TypeRolePermisos.CONSULTA)) { throw new FrontException(
			 * "No se está accediendo con perfil Administrador Entidad o Desarrollador Entidad con acceso al area"
			 * ); }
			 */

			if (permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)) {
				// Solo el administrador de area puede editar
				permiteAlta = true;
				permiteEditar = true;
			} else {
				permiteAlta = false;
				permiteEditar = false;
			}
		} else {
			permiteAlta = true;
			permiteEditar = true;
		}

		if (ambito.equals(TypeAmbito.AREA.toString())) {

			idArea = Long.valueOf(id);

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

		} else if (ambito.equals(TypeAmbito.ENTIDAD.toString())) {

			idEntidad = UtilJSF.getIdEntidad();
			mostrarBreadcrumb = false;

		} else {
			mostrarBreadcrumb = false;
		}

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
		listaDatos = configuracionAutenticacionService.listConfiguracionAutenticacion(TypeAmbito.fromString(ambito),
				idArea, idEntidad, UtilJSF.getIdioma(), filtro);
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

		if (configuracionAutenticacionService.removeConfiguracionAutenticacion(datoSeleccionado.getCodigo())) {
			// Refrescamos datos
			buscar();
			// Mostramos mensaje
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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		}
	}

	/**
	 * Abrir ayuda.
	 */
	public void ayuda() {
		UtilJSF.openHelp("configuracionAutenticacion");
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
		return permiteAlta;
	}

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getPermiteEditar() {
		return permiteEditar;
	}

	/**
	 * Dbl click.
	 */
	public void rcDobleClick() {
		if (getPermiteEditar()) {
			editar();
		} else if (!permiteEditar && !permiteAlta && ambito.equals(TypeAmbito.AREA.toString())) {
			consultar();
		}
	}

	/**
	 * Cambia de acción
	 */
	public void cambiarAccion() {
		switch (accion) {
		case "V":
			UtilJSF.redirectJsfPage("/secure/app/viewVariablesArea.xhtml?id=" + id);
		case "D":
			UtilJSF.redirectJsfPage("/secure/app/viewDominios.xhtml?ambito=A&id=" + id + "&area=" + area);
		case "F":
			UtilJSF.redirectJsfPage("/secure/app/viewFuentes.xhtml?ambito=A&id=" + id + "&area=" + area);
		case "S":
			UtilJSF.redirectJsfPage("/secure/app/viewFormulariosExternos.xhtml?ambito=A&id=" + id + "&area=" + area);
		case "C":
			break;
		case "E":
			UtilJSF.redirectJsfPage("/secure/app/viewEnviosRemotos.xhtml?ambito=A&id=" + id + "&area=" + area);
		default:
			break;
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
				 * if (re.getCodigo() != 1) { message = UtilJSF.getLiteral("info.alta.ok") +
				 * ". " + UtilJSF.getLiteral("error.refrescarCache") + ": " + re.getMensaje(); }
				 * else { message = UtilJSF.getLiteral("info.alta.ok") + ". " +
				 * UtilJSF.getLiteral("info.cache.ok"); }
				 */
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.alta.ok"));
			} else {
				ResultadoError re = this.refrescar();
				String message = null;
				if (re.getCodigo() != 1) {
					message = UtilJSF.getLiteral("info.modificado.ok") + ". "
							+ UtilJSF.getLiteral("error.refrescarCache") + ": " + re.getMensaje();
				} else {
					message = UtilJSF.getLiteral("info.modificado.ok") + ". " + UtilJSF.getLiteral("info.cache.ok");
				}
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
			}
			buscar();
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
	public List<ConfiguracionAutenticacion> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos the listaDatos to set
	 */
	public void setListaDatos(final List<ConfiguracionAutenticacion> listaDatos) {
		this.listaDatos = listaDatos;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(final String ambito) {
		this.ambito = ambito;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(final String area) {
		this.area = area;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public ConfiguracionAutenticacion getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final ConfiguracionAutenticacion datoSeleccionado) {
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
		if (TypeAmbito.fromString(ambito) == TypeAmbito.AREA) {
			params.put(TypeParametroVentana.AREA.toString(), this.idArea.toString());
		}
		if (TypeAmbito.fromString(ambito) == TypeAmbito.ENTIDAD) {
			params.put(TypeParametroVentana.ENTIDAD.toString(), UtilJSF.getIdEntidad().toString());
		}
		params.put(TypeParametroVentana.AMBITO.toString(), ambito);
		UtilJSF.openDialog(DialogConfiguracionAutenticacion.class, modoAccesoDlg, params, true, 580, 195);
	}

	/**
	 * @return the configuracionAutenticacionService
	 */
	public ConfiguracionAutenticacionService getConfiguracionAutenticacionService() {
		return configuracionAutenticacionService;
	}

	/**
	 * @param configuracionAutenticacionService the
	 *                                          configuracionAutenticacionService to
	 *                                          set
	 */
	public void setConfiguracionAutenticacionService(
			final ConfiguracionAutenticacionService configuracionAutenticacionService) {
		this.configuracionAutenticacionService = configuracionAutenticacionService;
	}

	public boolean isMostrarBreadcrumb() {
		return mostrarBreadcrumb;
	}

	public void setMostrarBreadcrumb(final boolean mostrarBreadcrumb) {
		this.mostrarBreadcrumb = mostrarBreadcrumb;
	}

	public MenuModel getBreadCrumb() {
		return breadCrumb;
	}

	public void setBreadCrumb(final MenuModel breadCrumb) {
		this.breadCrumb = breadCrumb;
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
		UtilJSF.setPaginacion(paginacion, "viewConfiguracionAurenticacion");
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

}
