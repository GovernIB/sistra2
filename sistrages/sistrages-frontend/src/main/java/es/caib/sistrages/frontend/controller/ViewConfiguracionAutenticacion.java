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

import es.caib.sistrages.core.api.exception.FrontException;
import es.caib.sistrages.core.api.model.ConfiguracionAutenticacion;
import es.caib.sistrages.core.api.model.Entidad;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypePropiedadConfiguracion;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.ConfiguracionAutenticacionService;
import es.caib.sistrages.core.api.service.EntidadService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.SystemService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.ResultadoError;
import es.caib.sistrages.frontend.model.comun.Constantes;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;
import es.caib.sistrages.frontend.util.UtilRest;

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

	@Inject
	private EntidadService entidadService;

	/** Filtro (puede venir por parametro). */
	private String filtro;

	/** Lista de datos. */
	private List<ConfiguracionAutenticacion> listaDatos;

	/** Dato seleccionado en la lista. */
	private ConfiguracionAutenticacion datoSeleccionado;

	/** Id. **/
	private String id;

	/** Ambito. **/
	private String ambito;

	/** Area. **/
	private String area;

	boolean permiteAlta = false;
	boolean permiteEditar = false;

	/** Mostrar breadcrumb. **/
	private boolean mostrarBreadcrumb;

	/** miga de pan */
	private MenuModel breadCrumb;

	/**
	 * Inicializacion.
	 */
	public void init() {
		// Titulo
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(Long.valueOf(id));

			if (!permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
					&& !permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)
					&& !permisos.contains(TypeRolePermisos.CONSULTA)) {
				throw new FrontException(
						"No se está accediendo con perfil Administrador Entidad o Desarrollador Entidad con acceso al area");
			}

			if (permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)) {
				// Solo el administrador de area puede editar
				permiteAlta = true;
				permiteEditar = true;
			} else {
				permiteAlta = false;
				permiteEditar = false;
			}
		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			permiteAlta = true;
			permiteEditar = true;
		} else {
			throw new FrontException(
					"No se está accediendo con perfil Administrador Entidad o Desarrollador Entidad con acceso al area");
		}

		// Recupera datos
		buscar();

		if (ambito.equals(TypeAmbito.AREA.toString())) {

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
		listaDatos = configuracionAutenticacionService.listConfiguracionAutenticacion(Long.valueOf(id),
				UtilJSF.getIdioma(), filtro);
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
		if (configuracionAutenticacionService.removeConfiguracionAutenticacion(datoSeleccionado.getCodigo())) {
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
		final String urlBase = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_URL.toString());
		final String usuario = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_USER.toString());
		final String pwd = systemService
				.obtenerPropiedadConfiguracion(TypePropiedadConfiguracion.SISTRAMIT_REST_PWD.toString());
		final Entidad entidad = entidadService.loadEntidad(UtilJSF.getIdEntidad());
		final ResultadoError resultado = UtilRest.refrescar(urlBase, usuario, pwd, Constantes.CACHE_ENTIDAD,
				entidad.getCodigoDIR3());
		if (resultado.getCodigo() == 1) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.refrescar"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.ERROR,
					UtilJSF.getLiteral("error.refrescar") + ": " + resultado.getMensaje());
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
		} else {
			consultar();
		}
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
	public List<ConfiguracionAutenticacion> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *                       the listaDatos to set
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
	 * @param datoSeleccionado
	 *                             the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final ConfiguracionAutenticacion datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * Abrir dialogo.
	 *
	 * @param modoAccesoDlg
	 *                          Modo acceso
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
		params.put(TypeParametroVentana.AREA.toString(), this.id);
		UtilJSF.openDialog(DialogConfiguracionAutenticacion.class, modoAccesoDlg, params, true, 550, 195);
	}

	/**
	 * @return the configuracionAutenticacionService
	 */
	public ConfiguracionAutenticacionService getConfiguracionAutenticacionService() {
		return configuracionAutenticacionService;
	}

	/**
	 * @param configuracionAutenticacionService
	 *                                              the
	 *                                              configuracionAutenticacionService
	 *                                              to set
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

}
