package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Tramite;
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
 * Mantenimiento de tramites.
 *
 * @author Indra
 *
 */
@ManagedBean
@ViewScoped
public class ViewTramites extends ViewControllerBase {

	/**
	 * Enlace area.
	 */
	@Inject
	private TramiteService tramiteService;

	/**
	 * security service.
	 */
	@Inject
	private SecurityService securityService;

	/**
	 * Filtro (puede venir por parametro).
	 */
	private String filtro;

	/**
	 * Lista de datos.
	 */
	private TreeNode areas;

	/**
	 * Dato seleccionado en la lista.
	 */
	private DefaultTreeNode areaSeleccionada;

	/**
	 * Lista de datos.
	 */
	private List<Tramite> tramites;

	/**
	 * Dato seleccionado en la lista.
	 */
	private Tramite tramiteSeleccionada;

	/** Id entidad. */
	private Long idEntidad;

	/**
	 * Inicializacion.
	 */
	public void init() {
		// Id entidad
		idEntidad = UtilJSF.getIdEntidad();
		// Control acceso
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidad(idEntidad);
		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		// Recupera areas
		buscarAreas();
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
		if (!verificarFilaSeleccionadaArea())
			return;

		final Area area = (Area) this.areaSeleccionada.getData();
		if (this.tramiteService.removeArea(area.getId())) {
			this.buscarAreas();
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		}
	}

