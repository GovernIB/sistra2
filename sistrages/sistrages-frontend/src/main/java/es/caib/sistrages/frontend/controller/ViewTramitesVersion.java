package es.caib.sistrages.frontend.controller;

import java.util.Arrays;
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
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeEntorno;
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

	/** Tramite. */
	private Tramite tramite;

	/** Area. **/
	private Area area;

	/** Permisos. **/
	private boolean permiteAltaArea = false;
	private boolean permiteEliminarArea = false;
	private boolean permiteEditarArea = false;
	private boolean permiteConsultarArea = false;
	private boolean permiteAlta = false;
	private boolean permiteEliminar = false;
	private boolean permiteConsultar = false;
	private boolean permiteEditar = false;
	private boolean permiteClonar = false;
	private boolean permiteBloquear = false;
	private boolean permiteDesbloquear = false;
	private boolean permiteProcedimientos = false;
	private boolean permiteExportar = false;
	private boolean permiteMover = false;
	private boolean permiteHistorial = false;

	/**
	 * Inicializacion.
	 */
	public void init() {

		/* titulo pantalla */
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));

		// Control acceso
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidadByEntidad(UtilJSF.getIdEntidad());

		// Preparamos datos.
		tramite = tramiteService.getTramite(Long.valueOf(id));
		area = tramiteService.getAreaTramite(Long.valueOf(id));

		// Verficamos que puedes ver el area
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidadByArea(area.getCodigo());

		// Preparamos permisos
		checkPermiteArea();

		this.filtrar();

		/* inicializa breadcrum */
		breadCrumb = new DefaultMenuModel();

		DefaultMenuItem item = null;

		item = new DefaultMenuItem(area.getIdentificador());
		item.setUrl("/secure/app/viewTramites.xhtml?area=" + area.getCodigo());
		breadCrumb.addElement(item);

		item = new DefaultMenuItem(tramite.getIdentificador());
		item.setUrl("#");
		breadCrumb.addElement(item);

		breadCrumb.generateUniqueIds();

	}

	/**
	 * Comprueba si para el area, puede editarlo o consultarlo.
	 */
	private void checkPermiteArea() {

		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {

			this.permiteAltaArea = true;
			this.permiteEliminarArea = true;
			this.permiteEditarArea = true;
			this.permiteConsultarArea = true;

		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {

			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(this.area.getCodigo());

			// Si no es desarrollo, desactivar seguro el area para alta y editar.
			if (UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {

				this.permiteAltaArea = permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
						|| permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA);

				this.permiteEliminarArea = permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
						|| permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA);
				this.permiteEditarArea = permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
						|| permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA);
				this.permiteConsultarArea = permisos.contains(TypeRolePermisos.CONSULTA);
			} else {

				if (permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)) {

					this.permiteEditarArea = true;
					this.permiteConsultarArea = true;

				} else {

					this.permiteEditarArea = false;
					this.permiteConsultarArea = true;

				}

				this.permiteAltaArea = false;
				this.permiteEliminarArea = false;

			}

		} else {
			this.permiteAltaArea = false;
			this.permiteEditarArea = false;
			this.permiteConsultarArea = false;
			this.permiteEliminarArea = false;
		}

		permiteAlta = permiteAltaArea;
		permiteConsultar = permiteConsultarArea;
		permiteExportar = permiteAltaArea;
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

		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
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

		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {

			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(this.area.getCodigo());
			// Si no puedes editar, no puedes bloquear
			if (!permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)
					&& !permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)) {
				UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.permisos.insuficientes"));
				return;
			}
		}

		tramiteService.bloquearTramiteVersion(this.datoSeleccionado.getCodigo(),
				UtilJSF.getSessionBean().getUserName());

		filtrar();
		this.datoSeleccionado = null;
		actualizarPermisos();
	}

	/**
	 * Bloquear version.
	 */
	public void desbloquear() {

		if (!verificarFilaSeleccionada())
			return;

		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
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

		if (!permiteEditar || (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR
				&& !this.datoSeleccionado.getDatosUsuarioBloqueo().equals(UtilJSF.getSessionBean().getUserName()))) {
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
			actualizarPermisos();
		}
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
	 * Actualiza los permisos según el tramite seleccionado.
	 */
	public void actualizarPermisos() {

		if (this.datoSeleccionado == null) {

			permiteEditar = false;
			permiteConsultar = false;
			permiteEliminar = false;
			permiteBloquear = false;
			permiteDesbloquear = false;
			permiteMover = false;
			permiteClonar = false;
			permiteProcedimientos = false;
			permiteExportar = false;
			permiteHistorial = false;

		} else {

			checkPermiteEditar();
			checkPermiteConsulta();
			checkPermiteEliminar();
			checkPermiteBloquear();
			checkPermiteDesbloquear();
			checkPermiteMover();
			checkPermiteClonar();
			checkPermiteProcedimientos();
			checkPermiteExportar();
			checkPermiteHistorial();

		}
	}

	/**
	 * Comprueba si puede editar
	 *
	 * @return el valor de permiteEditar
	 */
	public void checkPermiteEditar() {

		if (this.datoSeleccionado == null) {
			permiteEditar = false;
			return;
		}

		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			permiteEditar = false;
			return;
		}

		if (!this.datoSeleccionado.getBloqueada()) {
			permiteEditar = false;
			return;
		}

		permiteEditar = permiteEditarArea;

	}

	private void checkPermiteExportar() {
		if (this.datoSeleccionado == null) {
			this.permiteExportar = false;
		} else {
			this.permiteExportar = this.permiteAltaArea;
		}
	}

	/**
	 * Calcula si se puede eliminar.
	 */
	public void checkPermiteEliminar() {

		if (this.datoSeleccionado == null) {
			permiteEliminar = false;
			return;
		}

		permiteEliminar = permiteEliminarArea;
	}

	/**
	 * Calcula si se puede consultar.
	 *
	 */
	public void checkPermiteConsulta() {

		if (this.datoSeleccionado == null) {
			permiteConsultar = false;
		} else {
			if (permiteConsultarArea) {
				if (permiteEditar) {
					permiteConsultar = false;
				} else {
					permiteConsultar = true;
				}
			} else {
				permiteConsultar = false;
			}
		}
	}

	/**
	 * Calcula si se puede bloquear el tramite o no.
	 *
	 */
	public void checkPermiteBloquear() {

		if (this.datoSeleccionado == null) {
			this.permiteBloquear = false;
			return;
		}

		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			this.permiteBloquear = false;
			return;
		}

		if (this.datoSeleccionado.getBloqueada()) {
			this.permiteBloquear = false;
			return;
		}

		this.permiteBloquear = this.permiteEditarArea;

	}

	/**
	 * Calcula si se puede bloquear el tramite o no.
	 *
	 */
	public void checkPermiteDesbloquear() {

		if (this.datoSeleccionado == null) {
			this.permiteDesbloquear = false;
			return;
		}

		if (!UtilJSF.checkEntorno(TypeEntorno.DESARROLLO)) {
			this.permiteDesbloquear = false;
			return;
		}

		if (!this.datoSeleccionado.getBloqueada()) {
			this.permiteDesbloquear = false;
			return;
		}

		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR
				&& !this.datoSeleccionado.getDatosUsuarioBloqueo().equals(UtilJSF.getSessionBean().getUserName())) {
			this.permiteDesbloquear = false;
			return;
		}

		this.permiteDesbloquear = this.permiteEditarArea;
	}

	/**
	 * Calcula si se permite mover.
	 */
	public void checkPermiteMover() {
		if (this.datoSeleccionado == null) {
			this.permiteDesbloquear = false;
			return;
		}

		permiteMover = permiteEditarArea;
	}

	/**
	 * Calcula si se permite mover.
	 */
	public void checkPermiteProcedimientos() {
		if (this.datoSeleccionado == null) {
			permiteProcedimientos = false;
		} else {
			permiteProcedimientos = true;
		}
	}

	/**
	 * Calcula si se puede clonar.
	 */
	public void checkPermiteClonar() {
		if (this.datoSeleccionado == null) {
			permiteClonar = false;
		} else {
			permiteClonar = permiteAltaArea;
		}

	}

	/**
	 * Calcula si se puede clonar.
	 */
	public void checkPermiteHistorial() {
		if (this.datoSeleccionado == null) {
			setPermiteHistorial(false);
		} else {
			setPermiteHistorial(true);
		}

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
	 * @return the permiteEliminar
	 */
	public boolean isPermiteEliminar() {
		return permiteEliminar;
	}

	/**
	 * @param permiteEliminar
	 *            the permiteEliminar to set
	 */
	public void setPermiteEliminar(final boolean permiteEliminar) {
		this.permiteEliminar = permiteEliminar;
	}

	/**
	 * @return the permiteEditar
	 */
	public boolean isPermiteEditar() {
		return permiteEditar;
	}

	/**
	 * @param permiteEditar
	 *            the permiteEditar to set
	 */
	public void setPermiteEditar(final boolean permiteEditar) {
		this.permiteEditar = permiteEditar;
	}

	/**
	 * @return the permiteBloquear
	 */
	public boolean isPermiteBloquear() {
		return permiteBloquear;
	}

	/**
	 * @param permiteBloquear
	 *            the permiteBloquear to set
	 */
	public void setPermiteBloquear(final boolean permiteBloquear) {
		this.permiteBloquear = permiteBloquear;
	}

	/**
	 * @return the permiteDesbloquear
	 */
	public boolean isPermiteDesbloquear() {
		return permiteDesbloquear;
	}

	/**
	 * @param permiteDesbloquear
	 *            the permiteDesbloquear to set
	 */
	public void setPermiteDesbloquear(final boolean permiteDesbloquear) {
		this.permiteDesbloquear = permiteDesbloquear;
	}

	/**
	 * @return the permiteProcedimientos
	 */
	public boolean isPermiteProcedimientos() {
		return permiteProcedimientos;
	}

	/**
	 * @param permiteProcedimientos
	 *            the permiteProcedimientos to set
	 */
	public void setPermiteProcedimientos(final boolean permiteProcedimientos) {
		this.permiteProcedimientos = permiteProcedimientos;
	}

	/**
	 * @return the permiteMover
	 */
	public boolean isPermiteMover() {
		return permiteMover;
	}

	/**
	 * @param permiteMover
	 *            the permiteMover to set
	 */
	public void setPermiteMover(final boolean permiteMover) {
		this.permiteMover = permiteMover;
	}

	/**
	 * @return the permiteConsultar
	 */
	public boolean isPermiteConsultar() {
		return permiteConsultar;
	}

	/**
	 * @return the permiteConsultar
	 */
	public boolean getPermiteConsultar() {
		return permiteConsultar;
	}

	/**
	 * @param permiteConsultar
	 *            the permiteConsultar to set
	 */
	public void setPermiteConsultar(final boolean permiteConsultar) {
		this.permiteConsultar = permiteConsultar;
	}

	/**
	 * @return the permiteClonar
	 */
	public boolean isPermiteClonar() {
		return permiteClonar;
	}

	/**
	 * @param permiteClonar
	 *            the permiteClonar to set
	 */
	public void setPermiteClonar(final boolean permiteClonar) {
		this.permiteClonar = permiteClonar;
	}

	/**
	 * @return the permiteHistorial
	 */
	public boolean isPermiteHistorial() {
		return permiteHistorial;
	}

	/**
	 * @param permiteHistorial
	 *            the permiteHistorial to set
	 */
	public void setPermiteHistorial(final boolean permiteHistorial) {
		this.permiteHistorial = permiteHistorial;
	}

	/**
	 * @return the permiteExportar
	 */
	public boolean isPermiteExportar() {
		return permiteExportar;
	}

	/**
	 * @param permiteExportar
	 *            the permiteExportar to set
	 */
	public void setPermiteExportar(final boolean permiteExportar) {
		this.permiteExportar = permiteExportar;
	}

	/**
	 * @return the permiteEliminarArea
	 */
	public boolean isPermiteEliminarArea() {
		return permiteEliminarArea;
	}

	/**
	 * @return the permiteEliminarArea
	 */
	public boolean getPermiteEliminarArea() {
		return permiteEliminarArea;
	}

	/**
	 * @param permiteEliminarArea
	 *            the permiteEliminarArea to set
	 */
	public void setPermiteEliminarArea(final boolean permiteEliminarArea) {
		this.permiteEliminarArea = permiteEliminarArea;
	}

}
