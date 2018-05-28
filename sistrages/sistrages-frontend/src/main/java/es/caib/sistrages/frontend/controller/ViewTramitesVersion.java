package es.caib.sistrages.frontend.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.types.TypeModoAcceso;
import es.caib.sistrages.frontend.model.types.TypeNivelGravedad;
import es.caib.sistrages.frontend.model.types.TypeParametroVentana;
import es.caib.sistrages.frontend.util.UtilJSF;

/**
 * Mantenimiento de tramites version.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewTramitesVersion extends ViewControllerBase {

	/** Security service. */
	@Inject
	private SecurityService securityService;

	/** Tramite service. */
	@Inject
	private TramiteService tramiteService;

	/** Id del trámite. **/
	private String id;

	/** Breadcrumb. **/
	private MenuModel breadCrumb;

	/** Filtro (puede venir por parametro). */
	private String filtro;

	/** Lista de datos. */
	private List<TramiteVersion> listaDatos;

	/** Dato seleccionado en la lista. */
	private TramiteVersion datoSeleccionado;

	/** Indica si puede realizar el alta/baja. **/
	private boolean permiteAlta = false;

	/** Indica si puede realizar la modificación. **/
	private boolean permiteModificacion = false;

	/** Indica si puede consultar. **/
	private boolean permiteConsulta = false;

	/**
	 * Inicializacion.
	 */
	public void init() {

		/* titulo pantalla */
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		// Control acceso
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidad(UtilJSF.getIdEntidad());

		// Preparamos permisos
		checkPermiteAlta();
		checkPermiteEditar();
		checkPermiteConsulta();

		this.filtrar();

		final Tramite tramite = tramiteService.getTramite(Long.valueOf(id));
		final Area area = tramiteService.getAreaTramite(Long.valueOf(id));

		/* inicializa breadcrum */
		breadCrumb = new DefaultMenuModel();

		DefaultMenuItem item = null;

		item = new DefaultMenuItem(area.getCodigo());
		item.setUrl("/secure/app/viewTramites.xhtml?area=" + area.getId());
		breadCrumb.addElement(item);

		item = new DefaultMenuItem(tramite.getIdentificador());
		item.setUrl("#");
		breadCrumb.addElement(item);

		breadCrumb.generateUniqueIds();

		checkPermiteAlta();

	}

	/**
	 * Abre dialogo para nuevo dato.
	 */
	public void nuevo() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.id);
		UtilJSF.openDialog(DialogTramiteVersion.class, TypeModoAcceso.ALTA, params, true, 400, 150);

	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void editar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, List<String>> params = new HashMap<>();
		// params.put(TypeParametroVentana.ID.toString(),

		params.put(TypeParametroVentana.ID.toString(), Arrays.asList(datoSeleccionado.getCodigo().toString()));
		params.put(TypeParametroVentana.MODO_ACCESO.toString(), Arrays.asList(TypeModoAcceso.EDICION.name()));
		UtilJSF.redirectJsfPage("/secure/app/viewDefinicionVersion.xhtml", params);

	}

	/**
	 * Abre dialogo para editar dato.
	 */
	public void consultar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Muestra dialogo
		final Map<String, List<String>> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), Arrays.asList(datoSeleccionado.getCodigo().toString()));
		params.put(TypeParametroVentana.MODO_ACCESO.toString(), Arrays.asList(TypeModoAcceso.CONSULTA.name()));
		UtilJSF.redirectJsfPage("/secure/app/viewDefinicionVersion.xhtml", params);

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

		if (!verificarFilaSeleccionada())
			return;

		if (!checkEntornoDesarrollo()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewTramitesVersion.entorno.noDesarrollo"));
			return;
		}

		if (this.datoSeleccionado.getBloqueada()) {
			// Si está bloqueada, entonces no puedes bloquearlo.
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewTramitesVersion.bloquear.errorYaBloqueado"));
			return;
		}

		if (!this.permiteModificacion) {
			// Si no puedes editar, no puedes bloquear
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.permisos.insuficientes"));
			return;
		}

		tramiteService.bloquearTramiteVersion(this.datoSeleccionado.getCodigo(),
				UtilJSF.getSessionBean().getUserName());

		filtrar();
		this.datoSeleccionado = null;
	}

	/**
	 * Bloquear version.
	 */
	public void desbloquear() {

		if (!verificarFilaSeleccionada())
			return;

		if (!checkEntornoDesarrollo()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewTramitesVersion.entorno.noDesarrollo"));
			return;
		}

		if (!this.datoSeleccionado.getBloqueada()) {
			// Si está desbloqueada, entonces no puedes bloquearlo.
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
					UtilJSF.getLiteral("viewTramitesVersion.bloquear.errorYaDesbloqueado"));
			return;
		}

		if (!this.permiteModificacion) {
			// Si no puedes editar, no puedes bloquear
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.permisos.insuficientes"));
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());

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
			filtrar();
			this.datoSeleccionado = null;
		}
	}

	/**
	 * Comprueba si el entorno es DESARROLLO.
	 *
	 * @return
	 */
	private boolean checkEntornoDesarrollo() {
		final String entorno = FacesContext
				.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(),
						"#{negocioModuleConfig}", es.caib.sistrages.frontend.model.comun.ModuleConfig.class)
				.getEntorno();
		return "des".equals(entorno);
	}

	/**
	 * Exportar version.
	 */
	public void exportar() {
		if (!verificarFilaSeleccionada()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
		UtilJSF.openDialog(DialogTramiteVersionExportar.class, TypeModoAcceso.EDICION, params, true, 900, 520);
	}

	/**
	 * Importar versión.
	 */
	public void importar() {
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), id);
		UtilJSF.openDialog(DialogTramiteVersionImportar.class, TypeModoAcceso.EDICION, params, true, 900, 520);
	}

	/**
	 * Historial.
	 */
	public void historial() {

		if (!verificarFilaSeleccionada()) {
			return;
		}

		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), this.datoSeleccionado.getCodigo().toString());
		UtilJSF.openDialog(DialogHistorialVersion.class, TypeModoAcceso.CONSULTA, params, true, 900, 520);

	}

	/**
	 * Regrescar.
	 */
	public void refrescar() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Duplicar.
	 */
	public void duplicar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Clonamos
		this.tramiteService.clonadoTramiteVersion(this.datoSeleccionado.getCodigo(),
				UtilJSF.getSessionBean().getUserName());

		// Refrescamos datos
		this.datoSeleccionado = null;
		this.filtrar();

		// Mostramos mensaje
		// UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
		// UtilJSF.getLiteral("info.clonado.ok"));
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO,
				"Se ha clonado pero falta revisar el clonado de ficheros y de formularios interno");
	}

	/**
	 * Elimina dato seleccionado.
	 */
	public void eliminar() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionada())
			return;

		// Eliminamos
		this.tramiteService.removeTramiteVersion(this.datoSeleccionado.getCodigo());

		// Refrescamos datos
		this.filtrar();

		// Mostramos mensaje
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
	}

	/**
	 * Filtrar
	 */
	public void filtrar() {
		listaDatos = tramiteService.listTramiteVersion(Long.valueOf(id), this.filtro);
	}

	/**
	 * Obtiene el valor de permiteAlta.
	 *
	 * @return el valor de permiteAlta
	 */
	public void checkPermiteAlta() {
		this.setPermiteAlta(false);
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT
				|| UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.SUPER_ADMIN) {
			this.setPermiteAlta(true);
		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {

			final List<TypeRolePermisos> permisos = securityService.getPermisosDesarrolladorEntidad(Long.valueOf(id));

			this.setPermiteAlta(permisos.contains(TypeRolePermisos.ALTA_BAJA));
		}
	}

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public void checkPermiteEditar() {
		this.setPermiteModificacion(false);
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT
				|| UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.SUPER_ADMIN) {

			this.setPermiteModificacion(true);

		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {

			final List<TypeRolePermisos> permisos = securityService.getPermisosDesarrolladorEntidad(Long.valueOf(id));
			this.setPermiteModificacion(
					permisos.contains(TypeRolePermisos.MODIFICACION) || permisos.contains(TypeRolePermisos.ALTA_BAJA));

		}
	}

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public void checkPermiteConsulta() {
		this.setPermiteConsulta(!this.permiteAlta && !this.permiteModificacion);
	}

	/**
	 * Retorno dialogo.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogo(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		String message = null;

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled() && !respuesta.getModoAcceso().equals(TypeModoAcceso.CONSULTA)) {
			// Mensaje
			if (respuesta.getModoAcceso().equals(TypeModoAcceso.ALTA)) {
				message = UtilJSF.getLiteral("info.alta.ok");
			} else {
				message = UtilJSF.getLiteral("info.modificado.ok");
			}
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);

			// Refrescamos datos
			this.filtrar();
		}
	}

	// ------- FUNCIONES PRIVADAS ------------------------------
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

	// ------- GETTERS / SETTERS --------------------------------
	/**
	 * @return the filtro
	 */
	public String getFiltro() {
		return filtro;
	}

	/**
	 * @param filtro
	 *            the filtro to set
	 */
	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	/**
	 * @return the listaDatos
	 */
	public List<TramiteVersion> getListaDatos() {
		return listaDatos;
	}

	/**
	 * @param listaDatos
	 *            the listaDatos to set
	 */
	public void setListaDatos(final List<TramiteVersion> listaDatos) {
		this.listaDatos = listaDatos;
	}

	/**
	 * @return the datoSeleccionado
	 */
	public TramiteVersion getDatoSeleccionado() {
		return datoSeleccionado;
	}

	/**
	 * @param datoSeleccionado
	 *            the datoSeleccionado to set
	 */
	public void setDatoSeleccionado(final TramiteVersion datoSeleccionado) {
		this.datoSeleccionado = datoSeleccionado;
	}

	/**
	 * @return the breadCrumb
	 */
	public MenuModel getBreadCrumb() {
		return breadCrumb;
	}

	/**
	 * @param breadCrumb
	 *            the breadCrumb to set
	 */
	public void setBreadCrumb(final MenuModel breadCrumb) {
		this.breadCrumb = breadCrumb;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the permiteAlta
	 */
	public boolean isPermiteAlta() {
		return permiteAlta;
	}

	/**
	 * @param permiteAlta
	 *            the permiteAlta to set
	 */
	public void setPermiteAlta(final boolean permiteAlta) {
		this.permiteAlta = permiteAlta;
	}

	/**
	 * @return the permiteModificacion
	 */
	public boolean isPermiteModificacion() {
		return permiteModificacion;
	}

	/**
	 * @return the permiteConsulta
	 */
	public boolean isPermiteConsulta() {
		return permiteConsulta;
	}

	/**
	 * @param permiteModificacion
	 *            the permiteModificacion to set
	 */
	public void setPermiteModificacion(final boolean permiteModificacion) {
		this.permiteModificacion = permiteModificacion;
	}

	/**
	 * @param permiteConsulta
	 *            the permiteConsulta to set
	 */
	public void setPermiteConsulta(final boolean permiteConsulta) {
		this.permiteConsulta = permiteConsulta;
	}
}
