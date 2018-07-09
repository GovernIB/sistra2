package es.caib.sistrages.frontend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import es.caib.sistrages.core.api.model.Area;
import es.caib.sistrages.core.api.model.Dominio;
import es.caib.sistrages.core.api.model.Tramite;
import es.caib.sistrages.core.api.model.TramiteVersion;
import es.caib.sistrages.core.api.model.types.TypeAmbito;
import es.caib.sistrages.core.api.model.types.TypeRoleAcceso;
import es.caib.sistrages.core.api.model.types.TypeRolePermisos;
import es.caib.sistrages.core.api.service.DominioService;
import es.caib.sistrages.core.api.service.SecurityService;
import es.caib.sistrages.core.api.service.TramiteService;
import es.caib.sistrages.frontend.model.DialogResult;
import es.caib.sistrages.frontend.model.TramiteVersiones;
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
	 * Service.
	 */
	@Inject
	private DominioService dominioService;

	/**
	 * Service.
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
	 * Id Area.
	 */
	private String idArea;

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
	// private List<Tramite> tramites;

	/**
	 * Dato seleccionado en la lista.
	 */
	private TramiteVersiones tramiteSeleccionada;

	/** Lista de datos. */
	private List<TramiteVersiones> listaTramiteVersiones;

	/** Dato seleccionado en la lista. */
	private TramiteVersion versionSeleccionada;

	/**
	 * Inicializacion.
	 */
	public void init() {
		// Id entidad
		final Long idEntidad = UtilJSF.getIdEntidad();
		// Control acceso
		UtilJSF.verificarAccesoAdministradorDesarrolladorEntidadByEntidad(idEntidad);
		// Titulo pantalla
		setLiteralTituloPantalla(UtilJSF.getTitleViewNameFromClass(this.getClass()));
		// Recupera areas
		// Para obtener el idArea
		idArea = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getParameter("area");
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
		if (this.tramiteService.removeArea(area.getCodigo())) {
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
		UtilJSF.redirectJsfPage("/secure/app/viewDominios.xhtml?ambito=A&id=" + area.getCodigo());
	}

	/**
	 * Fuente datos area.
	 */
	public void datosArea() {
		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaArea())
			return;

		final Area area = (Area) this.areaSeleccionada.getData();
		UtilJSF.redirectJsfPage("/secure/app/viewFuentes.xhtml?ambito=A&id=" + area.getCodigo());
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
		params.put(TypeParametroVentana.ID.toString(),
				Arrays.asList(this.tramiteSeleccionada.getTramite().getCodigo().toString()));
		UtilJSF.redirectJsfPage("/secure/app/viewTramitesVersion.xhtml", params);

	}

	/**
	 * Importar versión.
	 */
	public void importar() {
		final Map<String, String> params = new HashMap<>();
		UtilJSF.openDialog(DialogTramiteVersionImportar.class, TypeModoAcceso.EDICION, params, true, 900, 520);
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

			// Refrescamos datos
			this.buscarAreas();
			this.filtrar();
		}
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
				String.valueOf(((Area) this.areaSeleccionada.getData()).getCodigo()));
		UtilJSF.openDialog(DialogTramite.class, TypeModoAcceso.ALTA, params, true, 540, 220);
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
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR && areaSeleccionada != null) {
			final List<TypeRolePermisos> permisos = securityService
					.getPermisosDesarrolladorEntidadByArea(((Area) areaSeleccionada.getData()).getCodigo());
			res = permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA);
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
						.getPermisosDesarrolladorEntidadByArea(((Area) areaSeleccionada.getData()).getCodigo());
				res = (permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)
						|| permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA));
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
						.getPermisosDesarrolladorEntidadByArea(((Area) areaSeleccionada.getData()).getCodigo());

				return (permisos.contains(TypeRolePermisos.CONSULTA));
			}
		}
		return false;
	}

	/**
	 * Método publico de filtrar
	 */
	public void filtrar() {
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
	 * Double click area.
	 *
	 * @param event
	 */
	public void onRowDblClickArea(final NodeSelectEvent event) {
		this.areaSeleccionada = (DefaultTreeNode) event.getTreeNode();
		if (getPermiteEditarArea()) {
			this.editarArea();
		}
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
		final List<Area> listaAreas = tramiteService.listArea(UtilJSF.getSessionBean().getEntidad().getCodigo(),
				this.filtro);
		List<Area> listaMostrarAreas = new ArrayList<>();

		// filtramos las areas del desarrollador
		if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.ADMIN_ENT) {
			listaMostrarAreas = listaAreas;
		} else if (UtilJSF.getSessionBean().getActiveRole() == TypeRoleAcceso.DESAR) {
			for (final Area area : listaAreas) {
				final List<TypeRolePermisos> permisos = securityService
						.getPermisosDesarrolladorEntidadByArea(area.getCodigo());

				if (permisos.contains(TypeRolePermisos.ADMINISTRADOR_AREA)
						|| permisos.contains(TypeRolePermisos.DESARROLLADOR_AREA)
						|| permisos.contains(TypeRolePermisos.CONSULTA)) {
					listaMostrarAreas.add(area);
				}
			}

		}

		areas = new DefaultTreeNode("Root", null);
		areaSeleccionada = null;

		for (int i = 0; i < listaMostrarAreas.size(); i++) {
			final Area area = listaMostrarAreas.get(i);
			final DefaultTreeNode nodoArea = new DefaultTreeNode(area);
			areas.getChildren().add(nodoArea);
			// Si el idArea (el que viene por la url) coincide con el id del area
			// o no se pasa el parámetro y es el primer elemento de la lista
			// entonces lo buscamos por defecto.
			if ((idArea != null && area.getCodigo().compareTo(Long.valueOf(idArea)) == 0)
					|| (idArea == null && i == 0)) {
				this.areaSeleccionada = nodoArea;
				areas.setSelected(true);
				nodoArea.setSelected(true);

			}
		}

		// Lo desactivamos
		idArea = null;

		buscarTramites();
	}

	/**
	 * Buscar tramites.
	 */
	private void buscarTramites() {
		// Quitamos seleccion de dato
		tramiteSeleccionada = null;
		versionSeleccionada = null;

		if (areaSeleccionada == null) {
			listaTramiteVersiones = new ArrayList<>();
		} else {
			listaTramiteVersiones = new ArrayList<>();

			final List<Tramite> tramites = tramiteService.listTramite(((Area) areaSeleccionada.getData()).getCodigo(),
					this.filtro);

			// Obtenemos activa a los tramites que tengan alguna version activa
			final List<Long> idTramites = tramiteService
					.listTramiteVersionActiva(((Area) areaSeleccionada.getData()).getCodigo());

			for (final Tramite tramite : tramites) {
				if (idTramites.contains(tramite.getCodigo())) {
					tramite.setActivo(true);
				}

				final List<TramiteVersion> listaVersiones = tramiteService.listTramiteVersion(tramite.getCodigo(),
						null);

				listaTramiteVersiones.add(new TramiteVersiones(tramite, listaVersiones));
			}

		}
	}

	/**
	 * Double click area.
	 *
	 * @param event
	 */
	public void onRowDblClickTramites(final NodeSelectEvent event) {
		if (this.tramiteSeleccionada == null) {
			return;
		}

		this.versionesTramite();

	}

	public void onRowDblClickTramites() {
		if (this.tramiteSeleccionada == null) {
			return;
		}

		if (getPermiteEditarTramite()) {
			this.editarTramite();
		} else if (getPermiteConsultarTramite()) {
			this.consultarTramite();
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
				String.valueOf(((Area) this.areaSeleccionada.getData()).getCodigo()));
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

		params.put(TypeParametroVentana.ID.toString(),
				String.valueOf(this.tramiteSeleccionada.getTramite().getCodigo()));
		UtilJSF.openDialog(DialogTramite.class, modoAcceso, params, true, 540, 220);
	}

	/**
	 * Abre dialogo para mover tramite.
	 */
	public void moverTramite() {

		// Verifica si no hay fila seleccionada
		if (!verificarFilaSeleccionadaTramite())
			return;

		// Verificamos que los dominiosId que tiene asignado, alguna de sus versiones,
		// no
		// sea ninguno de ambito area.
		if (checkDominioArea()) {
			UtilJSF.addMessageContext(TypeNivelGravedad.WARNING,
					UtilJSF.getLiteral("viewTramites.error.tramiteConDominioAreas"));
			return;
		}

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(),
				String.valueOf(this.tramiteSeleccionada.getTramite().getCodigo()));
		UtilJSF.openDialog(DialogMoverTramite.class, TypeModoAcceso.EDICION, params, true, 540, 120);
	}

	/**
	 * Comprobamos que no tiene ningun dominio de tipo area asignado.
	 *
	 * @return
	 */
	private boolean checkDominioArea() {
		final List<TramiteVersion> tramitesVersion = tramiteService
				.listTramiteVersion(this.tramiteSeleccionada.getTramite().getCodigo(), null);
		if (tramitesVersion != null) {
			for (final TramiteVersion tramiteVersion : tramitesVersion) {
				final List<Long> dominiosId = tramiteService.getTramiteDominiosId(tramiteVersion.getCodigo());
				if (dominiosId != null) {
					for (final Long dominioId : dominiosId) {
						final Dominio dominio = dominioService.loadDominio(dominioId);
						if (dominio.getAmbito() == TypeAmbito.AREA) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public void unSelectTramite() {
		tramiteSeleccionada = null;
	}

	public void unSelectVersionTramite() {
		versionSeleccionada = null;
	}

	/**
	 * Abre dialogo para nueva version.
	 */
	public void nuevaVersion() {

		// Muestra dialogo
		final Map<String, String> params = new HashMap<>();
		params.put(TypeParametroVentana.ID.toString(), String.valueOf(tramiteSeleccionada.getTramite().getCodigo()));
		UtilJSF.openDialog(DialogTramiteVersion.class, TypeModoAcceso.ALTA, params, true, 400, 150);

	}

	/**
	 * Retorno dialogo nueva version.
	 *
	 * @param event
	 *            respuesta dialogo
	 */
	public void returnDialogoNuevaVersion(final SelectEvent event) {

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
			buscarTramites();
		}
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
	// public List<Tramite> getTramites() {
	// return tramites;
	// }

	/**
	 * Establece el valor de tramites.
	 *
	 * @param tramites
	 *            el nuevo valor de tramites
	 */
	// public void setTramites(final List<Tramite> tramites) {
	// this.tramites = tramites;
	// }

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
	 * @param tramiteSeleccionada
	 *            el nuevo valor de tramiteSeleccionada
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
	 * @param idArea
	 *            the idArea to set
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

}