	/**
	 * Dominios de area.
	 */
	public void dominioArea() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea())
			return;

		final Area area = (Area) this.areaSeleccionada.getData();
		UtilJSF.redirectJsfPage("/secure/app/viewDominios.xhtml?ambito=A&id=" + area.getId());
	}

	/**
	 * Fuente datos area.
	 */
	public void datosArea() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea())
			return;

		final Area area = (Area) this.areaSeleccionada.getData();
		UtilJSF.redirectJsfPage("/secure/app/viewFuentes.xhtml?ambito=A&id=" + area.getId());
	}

	/**
	 * Abre dialogo para consultar datos de tramite.
	 */
	public void consultarTramite() {
		abrirTramite(TypeModoAcceso.CONSULTA);
	}

	/**
	 * Abre dialogo para editar tramite.
	 */
	public void editarTramite() {

		abrirTramite(TypeModoAcceso.EDICION);
	}

	/**
	 * Abre dialogo para editar versiones de tramite.
	 */
	public void versionesTramite() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaTramite())
			return;

		// Muestra dialogo
		final Map<String, List<String>> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), Arrays.asList(this.tramiteSeleccionada.getId().toString()));
		params.put(TypeParametroVentana.MODO_ACCESO.toString(), Arrays.asList(TypeModoAcceso.EDICION.name()));
		UtilJSF.redirectJsfPage("/secure/app/viewTramitesVersion.xhtml", params);

	}

	/**
	 * Abre dialogo de nuevo tramite.
	 */
	public void nuevoTramite() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea())
			return;

		final Map<String, String> params = new HashMap<>();

		params.put(TypeParametroVentana.AREA.toString(),
				String.valueOf(((Area) this.areaSeleccionada.getData()).getId()));
		UtilJSF.openDialog(DialogTramite.class, TypeModoAcceso.ALTA, params, true, 540, 220);
	}

	/**
	 * Abre dialogo para eliminar tramite.
	 */
	public void eliminarTramite() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaTramite())
			return;

		if (tramiteService.removeTramite(tramiteSeleccionada.getId())) {
			buscarTramites();
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, UtilJSF.getLiteral("info.borrado.ok"));
		} else {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.borrar.dependencias"));
		}

	}

	/**
	 * Retorno dialogo area.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoArea(final SelectEvent event) {

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
			buscarAreas();
		}
	}

	/**
	 * Obtiene el valor de permiteAlta.
	 *
	 * @return el valor de permiteAlta
	 */
	public boolean getPermiteAltaArea() {
		return (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT);
	}

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getPermiteEditarArea() {
		return (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT);
	}

	/**
	 * Obtiene el valor de permiteAlta.
	 *
	 * @return el valor de permiteAlta
	 */
	public boolean getPermiteAltaTramite() {
		boolean res = false;

		// Admin entidad
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			res = true;
		}

		// Desarrollador
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
			if (areaSeleccionada != null) {
				final List<TypeRolePermisos> permisos = securityService
						.getPermisosDesarrolladorEntidad(((Area) areaSeleccionada.getData()).getId());
				res = permisos.contains(TypeRolePermisos.ALTA_BAJA);
			}
		}

		return res;
	}

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getPermiteEditarTramite() {
		boolean res = false;

		// Admin entidad
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			res = true;
		}

		// Desarrollador
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
			if (areaSeleccionada != null) {
				final List<TypeRolePermisos> permisos = securityService
						.getPermisosDesarrolladorEntidad(((Area) areaSeleccionada.getData()).getId());
				res = (permisos.contains(TypeRolePermisos.MODIFICACION)
						|| permisos.contains(TypeRolePermisos.ALTA_BAJA));
			}
		}

		return res;
	}

	/**
	 * Obtiene el valor de permiteEditar.
	 *
	 * @return el valor de permiteEditar
	 */
	public boolean getPermiteConsultarTramite() {
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
			if (areaSeleccionada == null) {
				return false;
			} else {
				final List<TypeRolePermisos> permisos = securityService
						.getPermisosDesarrolladorEntidad(((Area) areaSeleccionada.getData()).getId());

				return (permisos.contains(TypeRolePermisos.CONSULTA));
			}
		}
		return false;
	}

	/**
	 * Método publico de filtrar
	 */
	public void filtrar() {
		if (filtro == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.filtronorelleno"));
			return;
		}

		this.buscarAreas();
	}

	/**
	 * Retorno dialogo tramite.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoTramite(final SelectEvent event) {

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
			buscarTramites();
		}
	}

	/**
	 * Retorno dialogo mover tramite.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoMoverTramite(final SelectEvent event) {

		final DialogResult respuesta = (DialogResult) event.getObject();

		// Verificamos si se ha modificado
		if (!respuesta.isCanceled()) {
			// Mensaje
			String message = null;
			message = UtilJSF.getLiteral("info.modificado.ok");
			UtilJSF.addMessageContext(TypeNivelGravedad.INFO, message);
			// Refrescamos datos
			buscarTramites();
		}
	}

	/**
	 * Procedimientos tramite.
	 */
	public void procedimientosTramite() {
		UtilJSF.addMessageContext(TypeNivelGravedad.INFO, "Sin implementar");
	}

	/**
	 * Verifica si hay fila seleccionada de area.
	 *
	 * @return
	 */
	private boolean verificarFilaSeleccionadaArea() {
		boolean filaSeleccionada = true;
		if (this.areaSeleccionada == null) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
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
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING, UtilJSF.getLiteral("error.noseleccionadofila"));
			filaSeleccionada = false;
		}
		return filaSeleccionada;
	}

	/**
	 * Area deseleccionada.
	 *
	 * @param event
	 */
	public void onRowUnselectArea(final NodeSelectEvent event) {
		this.areaSeleccionada = null;
	}

	/**
	 * Area seleccionada.
	 *
	 * @param event
	 */
	public void onRowSelectArea(final NodeSelectEvent event) {
		this.areaSeleccionada = (DefaultTreeNode) event.getTreeNode();

		buscarTramites();
	}

	/**
	 * Buscar areas.
	 */
	private void buscarAreas() {
		final List<Area> listaAreas = tramiteService.listArea(UtilJSF.getSessionBean().getEntidad().getId(),
				this.filtro);
		List<Area> listaMostrarAreas = new ArrayList<>();

		// filtramos las areas del desarrollador
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			listaMostrarAreas = listaAreas;
		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
			for (final Area area : listaAreas) {
				final List<TypeRolePermisos> permisos = securityService.getPermisosDesarrolladorEntidad(area.getId());

				if (permisos.contains(TypeRolePermisos.ALTA_BAJA) || permisos.contains(TypeRolePermisos.MODIFICACION)
						|| permisos.contains(TypeRolePermisos.CONSULTA)) {
					listaMostrarAreas.add(area);
				}
			}

		}

		areas = new DefaultTreeNode("Root", null);

		for (final Area area : listaMostrarAreas) {
			areas.getChildren().add(new DefaultTreeNode(area));
		}

		areaSeleccionada = null;
	}

	/**
	 * Buscar tramites.
	 */
	private void buscarTramites() {
		if (areaSeleccionada != null) {
			tramites = tramiteService.listTramite(((Area) areaSeleccionada.getData()).getId(), null);
			// Quitamos seleccion de dato
			tramiteSeleccionada = null;
		}
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

		params.put(TypeParametroVentana.ID.toString(),
				String.valueOf(((Area) this.areaSeleccionada.getData()).getId()));
		UtilJSF.openDialog(DialogArea.class, modoAcceso, params, true, 520, 160);
	}

	/**
	 * Abre dialogo para editar tramite.
	 */
	private void abrirTramite(final TypeModoAcceso modoAcceso) {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaTramite())
			return;

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();

		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.tramiteSeleccionada.getId()));
		UtilJSF.openDialog(DialogTramite.class, modoAcceso, params, true, 540, 220);
	}

	/**
	 * Abre dialogo para mover tramite.
	 */
	public void moverTramite() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaTramite())
			return;
		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(this.tramiteSeleccionada.getId()));
		UtilJSF.openDialog(DialogMoverTramite.class, TypeModoAcceso.EDICION, params, true, 540, 120);
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
	 * @param filtro
	 *            el nuevo valor de filtro
	 */
	public void setFiltro(final String filtro) {
		this.filtro = filtro;
	}

	/**
	 * Obtiene el valor de areas.
	 *
	 * @return el valor de areas
	 */
	public TreeNode getAreas() {
		return areas;
	}

	/**
	 * Establece el valor de areas.
	 *
	 * @param areas
	 *            el nuevo valor de areas
	 */
	public void setAreas(final TreeNode areas) {
		this.areas = areas;
	}

	/**
	 * Obtiene el valor de areaSeleccionada.
	 *
	 * @return el valor de areaSeleccionada
	 */
	public DefaultTreeNode getAreaSeleccionada() {
		return areaSeleccionada;
	}

	/**
	 * Establece el valor de areaSeleccionada.
	 *
	 * @param areaSeleccionada
	 *            el nuevo valor de areaSeleccionada
	 */
	public void setAreaSeleccionada(final DefaultTreeNode areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

	/**
	 * Obtiene el valor de tramites.
	 *
	 * @return el valor de tramites
	 */
	public List<Tramite> getTramites() {
		return tramites;
	}

	/**
	 * Establece el valor de tramites.
	 *
	 * @param tramites
	 *            el nuevo valor de tramites
	 */
	public void setTramites(final List<Tramite> tramites) {
		this.tramites = tramites;
	}

	/**
	 * Obtiene el valor de tramiteSeleccionada.
	 *
	 * @return el valor de tramiteSeleccionada
	 */
	public Tramite getTramiteSeleccionada() {
		return tramiteSeleccionada;
	}

	/**
	 * Establece el valor de tramiteSeleccionada.
	 *
	 * @param tramiteSeleccionada
	 *            el nuevo valor de tramiteSeleccionada
	 */
	public void setTramiteSeleccionada(final Tramite tramiteSeleccionada) {
		this.tramiteSeleccionada = tramiteSeleccionada;
	}

}
